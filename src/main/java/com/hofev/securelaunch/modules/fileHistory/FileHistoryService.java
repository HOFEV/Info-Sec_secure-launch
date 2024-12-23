package com.hofev.securelaunch.modules.fileHistory;

import com.hofev.securelaunch.models.FileObj;
import com.hofev.securelaunch.repositories.FileObjRepository;
import com.hofev.securelaunch.services.FileService;
import com.hofev.securelaunch.utils.HashingUtil;

import java.io.File;
import java.io.IOException;

public class FileHistoryService {

    private FileHistoryService() {}

    // Обновление хэш содержимого файла
    public static void updateHashFileContent(File file, String content) {

        // Работа с файловым репозиторием
        FileObjRepository fileObjRepository = FileObjRepository.getInstance();

        // Получение истории о файле
        FileObj fileObj = fileObjRepository.reloadList().getFileObjByName(file.getName());

        // Изменение хэш - содержимого файла
        if (fileObj != null) {
            fileObj.setHashData(getHashOfContentFile(content));

            // Обновление базы файлов
            fileObjRepository.uploadFileObjList();

        } else {
            // Добавление файла в базу файлов
            try {
                fileObjRepository.addFileObj(file);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static boolean isFileValid(File file) throws IOException {

        // Получение истории файла
        FileObj fileObj = FileObjRepository.getInstance().reloadList().getFileObjByName(file.getName());

        // Если ее нет в базе
        if (fileObj == null) return true;

        // Получение хэш содержимого файла из базы
        String hashFromFileData = fileObj.getHashData();

        // Получение хэш содержимого текущего файла
        String hashFromFile = getHashOfContentFile(FileService.readFileContent(file));

        return hashFromFile.equals(hashFromFileData);
    }

    // Выдает хэш содержимого
    public static String getHashOfContentFile(String content) {
        return HashingUtil.hash256(content);
    }
}
