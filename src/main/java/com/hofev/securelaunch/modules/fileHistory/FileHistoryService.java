package com.hofev.securelaunch.modules.fileHistory;

import com.hofev.securelaunch.exceptions.FileObjNotFoundException;
import com.hofev.securelaunch.repositories.FileObjRepository;
import com.hofev.securelaunch.services.FileService;
import com.hofev.securelaunch.utils.HashingUtil;

import java.io.File;
import java.io.IOException;

public class FileHistoryService {

    private FileHistoryService() {}

    // Обновление хэш содержимого файла
    public static void updateHashFileContent(File file, String content) {
        // Изменение хэш содержимого файла в истории файлов
        try {
            FileObjRepository.getInstance().getFileObjByName(file.getName()).setHashData(getHashOfContentFile(content));
        } catch (FileObjNotFoundException e) {
            try {
                FileObjRepository.getInstance().createFileObj(file);
            } catch (IOException _) {}
        }
    }

    public static boolean isFileValid(File file) throws IOException {
        try {
            // Проверяется совместимость хэша
            return checkMatchOfHashFileContent(file);
        } catch (FileObjNotFoundException e) {
            return true;
        }
    }

    // Проверка на совпадение хэша содержимого
    private static boolean checkMatchOfHashFileContent(File file) throws IOException, FileObjNotFoundException {
        // Получение хэш содержимого файла из базы
        String hashFromFileData = null;
        hashFromFileData = FileObjRepository.getInstance().getFileObjByName(file.getName()).getHashData();
        // Получение хэш содержимого текущего файла
        String hashFromFile = getHashOfContentFile(FileService.readFileContent(file));
        return hashFromFile.equals(hashFromFileData);
    }

    // Выдает хэш содержимого
    public static String getHashOfContentFile(String content) {
        return HashingUtil.hash256(content);
    }
}
