package br.edu.ufcg.virtus.core.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.edu.ufcg.virtus.common.exception.BusinessException;
import br.edu.ufcg.virtus.common.security.authorization.Authorization;
import br.edu.ufcg.virtus.core.dto.FilePathDTO;
import br.edu.ufcg.virtus.core.dto.FileWrapperDTO;
import br.edu.ufcg.virtus.core.service.file.FileService;
import io.swagger.annotations.Api;

@RestController
@RequestMapping("file")
@Authorization("file")
@Api(value = "file", tags = "file-controller")
public class FileController {

    @Autowired
    private FileService service;

    @PostMapping
    public ResponseEntity<FilePathDTO> uploadFile(@RequestParam("file") MultipartFile file,
            @RequestParam(value = "ContentType", required = false) String contentType) throws Exception {
        final FilePathDTO filePathDTO = this.service.saveFile(file, contentType);
        return new ResponseEntity<>(filePathDTO, HttpStatus.OK);
    }

    @PostMapping(path = "/attachments")
    public ResponseEntity<List<FilePathDTO>> uploadListOfFiles(@RequestParam("files") List<MultipartFile> files) throws Exception {
        final List<FilePathDTO> pathDTOS = this.service.saveFiles(files);
        return new ResponseEntity<>(pathDTOS, HttpStatus.OK);
    }

    @GetMapping
    public HttpEntity<byte[]> downloadFile(@RequestParam("fileName") String fileName) throws BusinessException, IOException {
        final FileWrapperDTO fileWrapper = this.service.getFile(fileName);
        final HttpHeaders header = this.mountHTTPHeaders(fileWrapper.getMimeType(), fileWrapper.getFileLength(), fileWrapper.getFileName());

        return new ResponseEntity<>(fileWrapper.getFileBytes(), header, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteFile(@RequestParam("fileName") String fileName) throws BusinessException, IOException {
        this.service.deleteFile(fileName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private HttpHeaders mountHTTPHeaders(String mimeType, long contentLength, String fileName) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(mimeType));
        headers.setContentLength(contentLength);
        headers.set("Content-Disposition", "attachment; filename=" + fileName.replace(" ", "_"));
        return headers;
    }

}
