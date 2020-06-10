package ua.edu.lpnu.dsct.client.utilities;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class FileManager {
    public static Logger logger = Logger.getGlobal();

    public static byte[] read(String filePath) throws IOException {
        Path path = Paths.get(filePath);

        if (Files.notExists(path)) {
            throw new FileNotFoundException("File '" + filePath + "' does not exist.");
        }
        return Files.readAllBytes(path);
    }

    public static void write(byte[] content, String filePath) throws IOException {
        Path path = Paths.get(filePath);

        Path parent = path.getParent();
        if(parent != null && Files.notExists(parent)) {
            Files.createDirectories(parent);
        }

        if (Files.exists(path)) {
            logger.info("File '" + filePath + "' already exists and its contents will be overwritten.");
        }
        Files.write(path, content);
    }
}