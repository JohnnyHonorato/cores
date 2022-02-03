package br.edu.ufcg.virtus.tracker.service.file;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.edu.ufcg.virtus.common.exception.BusinessException;
import br.edu.ufcg.virtus.tracker.model.FilePath;
import br.edu.ufcg.virtus.tracker.repository.FilePathRepository;

@Service
@Profile("default")
public class FileLocalService implements FileService {

    @Autowired
    private FilePathRepository repository;

    @Override
    public void deleteMultipleFiles(List<Integer> ids) throws BusinessException {
        for (final Integer id : ids) {
            final FilePath file = this.repository.findById(id);
            this.deleteFile(file.getDirectory());
            this.repository.delete(file);
        }
    }

    private void deleteFile(String path) throws BusinessException {
        final File file = new File(path);
        if (file.exists()) {
            file.delete();
        } else {
            throw new BusinessException("file.not.found", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteFiles(List<FilePath> files) throws BusinessException {
        for (final FilePath filePath : files) {
            this.deleteFile(filePath.getDirectory());
        }
    }

}
