package br.edu.ufcg.virtus.core.service.file;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.edu.ufcg.virtus.common.exception.BusinessException;
import br.edu.ufcg.virtus.core.dto.FilePathDTO;
import br.edu.ufcg.virtus.core.dto.FileWrapperDTO;

@Service
public interface FileService {

    FileWrapperDTO getFile(String fileName) throws BusinessException;

    String save(MultipartFile multipartFile, String fileName, String contentType) throws Exception;

    FilePathDTO saveFile(MultipartFile multipartFile, String contentType) throws Exception;

    List<FilePathDTO> saveFiles(List<MultipartFile> files) throws Exception;

    void deleteFile(String fileName) throws BusinessException;

    String getRoot();

}
