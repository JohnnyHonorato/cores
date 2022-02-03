import {Component, ElementRef, EventEmitter, Input, Output, ViewChild} from '@angular/core';
import {QuickViewService} from '@shared/services/quick-view.service';
import {TrackerFileService} from '@shared/services/tracker-file-service.service';
import {FileService, NotificationService} from 'common';

@Component({
    selector: 'tracker-quick-view-attachment',
    templateUrl: './quick-view-attachment.component.html',
    styleUrls: ['./quick-view-attachment.component.scss']
})
export class QuickViewAttachmentComponent {

    @Input() set trackerId(value) {
        this.loading = true;
        this.searchId = value;
        this.service.getFilesPathByTrackerId(value).subscribe((result) => {
            this.items = result;
            this.loading = false;
            this.isDisplayed = true;
        }, (error) => {
            this.notification.error(error.error.message);
            this.loading = false;
        });
    }

    @Input() coordinatesCard: any;

    @Output() closeQuickView = new EventEmitter<any>();

    @ViewChild('dropDownMenu') dropdown: ElementRef;

    isDisplayed = false;

    public searchId;

    public items;

    public loading = false;

    constructor(
        private service: TrackerFileService,
        private fileService: FileService,
        private notification: NotificationService,
        private quickViewService: QuickViewService
    ) {
    }

    downloadFile(file) {
        this.fileService.getFile(file.name).subscribe((result: any) => {
            this.fileService.onDownload(result, file.originalName);
        }, (result) => {
            this.notification.error(result.error.message);
        });
    }

    onChangeDisplay() {
        this.isDisplayed = false;
        this.closeQuickView.emit();
    }

    getTop() {
        return this.quickViewService.getTop(this.coordinatesCard);
    }

    getLeft() {
        return this.quickViewService.getLeft(this.coordinatesCard);
    }

    getRight() {
        return this.quickViewService.getRight(this.coordinatesCard);
    }

    getBottom() {
        return this.quickViewService.getBottom(this.coordinatesCard, this.dropdown);
    }

}
