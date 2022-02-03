export default class BlobFileConverter {

    static blobToBase64(blob) {
        const reader = new FileReader();
        reader.readAsDataURL(blob);
        reader.onloadend = () => {
            return reader.result;
        };
    }

    static base64ToFile(base64, filename) {
        const arrBase64 = base64.split(',');
        const mime = arrBase64[0].match(/:(.*?);/)[1];
        const bstr = atob(arrBase64[1]);
        let n = bstr.length;
        const u8arr = new Uint8Array(n);
        while (n--) {
            u8arr[n] = bstr.charCodeAt(n);
        }
        return new File([u8arr], filename, {type: mime});
    }

    static blobToFile(theBlob, fileName){
        theBlob.lastModifiedDate = new Date();
        theBlob.name = fileName;
        return theBlob;
    }

}
