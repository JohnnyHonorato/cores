package br.edu.ufcg.virtus.tracker.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.edu.ufcg.virtus.common.exception.BusinessException;
import br.edu.ufcg.virtus.tracker.service.file.FileService;
import io.swagger.annotations.Api;

@RestController
@RequestMapping("files")
@Api(value = "files", tags = "files-controller")
public class FileController {

    @Autowired
    private FileService service;
	
	@DeleteMapping
    public ResponseEntity<?> deleteListOfFiles(@RequestParam("ids") String ids) throws BusinessException, IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final List<Integer> idsList = mapper.readValue(ids, new TypeReference<List<Integer>>(){});
        this.service.deleteMultipleFiles(idsList);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
