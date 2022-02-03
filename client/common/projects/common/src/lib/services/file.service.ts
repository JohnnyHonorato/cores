import {Injectable} from '@angular/core';
import {HttpHeaders, HttpParams} from '@angular/common/http';
import {FileURL} from '../util/url.domain';
import {BaseService} from './base.service';

@Injectable()
export class FileService extends BaseService {

    constructor() {
        super();
    }

    upload(file: any, contentType: string) {
        const body = new FormData();
        body.append('file', file);
        body.append('ContentType', contentType);
        return this.http.post(this.getServerURL() + FileURL.BASE, body, {headers: this.getHeaders()});
    }

    getFile(fileName: string) {
        return this.http.get(this.getServerURL() + FileURL.BASE, {
            responseType: 'blob',
            params: new HttpParams().set('fileName', fileName),
            headers: this.getHeaders()
        });
    }

    uploadListOfFiles(files: any) {
        const body = new FormData();
        files.forEach(file => {
            body.append('files', file);
        });
        return this.http.post(this.getServerURL() + FileURL.MULTIPLE_FILE, body, {headers: this.getHeaders()});
    }

    deleteFile(fileName: string) {
        return this.http.delete(this.getServerURL() + FileURL.BASE, {
            params: new HttpParams().set('fileName', fileName),
            headers: this.getHeaders()
        });
    }

    onDownload(response: any, nome: string): void {
        const dataType = response.type;
        const binaryData = [];
        binaryData.push(response);
        const downloadLink = document.createElement('a');
        downloadLink.href = window.URL.createObjectURL(new Blob(binaryData, {type: dataType}));
        downloadLink.setAttribute('download', nome);
        document.body.appendChild(downloadLink);
        downloadLink.click();
    }

    protected getHeaders(): HttpHeaders {
        let httpHeaders: HttpHeaders;
        const idToken = sessionStorage.getItem('id_token');
        if (idToken) {
            httpHeaders = new HttpHeaders()
                .set('Accept-Language', this.translateService.getLang())
                .set('apikey', this.getApiKey())
                .set('Authorization', `Bearer ${idToken}`);
        } else {
            httpHeaders = new HttpHeaders()
                .set('Accept-Language', this.translateService.getLang())
                .set('apikey', this.getApiKey());
        }
        return httpHeaders;
    }

    protected getApiKey(): string {
        return this.environment.getCoreApiKey();
    }
}
