import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {NotificationService} from 'common';
import {TagService} from 'src/app/shared/services/tag.service';

@Component({
    selector: 'tracker-popover-tags',
    templateUrl: './popover-tags.component.html',
    styleUrls: ['./popover-tags.component.scss']
})
export class PopoverTagsComponent implements OnInit {

    @Input() tracker;
    @Input() trackerModel;

    @Output() public selectedTag: EventEmitter<any> = new EventEmitter();
    @Output() public removedTag: EventEmitter<any> = new EventEmitter();

    public loading = false;
    public tag;

    public page = 1;

    constructor(
        private tagService: TagService,
        private notification: NotificationService) {
    }

    ngOnInit(): void {
    }

    openPage(pageNumber, selectedTag = null) {
        this.page = pageNumber;
        this.tag = selectedTag;
    }

    selectTag(selectedTag) {
        this.tagService.getTag(this.trackerModel.id, selectedTag.id).subscribe(
            (tag: any) => {
                tag.isChecked = selectedTag.isChecked;
                this.selectedTag.emit(tag);
            }, (result) => {
                this.notification.error(result.error.message);
            }
        );
    }
}
