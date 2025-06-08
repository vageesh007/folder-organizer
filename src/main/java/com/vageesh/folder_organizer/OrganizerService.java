package com.vageesh.folder_organizer;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Map;

@Service
public class OrganizerService {

    private final Map<String, List<String>> extMap;

    public OrganizerService(CategoryProperties props) {
        this.extMap = props.getExtMap();
    }

    public void organize(Path folder) throws IOException {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(folder)) {
            for (Path p : stream) {
                if (Files.isRegularFile(p)) {
                    String category = categorize(p);
                    Path target = folder.resolve(category);
                    Files.createDirectories(target);
                    Files.move(p, target.resolve(p.getFileName()),
                               StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }
    }

    private String categorize(Path file) {
        String name = file.getFileName().toString();
        int dotIndex = name.lastIndexOf('.');
        // compute once into an effectively final variable
        final String extension = (dotIndex >= 0)
            ? name.substring(dotIndex).toLowerCase()
            : "";

        return extMap.entrySet().stream()
            .filter(e -> e.getValue().contains(extension))
            .map(Map.Entry::getKey)
            .findFirst()
            .orElse("Others");
    }
}
