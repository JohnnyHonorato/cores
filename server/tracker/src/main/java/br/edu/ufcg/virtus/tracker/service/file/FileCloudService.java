package br.edu.ufcg.virtus.tracker.service.file;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.DeleteObjectsRequest.KeyVersion;

import br.edu.ufcg.virtus.common.exception.BusinessException;
import br.edu.ufcg.virtus.tracker.model.FilePath;
import br.edu.ufcg.virtus.tracker.repository.FilePathRepository;

@Service
@Profile({"dev", "homolog", "prod", "infra"})
public class FileCloudService implements FileService {

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private FilePathRepository repository;

    final String prefix = System.getenv("BUILD_ENV");

    final String bucket = System.getenv("FILES_BUCKET");

    final String fileSeparator = "/";

    private static final Logger LOGGER = Logger.getLogger(FileCloudService.class.getName());

    @Override
    public void deleteMultipleFiles(List<Integer> ids) throws BusinessException {
        final List<FilePath> files = this.repository.findByIdIn(ids);
        this.deleteFiles(files);
    }

    @Override
    public void deleteFiles(List<FilePath> files) throws BusinessException {
        final List<KeyVersion> keys = new ArrayList<>();
        files.forEach((file) -> keys.add(new KeyVersion(this.prefix + this.fileSeparator + file.getName())));
        try {
            final DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(this.bucket).withKeys(keys);
            this.amazonS3.deleteObjects(deleteObjectsRequest);
            this.repository.deleteAll(files);
        } catch (final AmazonServiceException e) {
            FileCloudService.LOGGER.log(Level.WARNING, e.getErrorMessage());
            throw new BusinessException("could.not.remove.file", HttpStatus.BAD_REQUEST);
        }
    }

}
