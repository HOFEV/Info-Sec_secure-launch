package com.hofev.securelaunch.modules.fileHistory;

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
        FileObjRepository.getInstance().getFileObjByName(file.getName()).setHashData(getHashOfContentFile(content));
    }

    // Проверка на совпадение хэша содержимого
    public static boolean checkMatchOfHashFileContent(File file) throws IOException {
        // Получение хэш содержимого файла из базы
        String hashFromFileData = FileObjRepository.getInstance().getFileObjByName(file.getName()).getHashData();
        // Получение хэш содержимого текущего файла
        String hashFromFile = getHashOfContentFile(FileService.readFileContent(file));
        return hashFromFile.equals(hashFromFileData);
    }

    // Выдает хэш содержимого
    public static String getHashOfContentFile(String content) {
        return HashingUtil.hash256(content);
    }
}
