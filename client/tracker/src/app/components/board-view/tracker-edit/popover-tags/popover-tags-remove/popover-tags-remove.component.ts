import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {NotificationService} from 'common';
import {TagService} from 'src/app/shared/services/tag.service';

@Component({
  selector: 'tracker-popover-tags-remove',
  templateUrl: './popover-tags-remove.component.html',
  styleUrls: ['./popover-tags-remove.component.scss']
})
export class PopoverTagsRemoveComponent implements OnInit {

  @Input() trackerModel;
  @Input() selectedTag;

  @Output() public openListTags: EventEmitter<any> = new EventEmitter();
  @Output() public removedTag: EventEmitter<any> = new EventEmitter();

  loading = false;

  constructor(private tagService: TagService, private notification: NotificationService) {
  }

  ngOnInit(): void {
  }

  removeTag() {
    this.tagService.removeTag(this.trackerModel.id, this.selectedTag.id).subscribe(
      () => {
        this.openListTags.emit();
        this.removedTag.emit(this.selectedTag);
        this.notification.deleteSuccess();
      }, (result) => {
        this.loading = false;
        this.openListTags.emit();
        this.notification.error(result.error.message);
    });
  }

}
