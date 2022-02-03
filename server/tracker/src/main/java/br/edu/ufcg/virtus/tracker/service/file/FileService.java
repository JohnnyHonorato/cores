package br.edu.ufcg.virtus.tracker.service.file;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.ufcg.virtus.common.exception.BusinessException;
import br.edu.ufcg.virtus.tracker.model.FilePath;

@Service
public interface FileService {

    void deleteMultipleFiles(List<Integer> ids) throws BusinessException;

    void deleteFiles(List<FilePath> files) throws BusinessException;

}
