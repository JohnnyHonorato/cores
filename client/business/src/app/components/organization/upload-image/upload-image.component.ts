import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {DomSanitizer} from '@angular/platform-browser';
import {BaseComponent, FileService} from 'common';
import {BsModalRef, BsModalService} from 'ngx-bootstrap/modal';
import BlobFileConverter from './blob-file-converter-util';
import {ModalImageComponent} from './modal-image/modal-image.component';

@Component({
    selector: 'business-upload-image',
    templateUrl: './upload-image.component.html',
    styleUrls: ['./upload-image.component.scss'],
})

export class UploadImageComponent extends BaseComponent implements OnInit {

    @Output() successPostingImage: EventEmitter<any> = new EventEmitter<any>();

    @Output() successDeleteImage: EventEmitter<any> = new EventEmitter<any>();

    @Input()
    set setObjectValue(object) {
        if (object?.directory) {
            this.id = object.id;
            this.fileName = object.name;
            this.getImage(object.name);
        } else if (object?.file?.name) {
            this.imageBlob = object.file;
        }
    }

    modalRef: BsModalRef;

    loading = true;

    image: any;

    imageBlob: any;

    fileName;

    name = '';

    id;

    constructor(
        private sanitizer: DomSanitizer,
        private modalService: BsModalService,
        private fileService: FileService
    ) {
        super();
    }

    ngOnInit(): void {
        this.loading = false;
    }

    submitImage($event) {
        this.loading = true;
        if (this.id && this.fileName) {
            this.fileService.deleteFile(this.fileName).subscribe(result => {
                this.saveImage($event);
                this.fileName = null;
            });
        } else {
            this.saveImage($event);
        }
    }

    saveImage($event) {
        const img = BlobFileConverter.base64ToFile($event.image, $event.name);
        this.image = $event.image;
        this.successPostingImage.emit({file: img, name: $event.name, contentType: $event.ContentType});
        this.loading = false;
        this.modalRef.hide();
    }

    getImage(name) {
        this.loading = false;
        this.fileService.getFile(name).subscribe(result => {
            this.loading = false;
            this.imageBlob = result;
            this.name = name;
            const objectURL = URL.createObjectURL(result);
            this.image = this.sanitizer.bypassSecurityTrustUrl(objectURL);
        });
    }

    openCloseModal() {
        this.modalRef = this.modalService.show(ModalImageComponent, {
            initialState: {openModal: {image: this.imageBlob, name: this.name}},
            backdrop: 'static',
            keyboard: false,
            class: 'modal-lg modal-dialog-centered'
        });
        this.modalRef.content.saveImage.subscribe(result => {
            this.submitImage(result);
        });
    }

    deleteImage() {
        this.image = '';
        this.imageBlob = null;
        this.name = null;
        this.fileName = null;
        this.successDeleteImage.emit();
    }

}
