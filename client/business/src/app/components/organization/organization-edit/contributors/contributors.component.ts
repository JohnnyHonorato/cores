import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {AppInjector, BaseComponent, CrudService, SelectSearchService} from 'common';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Observable} from 'rxjs/Observable';
import {Subject} from 'rxjs';
import {LeadURL} from '@shared/util/url.domain';

declare var $: any;

@Component({
    selector: 'business-contributors',
    templateUrl: './contributors.component.html',
    styleUrls: ['./contributors.component.scss']
})
export class ContributorsComponent extends BaseComponent implements OnInit {

    @Input() set setItems(item) {
        if (item) {
            this.items = item.peopleOrganizations ? item.peopleOrganizations : [];
        }
    }

    @Input()
    public itemId;

    @Output()
    public changeForm: EventEmitter<any> = new EventEmitter();

    public items = [];

    public type;

    public form: FormGroup;

    public searchItems: Observable<any[]>;

    public loadingComponent = false;

    public subjects = new Subject<string>();

    public selectedIndex = null;

    private index;

    private crudService: CrudService = AppInjector.get(CrudService);

    constructor(
        private formBuilder: FormBuilder,
        private searchService: SelectSearchService
    ) {
        super();
    }

    ngOnInit(): void {
        super.ngOnInit();
        this.form = this.getForm(null);
        this.initSearchItems();
    }

    getForm(item) {
        return this.formBuilder.group({
            id: new FormControl(item ? item.id : undefined, []),
            people: new FormControl(item ? item.people : undefined, [Validators.required]),
            department: new FormControl(item ? item.department : '', [Validators.maxLength(255)]),
            position: new FormControl(item ? item.position : '', [Validators.maxLength(255)])
        });
    }

    openModal() {
        $('#add-people-modal').modal();
    }

    initSearchItems() {
        this.searchService.search('', LeadURL.BASE, 5, 'lead.no-permission').subscribe((result) => {
            this.searchItems = this.searchService.startSubject(result, this.subjects, LeadURL.BASE, this.loadingComponent);
        });
    }

    resetForm() {
        this.selectedIndex = null;
        this.form = this.getForm(null);
    }

    addItem(value) {
        if (this.selectedIndex !== null) {
            this.items.splice(this.selectedIndex, 1);
            this.items.push(value);
        } else {
            this.items.push(value);
        }
        this.resetForm();
        this.changeForm.emit(this.items);
    }

    editItem(item, index) {
        this.selectedIndex = index;
        this.form = this.getForm(item);
    }

    setRemoveIndex(index) {
        this.index = index;
    }

    deleteItem() {
        this.items.splice(this.index, 1);
        this.changeForm.emit(this.items);
    }

}
