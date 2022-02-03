package br.edu.ufcg.virtus.core.service.file;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;

import br.edu.ufcg.virtus.common.exception.BusinessException;
import br.edu.ufcg.virtus.core.dto.FilePathDTO;
import br.edu.ufcg.virtus.core.dto.FileWrapperDTO;
import br.edu.ufcg.virtus.core.util.FileUtil;

@Service
@Profile({"dev", "homolog", "prod", "infra"})
public class FileCloudService implements FileService {

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private TransferManager transferManager;

    final String prefix = System.getenv("BUILD_ENV");

    final String bucket = System.getenv("FILES_BUCKET");

    final String fileSeparator = "/";

    private static final Logger LOGGER = Logger.getLogger(FileCloudService.class.getName());

    @Override
    public FileWrapperDTO getFile(String fileName) throws BusinessException {
        final S3Object s3Object = this.getObject(fileName);
        try {
            final byte[] fileBytes = s3Object.getObjectContent().readAllBytes();
            final String mimeType = s3Object.getObjectMetadata().getContentType();
            final long contentLength = s3Object.getObjectMetadata().getContentLength();
            final FileWrapperDTO fileWrapper = new FileWrapperDTO(fileName, mimeType, contentLength, fileBytes);
            try {
                s3Object.close();
            } catch (final IOException exception) {
                FileCloudService.LOGGER.log(Level.WARNING, exception.getMessage());
                throw new BusinessException("could.not.download.file", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return fileWrapper;
        } catch (final IOException exception) {
            FileCloudService.LOGGER.log(Level.WARNING, exception.getMessage());
            throw new BusinessException("could.not.download.file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public String save(MultipartFile multipartFile, String objectName, String contentType) throws Exception {
        objectName = (objectName == null ? multipartFile.getOriginalFilename() : objectName);
        this.validateObject(objectName);

        final InputStream inputStream = multipartFile.getInputStream();

        final ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        objectName = this.prefix + this.fileSeparator + objectName;
        final PutObjectRequest putObjectRequest = new PutObjectRequest(this.getRoot(), objectName, inputStream, metadata);

        try {
            final Upload upload = this.transferManager.upload(putObjectRequest);

            upload.waitForCompletion();
            inputStream.close();
            return objectName;
        } catch (final AmazonServiceException exception) {
            inputStream.close();
            FileCloudService.LOGGER.log(Level.WARNING, "File already exists " + objectName);
            throw new BusinessException("could.not.save.file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public FilePathDTO saveFile(MultipartFile multipartFile, String contentType) throws Exception {
        final String fileName = FileUtil.generateNewFileName(multipartFile.getOriginalFilename());
        final FilePathDTO dto = new FilePathDTO(fileName, this.save(multipartFile, fileName, contentType),
                multipartFile.getOriginalFilename(), Math.toIntExact(multipartFile.getSize()), multipartFile.getContentType());
        return dto;
    }

    @Override
    public List<FilePathDTO> saveFiles(List<MultipartFile> files) throws Exception {
        final List<FilePathDTO> pathDTOS = new ArrayList<>();
        for (final MultipartFile file : files) {
            final FilePathDTO dto = this.saveFile(file, file.getContentType());
            pathDTOS.add(dto);
        }

        return pathDTOS;
    }

    @Override
    public void deleteFile(String fileName) throws BusinessException {
        try {
            fileName = this.prefix + this.fileSeparator + fileName;
            this.amazonS3.deleteObject(new DeleteObjectRequest(this.getRoot(), fileName));
        } catch (final AmazonServiceException exception) {
            FileCloudService.LOGGER.log(Level.WARNING, "Error removing file");
            throw new BusinessException("could.not.remove.file", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public String getRoot() {
        return this.bucket;
    }

    private S3Object getObject(String objectName) throws BusinessException {
        S3Object s3Object = null;
        try {
            final String key = this.prefix + this.fileSeparator + objectName;
            s3Object = this.amazonS3.getObject(this.getRoot(), key);
            return s3Object;
        } catch (final AmazonServiceException exception) {
            if (s3Object != null) {
                try {
                    s3Object.close();
                } catch (final IOException e) {
                    throw new BusinessException("could.not.download.file", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
            FileCloudService.LOGGER.log(Level.WARNING, "File not found");
            throw new BusinessException("file.not.found", HttpStatus.BAD_REQUEST);
        }
    }

    private void validateObject(String objectName) throws BusinessException {
        final String bucket = this.getRoot() + this.fileSeparator + this.prefix;
        final boolean objectExists = this.amazonS3.doesObjectExist(bucket, objectName);
        if (objectExists) {
            FileCloudService.LOGGER.log(Level.WARNING, "Could not save file");
            throw new BusinessException("file.already.exists", objectName, HttpStatus.BAD_REQUEST);
        }
    }

}
