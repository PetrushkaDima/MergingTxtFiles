package com.example.mergingFiles;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Введите путь к корневой папке");
        final String BASE_DIR;
        try {
            BASE_DIR = new BufferedReader(new InputStreamReader(System.in)).readLine();
            if (BASE_DIR.isEmpty()
                    || !Files.isDirectory(Paths.get(BASE_DIR))
                    || !Files.isReadable(Paths.get(BASE_DIR))) {
                System.out.println("Неккорктный ввод");
                return;
            }
            final Path RESULT_PATH = Paths.get(BASE_DIR + "\\result.txt");
            Files.deleteIfExists(RESULT_PATH);
            final List<Path> pathList = FilesUtils.findTxtFiles(BASE_DIR);
            Files.createFile(RESULT_PATH);

            if (pathList.size() == 0) {
                System.out.println("Файлы .txt не найдены");
                return;
            }
            FilesUtils.mergeFiles(pathList, RESULT_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
