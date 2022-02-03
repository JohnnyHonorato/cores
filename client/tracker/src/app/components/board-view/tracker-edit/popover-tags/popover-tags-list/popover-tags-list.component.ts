import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {TagService} from 'src/app/shared/services/tag.service';

@Component({
    selector: 'tracker-popover-tags-list',
    templateUrl: './popover-tags-list.component.html',
    styleUrls: ['./popover-tags-list.component.scss']
})
export class PopoverTagsListComponent implements OnInit {

    @Input() tracker: any;
    @Input() trackerModel: any;
    @Output() public selectedTag: EventEmitter<any> = new EventEmitter();
    @Output() public openEditTag: EventEmitter<any> = new EventEmitter();
    @Output() public openRemoveTag: EventEmitter<any> = new EventEmitter();

    public tags: any = [];

    loading = false;

    @Input() set editedTag(editedTag) {
        if (editedTag) {
            if (this.tags.length > 0) {
                this.mapEditedTags(editedTag);
            } else {
                this.tags.push(editedTag);
            }
        }
    }

    constructor(
        private tagService: TagService
    ) {
    }

    ngOnInit(): void {
        this.loading = true;
        this.openMainPage();
    }

    openMainPage() {
        this.loading = true;
        this.tagService.getTags(this.trackerModel.id).subscribe(
            (result: any) => {
                this.loading = false;
                if (result) {
                    if (!this.tracker && !this.tags.length) {
                        this.tags = this.mapResult(result);
                    } else {
                        this.addNewItemsToTagList(result);
                    }
                }
            }, (error) => {
                this.loading = false;
            }
        );
    }

    private addNewItemsToTagList(newItems) {
        let newTagsList = newItems;
        if (this.tracker && this.tracker.hasOwnProperty('tags')) {
            this.tracker.tags.map((tag) => {
                newTagsList = newTagsList.map((item) => {
                    if (item.id === tag.id) {
                        tag.isChecked = true;
                        return tag;
                    } else {
                        return item;
                    }
                });
            });
        }
        this.tags.map((tag) => {
            newTagsList = newTagsList.map((item) => {
                if (item.id === tag.id) {
                    this.selectedTag.emit(tag);
                    return tag;
                } else {
                    return item;
                }
            });
        });
        this.tags = newTagsList;
    }

    private mapResult(result) {
        if (result.length) {
            return result.map((tag: any) => {
                tag.isChecked = false;
                this.selectedTag.emit(tag);
                return tag;
            });
        }
        return [];
    }

    private mapEditedTags(editedTag: any) {
        this.tags = this.tags.map((tag) => {
            if (editedTag.id === tag.id) {
                return editedTag;
            } else {
                return tag;
            }
        });
    }
}
