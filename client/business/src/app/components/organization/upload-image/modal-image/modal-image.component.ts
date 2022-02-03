import { OnInit, ElementRef, Input, Output, EventEmitter } from '@angular/core';
import { Component } from '@angular/core';
import { ImageCroppedEvent } from 'ngx-image-cropper';
import { ViewChild } from '@angular/core';
import { BaseComponent } from 'common';
import BlobFileConverter from '../blob-file-converter-util';
import { BsModalRef } from 'ngx-bootstrap/modal';

@Component({
    selector: 'business-modal-image',
    templateUrl: './modal-image.component.html',
    styleUrls: ['./modal-image.component.scss']
})
export class ModalImageComponent extends BaseComponent implements OnInit {

    @Input() set openModal(modal: any) {
        if (modal.image?.lastModifiedDate) {
            this.imgChangeEvt = modal.image;
        }
        else if (modal.image) {
            this.imgChangeEvt = BlobFileConverter.blobToFile(modal.image, modal.name ? modal.name : 'fim');
        }

    }

    @Output() saveImage: EventEmitter<any> = new EventEmitter<any>();

    imgChangeEvt: any = '';

    cropImgPreview: any = '';

    format = '';

    possibleImageTypes = ['image/png', 'image/jpeg', 'image/jpg'];

    maxSize = 10;

    cropperPosition;

    isUpdate = false;

    @ViewChild('myInputFile')
    myInputFile: ElementRef;

    constructor(public modalRef: BsModalRef) {
        super();
    }

    save() {
        // tslint:disable-next-line:deprecation
        const img = BlobFileConverter.base64ToFile(this.cropImgPreview, name);
        this.saveImage.emit({ image: this.cropImgPreview, name: this.getName(), ContentType: img.type });
    }

    clear() {
        this.imgChangeEvt = '';
        this.cropImgPreview = '';
        this.myInputFile.nativeElement.value = '';
        this.format = '';
    }

    getFormatImage() {
        return this.imgChangeEvt.target?.files[0].type.replace('image/', '');
    }

    onFileChange(event: any): void {
        this.isUpdate = true;
        this.imgChangeEvt = event;
        this.validateImage();
    }

    getName() {
        return this.imgChangeEvt.target === undefined ? this.imgChangeEvt.name : this.imgChangeEvt.target.files[0].name;
    }

    isCrop(event: ImageCroppedEvent) {
        return this.cropperPosition !== undefined && this.cropperPosition !== event.cropperPosition;
    }

    cropImg(event: ImageCroppedEvent) {
        if (this.cropperPosition === undefined) {
            this.cropperPosition = event;
        } else if (this.isCrop(event)) {
            this.isUpdate = true;
        }
        this.cropImgPreview = event.base64;
    }

    validateImage() {
        const MIMEtype = this.imgChangeEvt.target.files[0].type;
        const size = this.imgChangeEvt.target.files[0].size / 1024 / 1024;
        if (!this.possibleImageTypes.includes(MIMEtype)) {
            this.imgFailed();
        } else if (size > this.maxSize) {
            this.clear();
            this.notificationService.error(this.translateService.instant('image.invalid-size'));
        }
    }

    imgFailed() {
        this.clear();
        this.notification.error(this.translateService.instant('image.invalid-type'));
    }

}
