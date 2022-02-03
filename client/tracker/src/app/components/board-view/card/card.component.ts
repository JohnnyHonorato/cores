import {Component, ElementRef, EventEmitter, Input, Output, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {QuickViewConstants} from '@shared/constants/constants';
import {TranslateService} from '@ngx-translate/core';
import {ToastrService} from 'ngx-toastr';
import {FileService, NotificationService} from 'common';
import {TrackerService} from '@shared/services/tracker.service';
import {TrackerFileService} from '@shared/services/tracker-file-service.service';

@Component({
    selector: 'tracker-card',
    templateUrl: './card.component.html',
    styleUrls: ['./card.component.scss']
})
export class CardComponent {

    @Input()
    set setItem(object) {
        if (object) {
            this.item = object;
        }
    }

    @Input() boardName: string;

    @Output() filterCard: EventEmitter<any> = new EventEmitter<any>();

    @ViewChild(QuickViewConstants.ATTACHMENT_ICON) attachmentIcon: ElementRef;

    @ViewChild(QuickViewConstants.COMMENT_ICON) commentIcon: ElementRef;

    @ViewChild('numberComments') numberComments: ElementRef;

    @ViewChild('card') set content(content: ElementRef) {
        if (content) {
            this.setDragAndDropStyle(content.nativeElement);
        }
    }

    isComment = false;

    isAttachment = false;

    item;

    iconCoordinates;

    files: any[];

    constructor(
        private router: Router,
        private route: ActivatedRoute,
        private translateService: TranslateService,
        private toastService: ToastrService,
        private fileService: FileService,
        private trackerService: TrackerService,
        public notification: NotificationService,
        private trackerFileService: TrackerFileService
    ) {
    }

    filterByTag(event, tag) {
        event.stopPropagation();
        this.filter(tag);
    }

    filter(filterItem) {
        this.filterCard.emit(filterItem);
    }

    updateCountComments() {
        this.item.numberComments += 1;
    }

    emitShowQuickView(type) {
        if (type === QuickViewConstants.COMMENT) {
            this.openComments();
        } else if (type === QuickViewConstants.ATTACHMENT) {
            this.openAttachments();
        }
    }

    openComments() {
        this.openOrCloseQuickViewComment();
        this.setCoordinates(this.commentIcon);
    }

    openAttachments() {
        this.openOrCloseQuickViewAttachment();
        this.setCoordinates(this.attachmentIcon);
    }

    setCoordinates(element) {
        this.iconCoordinates = {
            left: (element.nativeElement.getBoundingClientRect().left - 6),
            top: (element.nativeElement.getBoundingClientRect().top + 10),
            bottom: (element.nativeElement.getBoundingClientRect().bottom - 38),
            direction: this.getDirection()
        };
    }

    private getDirection() {
        return (document.body.getBoundingClientRect().height - this.numberComments.nativeElement.getBoundingClientRect().y) < 265 ? 'UP' : 'DOWN';
    }

    openOrCloseQuickViewComment() {
        this.isComment = !this.isComment;
    }

    openOrCloseQuickViewAttachment() {
        this.isAttachment = !this.isAttachment;
    }

    navigateToCard(id) {
        this.route.params.subscribe(
            (params) => {
                this.router.navigate([`/tracker/${params.id}/status/${this.item.transition.target.id}/card/view/${id}`]);
            }
        );
    }

    navigateToLinkedCard(value) {
        this.router.navigate([`tracker/${value.trackerModel.id}/status/${value.status.id}/card/view/${value.id}`]);
    }

    onFileDropped(event) {
        const indexUploadToast = this.showDropFileToast(event);
        this.files = Array.from(event);
        this.uploadAttachments(indexUploadToast);
    }

    getToastMessageToFiles(files, cardId, boardName) {
        let message;
        message = `${this.translateService.instant('tracker.card.send-file')} ${files.length} ${this.translateService.instant('tracker.card.file-lower-case')}${files.length > 1 ? '(s)' : ''} ` +
            `${this.translateService.instant('tracker.card.send-file-2')} #${cardId} ${this.translateService.instant('tracker.card.send-file-3')} ${boardName}`;
        return message;
    }

    showDropFileToast(files) {
        const toast = this.toastService.show(`<div><div class="loader loader-position"></div><div>${
                this.getToastMessageToFiles(files, this.item.id, this.boardName)}</div></div>`,
            `${this.translateService.instant('tracker.card.upload-file')}`,
            { disableTimeOut: true, tapToDismiss: false, progressBar: true,
                enableHtml: true, toastClass: 'ngx-toastr upload-toast-color' });
        const index = toast.toastId;
        return index;
    }

    uploadAttachments(indexUploadToast) {
        this.files.forEach(file => {
            const fileIndex: number = this.files.indexOf(file);
            this.validateFile(file, fileIndex, indexUploadToast);
        });
        if (this.files && this.files.length > 0) {
            this.fileService.uploadListOfFiles(this.files).subscribe((result: any) => {
                this.trackerFilesUpload(indexUploadToast, result);
            }, (result) => {
                this.deleteToast(indexUploadToast);
                this.notification.error(result.error.message);
            });
        }
    }

    validateFile(file, fileIndex, indexUploadToast, counter: number = 0) {
        if (this.item.attachments.find(att => att.name === file.name || att.originalName === file.name)) {
            const {renamedFile, count} = this.trackerFileService.renameFile(file, counter);
            this.validateFile(renamedFile, fileIndex, indexUploadToast, count);
        } else {
            this.validateFileTypeAndUpdateCardAttachments(file, fileIndex, indexUploadToast);
        }
    }

    validateFileTypeAndUpdateCardAttachments(file, fileIndex, indexUploadToast) {
        if (this.trackerFileService.fileTypesRestrictions && !this.trackerFileService.validateAttachmentType(file)) {
            this.notification.error(this.translateService.instant('tracker.file-type-restrictions')
                .replace('{{value}}', this.trackerFileService.getExtByMimeType(file)));
            this.files.splice(fileIndex, 1);
        } else {
            this.files[fileIndex] = file;
        }
    }

    private trackerFilesUpload(indexUploadToast, filesToSave): void {
        this.trackerService
            .updatePartialTracker({ id: this.item.id, attachments: filesToSave })
            .subscribe(
                result => {
                    this.item.attachments = [...filesToSave, ...this.item.attachments];
                    this.deleteToast(indexUploadToast);
                    this.notification.successText(this.translateService.instant('tracker.card.attachment.file-upload-successful'));
                },
                error => {
                    this.deleteToast(indexUploadToast);
                    this.notification.error(
                        error.error ? error.error.message : error.message
                    );
                }
            );
    }

    deleteToast(index) {
        this.toastService.clear(index);
    }

    private setDragAndDropStyle(element) {
        element.addEventListener('dragover', changeTrackerBackground, false);
        element.addEventListener('dragleave', changeBackTrackerBackground, false);
        element.addEventListener('drop', changeBackTrackerBackground, false);

        function changeTrackerBackground() {
            element.style.backgroundColor = '#EFF2AA';
        }

        function changeBackTrackerBackground() {
            element.style.backgroundColor = 'white';
        }
    }

}
