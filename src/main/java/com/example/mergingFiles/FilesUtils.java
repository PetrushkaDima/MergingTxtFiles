package com.example.mergingFiles;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FilesUtils {
    public static List<Path> findTxtFiles(String baseDir) {
        final List<Path> pathList = new ArrayList<>();

        try {
            Files.walkFileTree(Paths.get(baseDir), new FileVisitor<Path>() {

                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (file.toString().endsWith(".txt") && Files.isReadable(file)) {
                        pathList.add(file);
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    return FileVisitResult.SKIP_SUBTREE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pathList;
    }

    public static void mergeFiles(List<Path> pathList, Path resultPath) {

        pathList.stream()
                .sorted(Comparator.comparing(Path::getFileName))
                .forEach(path -> {
                    try {
                        Files.write(resultPath, Files.readAllBytes(path), StandardOpenOption.APPEND);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        System.out.println("Файлы объединены в " + resultPath);
    }
}
