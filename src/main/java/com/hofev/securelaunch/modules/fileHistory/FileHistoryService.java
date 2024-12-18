package com.hofev.securelaunch.modules.fileHistory;

import com.hofev.securelaunch.exceptions.FileObjNotFoundException;
import com.hofev.securelaunch.models.FileObj;
import com.hofev.securelaunch.repositories.FileObjRepository;
import com.hofev.securelaunch.services.FileService;
import com.hofev.securelaunch.utils.HashingUtil;

import java.io.File;
import java.io.IOException;

public class FileHistoryService {


    private FileHistoryService() {}

    // Создание нового объекта для хранения данных о файле
    public static FileObj createFileObj(File file) throws IOException {
        return new FileObj(
                getHashOfContentFile(FileService.readFileContent(file)),
                file.getName()
        );
    }

    // Обновление хэш содержимого файла
    public static void updateHashFileContent(File file, String content) throws FileObjNotFoundException {
        FileObjRepository fileObjRepository = new FileObjRepository();
        // Изменение хэш содержимого файла в истории файлов
        fileObjRepository.getFileObjByName(file.getName()).setHashData(getHashOfContentFile(content));
        // Обновление истории файлов
        fileObjRepository.updateFileObjOnFile();
    }

    // Проверка на совпадение хэша содержимого
    public static boolean CheckMatchOfHashFileContent(File file) throws FileObjNotFoundException, IOException {
        // Получение хэш содержимого файла из базы
        String hashFromFileData = (new FileObjRepository()).getFileObjByName(file.getName()).getHashData();
        // Получение хэш содержимого текущего файла
        String hashFromFile = getHashOfContentFile(FileService.readFileContent(file));
        return hashFromFile.equals(hashFromFileData);
    }

    // Выдает хэш содержимого
    public static String getHashOfContentFile(String content) {
        return HashingUtil.hash256(content);
    }
}
