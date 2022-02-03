package br.edu.ufcg.virtus.common.dto;

/**
 * DTO for File Upload
 */
public class FileDTO {
    /**
     * File URL
     */
    private String fileURL;
    /**
     * File Name
     */
    private String fileName;
    /**
     * File size
     */
    private Long fileSize;

    /**
     * FileDTO
     * @param fileURL file URL
     * @param fileName file name
     */
    public FileDTO(String fileURL, String fileName) {
        this.fileURL = fileURL;
        this.fileName = fileName;
        this.fileSize = 0L;
    }

    /**
     * FileDTO
     */
    public FileDTO() {
        this.fileURL = "";
        this.fileName = "";
        this.fileSize = 0L;
    }

    /**
     * Gets file name
     * @return {@link String} fileName
     */
    public String getFileName() {
        return fileName;
    }
    /**
     * Gets file URL
     * @return {@link String} fileURL
     */
    public String getFileURL() {
        return fileURL;
    }

    /**
     * Gets the file size in bytes
     * @return {@link Long} fileSize
     */
    public Long getFileSize() {
        return fileSize;
    }

    /**
     * Sets file name
     * @param fileName file name
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Sets file URL
     * @param fileURL file URL
     */
    public void setFileURL(String fileURL) {
        this.fileURL = fileURL;
    }

    /**
     * Sets file size
     * @param fileSize file size
     */
    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }
}
