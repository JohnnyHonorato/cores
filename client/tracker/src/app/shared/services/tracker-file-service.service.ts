import {Injectable} from '@angular/core';
import {FileService} from 'common';
import {environment} from '@environments/environment';
import {filePathURL, SERVER_URL} from '@shared/util/url.domain';
import {HttpParams} from '@angular/common/http';
import MIMETYPES from '@shared/util/mime-types.json';

@Injectable({
    providedIn: 'root'
})
export class TrackerFileService extends FileService {

    fileTypesRestrictions;

    constructor() {
        super();
    }

    deleteTrackerAttachments(ids: any[]) {
        return this.http.delete(SERVER_URL + 'tracker/v1/files', {
            params: new HttpParams().set('ids', JSON.stringify(ids)),
            headers: this.getHeaders()
        });
    }

    getFilesPathByTrackerId(trackerId) {
        return this.get(`${filePathURL.TRACKER}/${trackerId}`);
    }

    protected getApiKey(): string {
        return environment.wso2.trackerApikey;
    }

    public renameFile(file: File, count: number = 0) {
        count++;
        let renamedFile;
        let name: string = file.name;
        name = name.replace(`(${count - 1})`, '');
        for (let index = name.length; index > 0; index--) {
            if (name[index] === '.') {
                renamedFile = new File([file], this.generateNewFileName(index, name, count), {type: file.type});
                break;
            }
        }
        return {renamedFile, count};
    }

    private generateNewFileName(index: number, name: string, count: number) {
        return `${name.substr(0, index)}(${count})${name.substr(index, name.length)}`;
    }

    validateAttachmentType(file) { // files é o this.files
        return this.fileTypesRestrictions.includes(file?.type) && !file.id;
    }

    getExtByMimeType(file) {
        const getExtension = str => str.slice(str.lastIndexOf('.') + 1);
        for (const [extension, contentType] of Object.entries(MIMETYPES)) {
            if (getExtension(file.name) === extension && file.type === contentType) {
                return '.' + extension;
            }
        }
        return '';
    }

}
