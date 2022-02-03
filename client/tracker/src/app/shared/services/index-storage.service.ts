import { Injectable } from '@angular/core';
declare var indexdb: any;
@Injectable({
  providedIn: 'root'
})
export class IndexStorageService {

  storageName = 'Way';

  constructor() { }

  add(keyName, value) {
    return new Promise(async (resolve, reject) => {
      if (indexdb !== undefined) {
        const request = await indexdb.transaction([this.storageName], 'readwrite').objectStore(this.storageName).put(value, keyName);
        request.onsuccess = (event) => {
          if (event.target.result) {
            resolve('success');
          } else {
            resolve(false);
          }
        };
        request.onerror = () => {
          console.error('indexdb add error');
        };
      }
    });

  }

  get(keyName) {
    return new Promise(async (resolve, reject) => {
      if (indexdb !== undefined) {
        const request = await indexdb.transaction([this.storageName], 'readwrite').objectStore(this.storageName).get(keyName);
        request.onsuccess = (event) => {
          if (event.target.result) {
            resolve(event.target.result);
          } else {
            resolve(false);
          }
        };
        request.onerror = () => {
          console.error('indexdb get error');
        };
      }
    });
  }

  remove(keyName) {
    return new Promise(async (resolve, reject) => {
      if (indexdb !== undefined) {
        const request = await indexdb.transaction([this.storageName], 'readwrite').objectStore(this.storageName).delete(keyName);
        request.onsuccess = (event) => {
          if (event.target.result) {
            resolve(event.target.value);
          } else {
            resolve(false);
          }
        };
        request.onerror = () => {
          console.error('indexdb remove error');
        };
      }
    });
  }
}
