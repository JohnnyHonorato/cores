import {ActivatedRoute} from '@angular/router';
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Component, OnInit, ViewChild} from '@angular/core';
import {BaseEditComponent, CustomValidators, FileService, SelectSearchService} from 'common';
import {ChecklistURL, PeopleURL, TrackerModelURL, TrackerURL} from '@shared/util/url.domain';
import {Observable, Subject} from 'rxjs';
import {ChecklistService} from '@shared/services/checklist.service';
import {IndexStorageService} from '@shared/services/index-storage.service';
import {BsModalRef, BsModalService} from 'ngx-bootstrap/modal';
import {IndexedDbDraftModalType} from '@shared/constants/constants';
import {DraftModalComponent} from './draft-modal/draft-modal.component';
import {TrackerFileService} from '@shared/services/tracker-file-service.service';
import {CustomizableAttributesComponent} from '../../customizable-attributes/customizable-attributes.component';
import {UploadMultipleFilesComponent} from '../../upload-multiple-files/upload-multiple-files.component';

@Component({
    selector: 'tracker-tracker-edit',
    templateUrl: './tracker-edit.component.html',
    styleUrls: ['./tracker-edit.component.scss']
})
export class TrackerEditComponent extends BaseEditComponent implements OnInit {

    @ViewChild(CustomizableAttributesComponent, {static: false})
    customizableAttributesComponent: CustomizableAttributesComponent;

    @ViewChild(UploadMultipleFilesComponent, {static: false})
    uploadMultipleFilesComponent: UploadMultipleFilesComponent;

    cardId;

    status;

    trackerModel;

    searchItems: Observable<any[]>;

    searchCards: Observable<any[]>;

    searchLoading = false;

    searchCardsLoading = false;

    subjects = new Subject<string>();

    cardsSubjects = new Subject<string>();

    members: Array<any> = [];

    cards: Array<any> = [];

    validAttributesValue: boolean;

    checklists: any = [];

    checklistIndexToDelete: number;

    checklistItemIndexToDelete: number;

    removeChecklistItemSubject: Subject<void> = new Subject<void>();

    filesInformation;

    attachmentsToRemoveIdsList: any[] = [];

    draft: any;

    modalRef: BsModalRef;

    customAttributesDraft;

    constructor(
        private formBuilder: FormBuilder,
        private route: ActivatedRoute,
        private searchService: SelectSearchService,
        private checklistService: ChecklistService,
        private indexedDbService: IndexStorageService,
        private modalService: BsModalService,
        private fileService: FileService,
        private trackerFileService: TrackerFileService
    ) {
        super();
    }

    ngOnInit(): void {
        super.ngOnInit();
        this.setTrackerModel();
    }

    setTrackerModel() {
        this.route.params.subscribe(params => {
            this.service.get(`${TrackerModelURL.BASE}/${params.id}`).subscribe(result => {
                this.trackerModel = result;
                this.setStatus(params.statusId);
                this.initSearchPeople();
                this.initCardSearch();
                this.loadDraftValue();
            });
        });
    }

    setStatus(statusId) {
        this.service.get(`${TrackerModelURL.BASE}/${this.trackerModel.id}/status/${statusId}`).subscribe(response => {
            this.status = response;
        });
    }

    getFormControls() {
        return {
            id: new FormControl(undefined, []),
            title: new FormControl(undefined, [Validators.required, CustomValidators.noWhitespaceValidator, Validators.maxLength(255)]),
            description: new FormControl(undefined, [Validators.maxLength(255)]),
            dueDate: new FormControl(undefined, [CustomValidators.dateValidator('#due-date')]),
            status: new FormControl(this.status, []),
            tags: new FormArray([]),
            assignees: new FormControl(this.item ? this.item.assignees : []),
            links: new FormControl(this.item ? this.item.links : []),
            trackerModel: new FormControl(this.trackerModel, []),
            attributesValue: new FormControl(undefined, []),
            checklists: new FormArray([]),
            transition: new FormControl(undefined, []),
            attachments: new FormArray([])
        };
    }

    getRouterURL(): string {
        return `/tracker/${this.trackerModel.id}`;
    }

    getServiceURL() {
        return `${TrackerURL.BASE}`;
    }

    getActivatedRoute() {
        return this.route;
    }

    protected getItemIdKey(): string {
        return 'idCard';
    }

    postGetItem() {
        this.formattedDate();
        super.postGetItem();
        this.configureChecklists();
        this.configureTags();
        this.initMembersSelect();
        this.initDraft();
        this.initCardSelect();
    }

    private configureChecklists() {
        this.checklists = this.item.checklists;
        this.createChecklistFormArray();
    }

    private formattedDate() {
        if (this.item.dueDate) {
            this.item.dueDate = new Date(this.item.dueDate);
        }
    }

    private configureTags() {
        this.item.tags.forEach(element => {
            element.isChecked = true;
            this.addTag(element);
        });
    }

    get tags() {
        return this.editForm.get('tags') as FormArray;
    }

    addTag(selectedTag) {
        const selectedTagIndex = this.removedTag(selectedTag);
        if (selectedTag.isChecked) {
            if (selectedTagIndex) {
                this.tags.insert(Number(selectedTagIndex), this.formBuilder.group(selectedTag));
            } else {
                this.tags.push(this.formBuilder.group(selectedTag));
            }
            this.initDraft();
        }
        this.item.tags = this.editForm.get('tags').value;
    }

    removedTag(selectedTag) {
        for (const i in this.tags.controls) {
            if (this.tags.controls.hasOwnProperty(i)) {
                const item = this.tags.value[i];
                if (selectedTag.id === item.id) {
                    this.tags.removeAt(parseInt(i, 10));
                    return i;
                }
            }
        }
        return false;
    }

    initMembersSelect() {
        if (this.item && this.item.assignees) {
            const assignees = this.item.assignees as Array<any>;
            this.members = assignees.map(assignee => ({id: assignee.peopleId, name: assignee.name}));
        }
    }

    initSearchPeople() {
        const searchEndpoint = `${PeopleURL.BASE}/module/${this.trackerModel.moduleId}`;
        this.searchService.search('', searchEndpoint, 5, 'tracker.people.no-permission').subscribe((result) => {
            const people = result.map(item => {
                return item;
            });
            this.searchItems = this.searchService.startSubject(people, this.subjects, searchEndpoint, this.searchLoading);
        });
    }

    initCardSearch() {

        const searchEndpoint = `${TrackerURL.BASE}/related-trackers?trackerModelId=${this.trackerModel.id}`;
        this.searchService.search('', searchEndpoint + '&trackerTitle=', 5, 'tracker.no-permission', 'id').subscribe((result) => {
            const cards = result.map(item => {
                return item;
            });
            this.searchCards = this.searchService.startTrackerLinkSubject(cards, this.cardsSubjects, searchEndpoint,
                this.searchCardsLoading, 5, 'tracker.no-permission');
        });
    }

    getTransitionByStatus() {
        return this.getTransitionBySourceAndTarget(this.status, this.status);
    }

    getTransitionBySourceAndTarget(source, target) {
        return this.trackerModel.transitions.find(item => item.source.id === source.id && item.target.id === target.id);
    }

    setAttributesValue(event: any) {
        this.editForm.get('attributesValue').setValue(event);
        this.changePeopleOrganizationField(event);
    }

    private changePeopleOrganizationField(event) {
        event.forEach(item => {
            if (item.value != null &&
                (item.attribute.type === 'PEOPLE' || item.attribute.type === 'ORGANIZATION' || item.attribute.type === 'CURRENCY')) {
                this.mountDraftObject();
                this.saveCardDataDraft();
            }
        });
    }

    setCustomAttFormStatus(status: string): void {
        this.validAttributesValue = (status === 'VALID');
    }

    createChecklist(name: string) {
        const arrayControl = this.editForm.get('checklists') as FormArray;
        this.checklists.push({id: null, name, index: arrayControl.length, items: []});
        arrayControl.push(this.formBuilder.group({id: null, name, items: new FormArray([])}));
        this.initDraft();
    }

    updateChecklist(checklist: any) {
        const checklistGroup = (this.editForm.get('checklists') as FormArray).at(checklist.index) as FormGroup;
        checklistGroup.controls.name.setValue(checklist.name);
        this.initDraft();
    }

    addChecklistItem(checklistItem: any): void {
        const arrayControl = (((this.editForm.controls.checklists as FormArray)
            .at(checklistItem.checklistIndex)) as FormGroup).controls.items as FormArray;
        arrayControl.push(this.formBuilder.group({
            id: checklistItem.id,
            name: checklistItem.name,
            done: checklistItem.done
        }));
        this.initDraft();
    }

    changeChecklistItemValue(indexes: any): void {
        const item = (((((this.editForm.controls.checklists as FormArray)
            .at(indexes.checklistIndex)) as FormGroup).controls.items as FormArray).at(indexes.itemIndex)) as FormGroup;
        item.controls.done.setValue(!item.controls.done.value);
        this.initDraft();
    }

    updateChecklistItem(element: any): void {
        const item = (((((this.editForm.controls.checklists as FormArray)
            .at(element.checklistIndex)) as FormGroup).controls.items as FormArray).at(element.itemIndex)) as FormGroup;
        item.controls.name.setValue(element.name);
        this.checklists[element.checklistIndex].items[element.itemIndex].name = element.name;
        this.initDraft();
    }

    deleteChecklist(): void {
        const checklistId = this.checklists[this.checklistIndexToDelete].id;
        if (checklistId) {
            this.checklistService.deleteChecklist(checklistId).subscribe(
                () => {
                    this.removeChecklistFromArray();
                },
                (error) => {
                    this.notificationService.error(error.error.message);
                });
        } else {
            this.removeChecklistFromArray();
        }
        this.initDraft();
    }

    removeChecklistFromArray(): void {
        const checklistArray = (this.editForm.get('checklists') as FormArray);
        checklistArray.removeAt(this.checklistIndexToDelete);
        this.checklists.splice(this.checklistIndexToDelete, 1);
        this.notificationService.success();
        this.initDraft();
    }

    setChecklistIndexToDelete(index: number): void {
        this.checklistIndexToDelete = index;
    }

    deleteChecklistItem(): void {
        const checklistItemId = this.checklists[this.checklistIndexToDelete].items[this.checklistItemIndexToDelete].id;
        if (checklistItemId) {
            const checklistId = this.checklists[this.checklistIndexToDelete].id;
            this.service.delete(`${ChecklistURL.BASE}/${checklistId}/item/${checklistItemId}`).subscribe(
                () => {
                    this.removeChecklistItemFromArray();
                },
                (error) => {
                    this.notificationService.error(error.error.message);
                });
        } else {
            this.removeChecklistItemFromArray();
        }
        this.initDraft();
    }

    removeChecklistItemFromArray(): void {
        const checklistItemArray = ((((this.editForm.controls.checklists as FormArray)
            .at(this.checklistIndexToDelete)) as FormGroup).controls.items as FormArray);
        checklistItemArray.removeAt(this.checklistItemIndexToDelete);
        this.checklists[this.checklistIndexToDelete].items.splice(this.checklistItemIndexToDelete, 1);
        this.notificationService.success();
        this.removeChecklistItemSubject.next();
        this.initDraft();
    }

    setChecklistItemIndexesToDelete(element: any): void {
        this.checklistIndexToDelete = element.checklistIndex;
        this.checklistItemIndexToDelete = element.checklistItemIndex;
        this.initDraft();
    }

    receiveFiles(files) {
        this.filesInformation = files;
    }

    removeFromAttachmentsList(event: any) {
        this.attachmentsToRemoveIdsList.push(event.id);
    }

    deleteTracker() {
        this.service.remove(this.getServiceURL(), this.item.id).subscribe(result => {
                this.notification.deleteSuccess();
                this.navigate([`/tracker/${this.trackerModel.id}`]);
            },
            error => {
                this.notification.error(error.error ? error.error.message : error.message);
            }
        );
    }

    backToList() {
        if (this.getParamId()) {
            this.navigate([`/tracker/${this.trackerModel.id}/status/${this.status.id}/card/view/${this.getParamId()}`]);
        } else {
            this.navigate([`/tracker/${this.trackerModel.id}`]);
        }
    }

    initDraft(): void {
        if (!this.isEditMode) {
            this.mountDraftObject();
            this.saveCardDataDraft();
        }
    }

    mountDraftObject() {
        this.draft = {};
        const formValue = this.editForm.getRawValue();
        delete formValue.status;
        delete formValue.trackerModel;
        delete formValue.attributesValue;
        formValue.assignees = this.members;
        formValue.attachments = this.filesInformation;
        formValue.tags = this.tags.getRawValue();
        formValue.attributesValue = this.editForm.controls.attributesValue.value;
        this.draft = formValue;
    }

    saveCardDataDraft() {
        if (this.draft !== undefined) {
            this.indexedDbService.add('draft', {boardId: parseInt(this.trackerModel.id, 10), formValue: this.draft});
        }
    }

    startFlowToDiscardDataFromDraft() {
        this.indexedDbService.get('draft').then((draft: any) => {
            if (draft && !this.isEditMode) {
                this.openSaveDraftModal(IndexedDbDraftModalType.DISCARD_DATA);
            } else {
                this.backToList();
            }
        });
    }

    loadDraftValue() {
        this.indexedDbService.get('draft').then((draft: any) => {
            if (draft && (draft.boardId === parseInt(this.trackerModel.id, 10))) {
                this.openSaveDraftModal(IndexedDbDraftModalType.RETRIEVE_DATA);
            }
        });
    }

    openSaveDraftModal(type) {
        this.modalRef = this.modalService.show(DraftModalComponent,
            {
                initialState: {type},
                backdrop: 'static',
                keyboard: false,
                class: 'modal-dialog-centered'
            });
        this.modalRef.content.ok.subscribe(() => {
            this.modalRef.hide();
            this.modalDraftOkAction(type);
        });
        this.modalRef.content.no.subscribe(() => {
            this.modalRef.hide();
            this.modalDraftNotAction(type);
        });
        this.modalRef.content.closeModal.subscribe(() => {
            this.modalRef.hide();
        });
    }

    modalDraftNotAction(type) {
        if (type !== IndexedDbDraftModalType.RETRIEVE_DATA) {
            this.backToList();
        }
    }

    modalDraftOkAction(type) {
        if (type === IndexedDbDraftModalType.RETRIEVE_DATA) {
            this.retrieveCardDataFromDraft();
        } else {
            this.indexedDbService.remove('draft').then(r => {
                this.backToList();
            });
        }
    }

    retrieveCardDataFromDraft() {
        this.indexedDbService.get('draft').then((result: any) => {
            this.editForm.get('title').setValue(result.formValue.title);
            this.editForm.get('dueDate').setValue(result.formValue.dueDate);
            this.members = result.formValue.assignees;
            this.editForm.get('description').setValue(result.formValue.description);
            const formArray = result.formValue.tags ? result.formValue.tags : [];
            this.filesInformation = result.formValue.attachments;
            this.item.attachments = this.filesInformation;
            this.checklists = result.formValue.checklists ? result.formValue.checklists : [];
            this.editForm.get('attributesValue').setValue(result.formValue.attributesValue);
            this.customAttributesDraft = result.formValue.attributesValue;
            this.createChecklistFormArray();
            for (const item of formArray) {
                this.addTag(item);
            }
        });
    }

    createChecklistFormArray(): any {
        const arrayControl = this.editForm.get('checklists') as FormArray;
        this.checklists.forEach(element => {
            arrayControl.push(this.formBuilder.group({
                    id: new FormControl(element.id),
                    name: new FormControl(element.name),
                    items: this.createChecklistItemFormArray(element)
                })
            );
        });
        return arrayControl;
    }

    createChecklistItemFormArray(checklist: any): any {
        const arrayControl = new FormArray([]);
        checklist.items.forEach((item: any) => {
            arrayControl.push(this.formBuilder.group({
                    id: new FormControl(item.id),
                    name: new FormControl(item.name),
                    done: new FormControl(item.done)
                })
            );
        });
        return arrayControl;
    }

    protected postInsert(): void {
        this.indexedDbService.remove('draft');
        if (!this.item.id) {
            this.cleanForm();
        }
    }

    cleanForm() {
        this.checklists = [];
        this.members = [];
        this.cards = [];
        this.filesInformation = [];
        this.item.tags = [];
        this.cards = [];
        this.editForm.reset();
        this.initForm();
        this.customizableAttributesComponent.resetForm();
        this.uploadMultipleFilesComponent.clearFileList();
    }

    protected postUpdate() {
        this.indexedDbService.remove('draft');
    }

    protected preInsert() {
        this.getAssigneesFromSelectedPeople();
        this.editForm.controls.links.setValue(this.cards);
        this.editForm.controls.transition.setValue(this.getTransitionByStatus());
        this.editForm.controls.status.setValue(this.status);
        this.editForm.controls.trackerModel.setValue(this.trackerModel);
    }

    protected preUpdate() {
        this.editForm.controls.links.setValue(this.cards);
        this.getAssigneesFromSelectedPeople();
    }

    getAssigneesFromSelectedPeople() {
        const previousAssignees = this.item && this.item.assignees ? this.item.assignees as Array<any> : [];
        const assignees = previousAssignees.filter(assignee => this.members.map(person => person.id).includes(assignee.peopleId));
        this.members.forEach(person => {
            if (!assignees.some(assignee => assignee.peopleId === person.id)) {
                assignees.push({peopleId: person.id, name: person.name});
            }
        });
        this.editForm.controls.assignees.setValue(assignees);
    }

    protected insert(saveAndContinue) {
        if (this.filesInformation) {
            this.uploadAttachments(saveAndContinue);
        } else {
            super.insert(saveAndContinue);
        }
    }

    uploadAttachments(saveAndContinue?: boolean) {
        this.fileService.uploadListOfFiles(this.filesInformation).subscribe((result: any) => {
            this.getFormValue().attachments = result;
            this.isEditMode ? super.update() : super.insert(saveAndContinue);
        }, (result) => {
            this.submitting = false;
            this.notification.error(result.error.message);
        });
    }

    protected update() {
        if (this.attachmentsToRemoveIdsList && this.attachmentsToRemoveIdsList.length > 0) {
            this.deleteAttachmentsFromServer();
        } else {
            this.uploadFilesAndSaveTracker();
        }
    }

    deleteAttachmentsFromServer() {
        this.trackerFileService.deleteTrackerAttachments(this.attachmentsToRemoveIdsList).subscribe(() => {
            this.attachmentsToRemoveIdsList = [];
            this.uploadFilesAndSaveTracker();
        }, (result) => {
            this.submitting = false;
            this.notification.error(result.error.message);
        });
    }

    private uploadFilesAndSaveTracker() {
        if (this.filesInformation) {
            this.uploadAttachments();
        } else {
            super.update();
        }
    }

    initCardSelect() {
        this.cards = this.item.links;
    }

}
