import {Component, Input} from '@angular/core';
import {OrganizationURL, PeopleURL} from '@shared/util/url.domain';
import {TempService} from '@shared/services/temp.service';

@Component({
    selector: 'tracker-view-customizable-attributes',
    templateUrl: './view-customizable-attributes.component.html',
    styleUrls: ['./view-customizable-attributes.component.scss']
})
export class ViewCustomizableAttributesComponent {

    @Input() set attributes(object: { attributesValue: Array<any>, transition: any, attributes: any }) {
        if (object.attributesValue) {
            const attFiltered = object.attributesValue.filter(attrValue => attrValue.attribute?.showOnCard && attrValue.value);
            for (const item of attFiltered) {
                this.setValuePeople(item);
                this.setValueOrganization(item);
            }
            this.attributesList = this.filterAttributes(object);
        }
    }

    attributesList;

    constructor(
        private tempService: TempService
    ) {
    }

    convertAttributeList(listString) {
        return listString.split(';');
    }

    filterAttributesTrackerModel(object) {
        const filteredList = [];
        const transitionsAtt = this.convertAttributeList(object.transition.attributes);
        transitionsAtt.forEach(item => {
            const findAttTrackerModel = object.attributes.find(x => x.title === item);
            if (findAttTrackerModel) {
                filteredList.push(findAttTrackerModel);
            }
        });
        return filteredList;
    }

    filterAttributes(object) {
        const formattedTransitions = this.convertAttributeList(object.transition.attributes);
        const filteredList = [];
        const list = this.filterAttributesTrackerModel(object);
        object.attributesValue.forEach(item => {
            if (formattedTransitions.find(x => x === item.attribute.title)) {
                filteredList.push(item);
            }
        });
        list.forEach(item => {
            if (!filteredList.find(x => x.attribute.title === item.title)) {
                filteredList.push({id: 0, attribute: item});
            }
        });
        return filteredList;
    }

    private setValuePeople(item) {
        if (item.attribute.type === 'PEOPLE') {
            const id = item.value.id ? item.value.id : item.value;
            this.tempService.get(`${PeopleURL.BASE}/${id}`).subscribe(result => {
                item.value = result;
            });
        }
    }

    private setValueOrganization(item) {
        if (item.attribute.type === 'ORGANIZATION') {
            const id = item.value.id ? item.value.id : item.value;
            this.tempService.get(`${OrganizationURL.BASE}/${id}`).subscribe(result => {
                item.value = result;
            });
        }
    }
}
