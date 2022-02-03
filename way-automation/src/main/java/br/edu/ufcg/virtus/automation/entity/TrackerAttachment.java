package br.edu.ufcg.virtus.automation.entity;

public class TrackerAttachment {
	
	private String originalName;
	private String fileSize;
	
	public TrackerAttachment(String originalName, String fileSize) {
		this.originalName = originalName;
		this.fileSize = fileSize;
	}
	
	public String getOriginalName() {
		return originalName;
	}
	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

}
