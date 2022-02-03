window.indexedDB = window.indexedDB || window.mozIndexedDB || window.webkitIndexedDB || window.msIndexedDB;

window.IDBTransaction = window.IDBTransaction || window.webkitIDBTransaction || window.msIDBTransaction;

window.IDBKeyRange = window.IDBKeyRange || window.webkitIDBKeyRange || window.msIDBKeyRange;

if (!window.indexedDB) {
    alert('Seu navegador não suporta indexDB')
}

var indexdb;

var request = window.indexedDB.open('way', 1);

request.onerror = (event) => {
    alert('error: ' + event.target.result);
}

request.onsuccess = (event) => {
    indexdb = request.result;
}

request.onupgradeneeded = (event) => {
    var indexdb = event.target.result;
    var objectStore = indexdb.createObjectStore('Way');
}
