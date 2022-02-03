import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {BaseEditComponent, CustomValidators} from 'common';
import {TagService} from '@shared/services/tag.service';

@Component({
    selector: 'tracker-popover-tags-edit',
    templateUrl: './popover-tags-edit.component.html',
    styleUrls: ['./popover-tags-edit.component.scss']
})
export class PopoverTagsEditComponent extends BaseEditComponent implements OnInit {

    @Input() trackerModel;

    @Input() selectedTag;

    @Output() public openListTags: EventEmitter<any> = new EventEmitter();

    defaultTagColor = '#132565';

    color;

    loading = false;

    constructor(
        private tagService: TagService
    ) {
        super();
    }

    ngOnInit(): void {
        this.editForm = new FormGroup(this.getFormControls(), {});
        this.changeColor(this.defaultTagColor);
        if (this.selectedTag) {
            this.getTagAndPatchForm(this.selectedTag);
        }
    }

    getFormControls(): {} {
        return {
            id: new FormControl(undefined, []),
            name: new FormControl(undefined, [Validators.required, Validators.maxLength(255), CustomValidators.noWhitespaceValidator]),
            color: new FormControl(undefined, []),
            trackerModel: new FormControl(this.trackerModel, [])
        };
    }

    getServiceURL(): string {
        return '';
    }

    getRouterURL(): string {
        return '';
    }

    changeColor(color) {
        this.editForm.get('color').setValue(color);
        this.color = color;
    }

    saveTag() {
        this.tagService.saveTag(this.trackerModel.id, this.editForm.value).subscribe(
            (tag: any) => {
                tag.isChecked = this.selectedTag?.isChecked;
                this.openListTags.emit(tag);
                this.notification.saveSuccess();
            }, (result) => {
                this.notification.error(result.error.message);
            }
        );
    }

    getTagAndPatchForm(tag) {
        this.loading = true;
        this.tagService.getTag(this.trackerModel.id, tag.id).subscribe(
            (resultTag: any) => {
                this.color = resultTag.color;
                resultTag.isChecked = tag.isChecked;
                this.editForm.patchValue(resultTag);
                this.loading = false;
            }, (result) => {
                this.notification.error(result.error.message);
                this.loading = false;
            }
        );
    }

}
