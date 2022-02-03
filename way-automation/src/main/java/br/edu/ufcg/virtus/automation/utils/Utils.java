package br.edu.ufcg.virtus.automation.utils;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import org.apache.commons.io.FileUtils;

import br.edu.ufcg.virtus.automation.dataset.ConfigData;


public class Utils {

	public static String getEnvironmentUserPath() {
		return System.getProperty("user.home");
	}

	public static String getProjectPath() {
		return System.getProperty("user.dir");
	}

	public static Boolean isFileExists(String filePath) {
		File f = new File(filePath);
		return f.exists();
	}
	
	public static int countFiles(String path) {
		File file = new File(path);
		File[] files = file.listFiles();	
		return files.length;
	}
	
	public static void clearFilesDirectory() throws IOException {
		File f = new File(Utils.getEnvironmentUserPath() + ConfigData.FILES_DIRECTORY);
		if (!f.exists())
			f.mkdirs();
		FileUtils.cleanDirectory(new File(Utils.getEnvironmentUserPath() + ConfigData.FILES_DIRECTORY));
	}
	
	public static void deleteFile(String filePath) throws IOException {
		File f = new File(filePath);
		if (!f.exists())
			f.mkdirs();
		FileUtils.forceDelete(f);
	}
	
	public static void copyFile(String file, String destination) {
		File source = new File(file);
		File destinationPath = new File(destination);
		try {
			FileUtils.copyToDirectory(source, destinationPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getTimeStamp() {
		SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		return timestampFormat.format(timestamp);
	}
	
	public static String removeCNPJMask(String cnpj) {
		return cnpj.replace(".", "").replace("/", "").replace("-", "");
	}
}
