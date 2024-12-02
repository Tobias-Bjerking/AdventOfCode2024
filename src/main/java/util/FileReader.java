package util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

public class FileReader {

    public static List<String> readLines(String fileName) {
        try {
            Path filePath = Paths.get(Objects.requireNonNull(FileReader.class.getClassLoader().getResource(fileName)).toURI());
            return Files.readAllLines(filePath);
        } catch (IOException | URISyntaxException e ) {
            throw new RuntimeException(e);
        }
    }
}
