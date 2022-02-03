import {Component, EventEmitter, Input, Output} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {FileService, NotificationService} from 'common';
import MIMETYPES from '@shared/util/mime-types.json';
import {TrackerFileService} from '@shared/services/tracker-file-service.service';

@Component({
    selector: 'tracker-upload-multiple-files',
    templateUrl: './upload-multiple-files.component.html',
    styleUrls: ['./upload-multiple-files.component.scss']
})
export class UploadMultipleFilesComponent {

    @Output() successPostingFiles: EventEmitter<any> = new EventEmitter<any>();

    @Output() setRemovedFilesList: EventEmitter<any> = new EventEmitter<any>();

    @Input() isPreview = false;

    @Input() isView = false;

    @Input() set setFiles(attachments) {
        if (attachments) {
            this.files = attachments;
        }
    }

    @Input() set setFileTypesRestrictions(typesRestriction) {
        if (typesRestriction) {
            this.trackerFileService.fileTypesRestrictions = this.getAttachmentsProperties(typesRestriction);
        }
    }

    files: any[] = [];

    fileToRemove: any;

    indexFileToRemove: number;

    filesExtensions = [];

    constructor(
        private notification: NotificationService,
        private translateService: TranslateService,
        private fileService: FileService,
        private trackerFileService: TrackerFileService
    ) {
    }

    onFileDropped($event) {
        this.prepareFilesList($event);
    }

    fileBrowseHandler(files) {
        if (!this.isPreview) {
            this.prepareFilesList(files);
        }
    }

    getFileToRemoveData(index: number, file: any) {
        this.fileToRemove = file;
        this.indexFileToRemove = index;
    }

    deleteFile() {
        this.files.splice(this.indexFileToRemove, 1);
        if (this.fileToRemove.id) {
            this.setRemovedFilesList.emit(this.fileToRemove);
            this.fileToRemove = undefined;
            this.indexFileToRemove = undefined;
        }
    }

    clearFileList() {
        this.files = [];
    }

    prepareFilesList(attachments) {
        for (const item of attachments) {
            if (item) {
                this.insertOnFileList(item);
            } else {
                this.notification.error(this.translateService.instant('tracker.attached-file').replace('{{value}}', item.name));
            }
        }
    }

     validateAttachments(file) {
        if (this.trackerFileService.fileTypesRestrictions) {
            if (this.trackerFileService.validateAttachmentType(file)) {
                this.files.unshift(file);
            } else {
                this.notification.error(this.translateService.instant('tracker.file-type-restrictions')
                    .replace('{{value}}', this.trackerFileService.getExtByMimeType(file)));
            }
        } else {
            this.files.unshift(file);
        }
        this.addFilesListToCard(this.files);
    }

    validationGetTypes(types) {
        if (types) {
            const receiveTypes = types.split(';');
            const attachmentsTypes = [];
            receiveTypes.forEach((type) => {
                attachmentsTypes.push({ext: type.split(':')[0], type: type.split(':')[1]});
            });
            return attachmentsTypes;
        }
    }

    getAttachmentsProperties(data) {
        if (data) {
            const types = [];
            this.validationGetTypes(data).forEach((item) => {
                types.push(item.type);
                if (item.ext) {
                    this.filesExtensions.push(item.ext);
                }
            });
            return types;
        }
    }

    insertOnFileList(item, count: number = 0) {
        if (!this.files.find(file => file.name === item.name || file.originalName === item.name)) {
            this.validateAttachments(item);
        } else {
            this.renameFileNameAndInsert(item, count);
        }
    }

    private renameFileNameAndInsert(file: File, counter: number = 0) {
        const {renamedFile, count} = this.trackerFileService.renameFile(file, counter);
        this.insertOnFileList(renamedFile, count);
    }

    addFilesListToCard(files) {
        this.successPostingFiles.emit(files);
    }

    downloadFile(file) {
        this.fileService.getFile(file.name).subscribe((result: any) => {
            this.fileService.onDownload(result, file.originalName);
        }, (result) => {
            this.notification.error(result.error.message);
        });
    }

    formatBytes(bytes, decimals) {
        if (bytes === 0) {
            return '0 Bytes';
        }
        const k = 1024;
        const dm = decimals <= 0 ? 0 : decimals || 2;
        const sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'];
        const i = Math.floor(Math.log(bytes) / Math.log(k));
        return parseFloat((bytes / Math.pow(k, i)).toFixed(dm)) + ' ' + sizes[i];
    }

    noCollapse(event) {
        if (this.isPreview) {
            event.stopPropagation();
        }
    }

}
