package br.edu.ufcg.virtus.core.service.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.edu.ufcg.virtus.common.exception.BusinessException;
import br.edu.ufcg.virtus.common.util.MessageUtil;
import br.edu.ufcg.virtus.core.dto.FilePathDTO;
import br.edu.ufcg.virtus.core.dto.FileWrapperDTO;
import br.edu.ufcg.virtus.core.util.FileUtil;

@Service
@Profile("default")
public class FileLocalService implements FileService {

    private static final Logger LOGGER = Logger.getLogger(FileLocalService.class.getName());

    @Override
    public FileWrapperDTO getFile(String fileName) throws BusinessException {
        final String filePath = this.getRoot() + fileName;
        final File file = new File(filePath);
        if (file.exists()) {
            try {
                final byte[] fileBytes = Files.readAllBytes(file.toPath());
                final String mimeType = Files.probeContentType(file.toPath());
                final FileWrapperDTO fileWrapper = new FileWrapperDTO(fileName, mimeType, file.length(), fileBytes);
                return fileWrapper;
            } catch (final IOException e) {
                throw new BusinessException("could.not.download.file", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        throw new BusinessException("file.not.found", HttpStatus.NOT_FOUND);
    }

    @Override
    public String save(MultipartFile multipartFile, String name, String contentType) throws Exception {
        this.validateDirectory();
        String filePath;
        if (name == null) {
            filePath = this.getRoot() + multipartFile.getOriginalFilename();
        } else {
            filePath = this.getRoot() + name;
        }
        final File file = new File(filePath);
        this.validateFile(file);
        final FileOutputStream foStream = new FileOutputStream(filePath);
        foStream.write(multipartFile.getBytes());
        foStream.close();
        return filePath;
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
        final String path = this.getRoot() + fileName;
        final File file = new File(path);
        if (!file.exists() || !file.delete()) {
            throw new BusinessException("file.not.found", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public String getRoot() {
        return System.getProperty("user.home") + "/way/file/";
    }

    private void validateFile(File convFile) throws Exception {
        if (!convFile.createNewFile()) {
            FileLocalService.LOGGER.log(Level.WARNING, "File already exists " + convFile.getName());
            final String fileAlreadyExistsMessage = MessageUtil.findMessage("file.already.exists");
            final String formattedMessage = String.format(fileAlreadyExistsMessage, convFile.getName());
            throw new BusinessException(formattedMessage, HttpStatus.BAD_REQUEST);
        }
    }

    private void validateDirectory() throws BusinessException {
        final File directory = new File(this.getRoot());
        if (!directory.exists() && !directory.mkdirs()) {
            FileLocalService.LOGGER.log(Level.WARNING, "Could not create directory ");
            throw new BusinessException("could.not.create.directory", HttpStatus.BAD_REQUEST);
        }
    }
}
