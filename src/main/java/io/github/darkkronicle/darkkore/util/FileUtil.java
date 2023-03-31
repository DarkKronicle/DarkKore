package io.github.darkkronicle.darkkore.util;

import lombok.experimental.UtilityClass;
import net.minecraft.client.MinecraftClient;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Utility class to work with files
 */
@UtilityClass
public class FileUtil {

    /**
     * Gets the configuration directory of Minecraft
     * @return Configuration directory
     */
    public File getConfigDirectory() {
        return new File(MinecraftClient.getInstance().runDirectory, "config");
    }

    /**
     * Get files with a specified extension
     * @param directory Directory to check
     * @param extension Extension to check. Used in a `endsWith` call
     * @return A {@link List} that contains files within a directory with the same name
     * @throws IOException If something goes wrong
     */
    // https://mkyong.com/java/how-to-find-files-with-certain-extension-only/
    public List<Path> getFilesWithExtension(Path directory, String extension) throws IOException {
        if (!directory.toFile().isDirectory()) {
            throw new IllegalArgumentException("Directory has to be directory!");
        }

        List<Path> paths;

        try (Stream<Path> walk = Files.walk(directory)) {
            paths = walk
                    .filter(p -> !Files.isDirectory(p))
                    .filter(p -> p.toString().endsWith(extension))
                    .collect(Collectors.toList());
        }
        return paths;
    }

    /**
     * Get files with a specified extension
     * @param directory Directory to check
     * @param extension Extension to check. Used in a `endsWith` call
     * @return An {@link Optional} empty if something went wrong, or contains the files
     */
    public Optional<List<Path>> getFilesWithExtensionCaught(Path directory, String extension) {
        try {
            return Optional.of(getFilesWithExtension(directory, extension));
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    /**
     * Gets a resource from src/resources. Works in an emulated environment.
     *
     * @param path Path from the resources to get
     * @return Stream of the resource
     * @throws URISyntaxException If the resource doesn't exist
     * @throws IOException Can't be opened
     */
    public static InputStream getResource(String path) throws URISyntaxException, IOException {
        URI uri = Thread.currentThread().getContextClassLoader().getResource(path).toURI();
        if (!uri.getScheme().equals("file")) {
            // it's not a file
            return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
        } else {
            // it's a file - try to access it directly!
            return new FileInputStream(Paths.get(uri).toFile());
        }
    }


    public void write(String data, File file) throws IOException {
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
            writer.write(data);
        }
    }

    public String read(File file) throws IOException {
        try (FileInputStream reader = new FileInputStream(file)) {
            return IOUtils.toString(reader, StandardCharsets.UTF_8);
        }
    }

}
