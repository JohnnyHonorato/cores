package br.edu.ufcg.virtus.core.util;

import java.util.UUID;

public class FileUtil {

    private FileUtil() {}

    public static String generateNewFileName(String filename) {
        final UUID uuid = UUID.randomUUID();
        return uuid.toString() + "." + filename.split("[.]")[1];
    }

}
