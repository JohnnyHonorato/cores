import {Component, Input, OnInit} from '@angular/core';
import {TempService} from '@shared/services/temp.service';
import {OrganizationURL, PeopleURL} from '@shared/util/url.domain';
import {AppInjector, NotificationService} from 'common';

@Component({
    selector: 'tracker-customizable-attributes-view',
    templateUrl: './customizable-attributes-view.component.html',
    styleUrls: ['./customizable-attributes-view.component.scss']
})
export class CustomizableAttributesViewComponent implements OnInit {

    @Input() attributeValues: any;

    @Input() transition: any;

    @Input() attributes;

    customizableAttributes = [];

    private notification: NotificationService = AppInjector.get(NotificationService);

    constructor(private tempService: TempService) {
    }

    ngOnInit() {
        this.prepareAttributes();
    }

    getColumnClass(item) {
        return (item?.attribute.type === 'STRING') ? 'col-12' : 'col-3';
    }

    prepareAttributes() {
        if (this.hasAttributesValue()) {
            this.setAttributesWithRelations(this.attributes);
            this.loadAllAttributes();
            this.orderCustomizableAttributes();
        }
    }

    hasAttributesValue() {
        return !(!this.attributeValues || !this.attributes);
    }

    private setAttributesWithRelations(attributes: any[]) {
        attributes.forEach(attribute => {
            this.getAttributeDependents(attributes, attribute);
        });
    }

    private getAttributeDependents(attributes, attribute) {
        attribute.dependents = attributes.filter(att => att.relatedAttribute?.title === attribute.title);
        attribute.dependents.forEach(dependent => {
            this.getAttributeDependents(attributes, dependent);
            for (let i = attributes.length - 1; i >= 0; --i) {
                if (attributes[i].title === dependent.title) {
                    attributes.splice(i, 1);
                }
            }
        });
    }

    loadAllAttributes() {
        for (const item of this.attributes) {
            this.loadAttribute(item, false);
        }
    }

    private loadAttribute(item, dependFromDeleted) {
        if (this.allowedAttribute(item.title)) {
            const attributeValue = this.getAttributeValue(item.id);
            if (dependFromDeleted) {
                attributeValue.value = undefined;
            }
            if (attributeValue && attributeValue.value && item.type === 'PEOPLE') {
                this.formatValuePeople(attributeValue, item);
            } else if (attributeValue && attributeValue.value && item.type === 'ORGANIZATION') {
                this.formatValueOrganization(attributeValue, item);
            } else {
                this.addAttributes(this.newAttribute(item, attributeValue));
            }
            if (item.dependents) {
                for (const dependent of item.dependents) {
                    this.loadAttribute(dependent, attributeValue.value?.deleted);
                }
            }
        }
    }

    private formatValueOrganization(attributeValue, item) {
        try {
            this.tempService.get(`${OrganizationURL.BASE}/${attributeValue.value}`).subscribe((organization: any) => {
                attributeValue.value = {
                    id: organization.id,
                    name: organization.name,
                    fantasyName: organization.fantasyName,
                    deleted: organization.deleted
                };
                this.addAttributes(this.newAttribute(item, attributeValue));
            });
        } catch (error) {
            this.notification.error(error.error.message);
        }
    }

    private formatValuePeople(attributeValue, item) {
        try {
            this.tempService.get(`${PeopleURL.BASE}/${attributeValue.value}`).subscribe((people: any) => {
                attributeValue.value = {
                    id: people.id,
                    name: people.name,
                    deleted: people.deleted
                };
                this.addAttributes(this.newAttribute(item, attributeValue));
            });
        } catch (error) {
            this.notification.error(error.error.message);
        }
    }

    allowedAttribute(item) {
        if (this.transition) {
            const attributes = this.transition.attributes.split(';');
            return attributes.includes(item);
        }
        return true;
    }

    private getAttributeValue(attId) {
        if (!this.attributeValues) {
            return undefined;
        }
        const index = this.attributeValues.findIndex((att) => {
            return att.attribute.id === attId;
        });
        return index > -1 ? this.attributeValues[index] : undefined;
    }

    addAttributes(attribute: any) {
        this.customizableAttributes.push(attribute);
    }

    newAttribute(attribute: any, attributeValue: any): any {
        return {
            id: attributeValue ? attributeValue.id : undefined,
            value: attributeValue ? this.getValueByType(attributeValue, attribute.type) : undefined,
            valueComplement: attributeValue?.valueComplement ? attributeValue.valueComplement : undefined,
            attribute: {
                id: attribute.id,
                title: attribute.title,
                type: attribute.type,
                listOptions: attribute.listValues ? attribute.listValues.split(';') : undefined,
                currency: attribute.currency ? attribute.currency : undefined
            }
        };
    }

    private getValueByType(attributeValue, type) {
        switch (type) {
            case 'DECIMAL':
                return +attributeValue.value;
            case 'BOOLEAN':
                return attributeValue.value === 'true';
            default:
                return attributeValue.value;
        }
    }

    orderCustomizableAttributes() {
        this.customizableAttributes.sort((a, b) => {
            return a.position < b.position ? -1 : a.position > b.position ? 1 : 0;
        });
    }
}
