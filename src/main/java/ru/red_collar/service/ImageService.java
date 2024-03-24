package ru.red_collar.service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Optional;
import lombok.SneakyThrows;
import ru.red_collar.util.PropertiesUtil;

public class ImageService {

    private static final ImageService INSTANCE = new ImageService();

    private static final String BASE_PATH = PropertiesUtil.get("image.base.url");


    public static ImageService getInstance() {
        return INSTANCE;
    }

    @SneakyThrows
    public Optional<InputStream> get(final String imagePath) {
        final Path fullImagePath = Path.of(BASE_PATH, imagePath);
        return Files.exists(fullImagePath) ?
            Optional.of(Files.newInputStream(fullImagePath)) :
            Optional.empty();
    }

    @SneakyThrows
    public void upload(final String imagePath, final InputStream inputStream) {
        final Path imageFullPath = Path.of(BASE_PATH, imagePath);
        try (inputStream) {
            Files.createDirectories(imageFullPath.getParent());
            Files.write(imageFullPath, inputStream.readAllBytes(), StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING);
        }
    }
}
