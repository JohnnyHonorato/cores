import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute} from '@angular/router';
import {BaseEditComponent, CustomValidators, SearchService} from 'common';
import {BusinessOpportunityURL, OrganizationURL, PeopleURL} from '@shared/util/url.domain';
import {TempService} from '@shared/service/temp.service';

@Component({
    selector: 'business-opportunity-edit',
    templateUrl: './business-opportunity-edit.component.html',
    styleUrls: ['./business-opportunity-edit.component.css']
})
export class BusinessOpportunityEditComponent extends BaseEditComponent implements OnInit {

    public organizations: any;

    public peoples: any[];

    public showRepresentativeFormList = false;

    public representativeForm: FormGroup;

    public idRepresentative = null;

    collapseOne = false;

    constructor(
        private searchService: SearchService,
        private tempService: TempService,
        private route: ActivatedRoute
    ) {
        super();
    }

    ngOnInit(): void {
        super.ngOnInit();
        this.initRepresentativeForm();
        this.loadOrganizations();
    }

    initRepresentativeForm() {
        this.representativeForm = this.getRepresentativeFormGroup(null);
    }

    getFormControls(): {} {
        return {
            id: new FormControl(undefined, []),
            title: new FormControl(undefined, [Validators.required, Validators.maxLength(255),
                CustomValidators.noWhitespaceValidator]),
            organizationId: new FormControl(undefined, [Validators.required]),
            description: new FormControl(undefined, [Validators.required, Validators.maxLength(5000),
                CustomValidators.noWhitespaceValidator]),
            representatives: new FormControl([])
        };
    }

    getActivatedRoute(): ActivatedRoute {
        return this.route;
    }

    getServiceURL(): string {
        return BusinessOpportunityURL.BASE;
    }

    getRouterURL(): string {
        return '/business-oportunities';
    }

    getYesOrNoValue(value: boolean) {
        return value ? 'yes' : 'no';
    }

    postGetItem() {
        super.postGetItem();
        if (this.isEditMode) {
            this.loadLeadsByOrganization(this.item.organizationId);
        }
    }

    loadOrganizations() {
        this.searchService.getAll(OrganizationURL.BASE).subscribe((data: any) => {
                this.organizations = data.items;
            }, error => {
                this.notification.error(this.translateService.instant('business-opportunity.error-on-load'));
            },
        );
    }

    editItem(item, index) {
        this.idRepresentative = index;
        this.setRepresentForm(item);
    }

    setRepresentForm(item) {
        if (item) {
            this.representativeForm.get('peopleId').setValue(parseInt(item.peopleId, 10));
            this.representativeForm.get('signatory').setValue(item.signatory);
            this.representativeForm.get('companyRepresentative').setValue(item.companyRepresentative);
            this.representativeForm.get('technicalRepresentative').setValue(item.technicalRepresentative);
        }
    }

    populateRepresentativesList() {
        if (this.item.representatives) {
            this.showRepresentativeFormList = true;
            this.item.representatives.forEach(representative => {
                const representativeName = this.getRepresentativeName(representative.peopleId);
                representative.name = representativeName;
            });
        }
    }

    addRepresentative(): void {
        if (!(this.idRepresentative === null && !this.representativeNotOnList())) {
            this.setRemoveIndex(this.idRepresentative);
        }
        const representative = this.representativeForm.value;
        representative.name = this.getRepresentativeName(representative.peopleId);
        this.item.representatives.push(representative);
        this.idRepresentative = null;
        this.resetForm();
    }

    private getRepresentativeName(representativeId) {
        return this.peoples.find(people => people.id === parseInt(representativeId, 10)).name;
    }

    representativeNotOnList() {
        const peopleIdNum = +this.representativeForm.get('peopleId').value;
        if (this.item.representatives.find(rep => rep.peopleId === peopleIdNum)) {
            this.notification.error(this.translateService.instant('business-opportunity.representative-already-in-list'));
            return true;
        } else {
            return false;
        }
    }

    getRepresentativeFormGroup(representative) {
        return new FormGroup(
            {
                peopleId: new FormControl(representative ? parseInt(representative.peopleId, 10) : undefined,
                    Validators.required),
                signatory: new FormControl(representative ? representative.signatory : false, Validators.required),
                companyRepresentative: new FormControl(
                    representative ? representative.companyRepresentative : false, Validators.required),
                technicalRepresentative: new FormControl(
                    representative ? representative.technicalRepresentative : false, Validators.required)
            });
    }

    setRemoveIndex(index: number): void {
        this.item.representatives.splice(index, 1);
        this.editForm.get('representatives').setValue(this.item.representatives);
    }

    onChangeOrganization(organizationId) {
        this.resetForm();
        this.item.representatives = [];
        this.editForm.get('representatives').setValue(this.item.representatives);
        this.loadLeadsByOrganization(organizationId);
    }

    resetForm() {
        this.idRepresentative = null;
        this.representativeForm = this.getRepresentativeFormGroup(null);
    }

    loadLeadsByOrganization(organizationId) {
        this.tempService.get(PeopleURL.BASE + `/organizations/${organizationId}`).subscribe((data: any) => {
            this.peoples = data;
            this.populateRepresentativesList();
        });
    }

}
