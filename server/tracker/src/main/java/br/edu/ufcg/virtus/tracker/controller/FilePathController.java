package br.edu.ufcg.virtus.tracker.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ufcg.virtus.common.controller.CrudBaseController;
import br.edu.ufcg.virtus.common.security.authorization.Authorization;
import br.edu.ufcg.virtus.common.security.authorization.ReadPermission;
import br.edu.ufcg.virtus.tracker.dto.FilePathDTO;
import br.edu.ufcg.virtus.tracker.model.FilePath;
import br.edu.ufcg.virtus.tracker.service.FilePathService;
import io.swagger.annotations.Api;

@RestController
@RequestMapping("file-path")
@Authorization("file-path")
@Api(value = "file-path", tags = "file-path-controller")
public class FilePathController extends CrudBaseController<FilePath, Integer, FilePathDTO> {

    @Autowired
    private FilePathService service;

    @Override
    protected FilePathService getService() {
        return this.service;
    }
    
	@GetMapping("/trackers/{trackerId}")
    @ReadPermission
    public ResponseEntity<List<FilePathDTO>> getFilePathByTrackerId(HttpServletRequest request,
            @PathVariable Integer trackerId) {
    	List<FilePathDTO> filePaths = toListDTO(this.service.getFilePathByTrackerId(trackerId));
        return new ResponseEntity<>(filePaths, HttpStatus.OK);
    }

}