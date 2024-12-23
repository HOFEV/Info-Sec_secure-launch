package com.hofev.securelaunch.repositories;

import com.hofev.securelaunch.exceptions.BrokenFileHistoryException;
import com.hofev.securelaunch.exceptions.FileObjAlreadyExistException;
import com.hofev.securelaunch.models.FileObj;
import com.hofev.securelaunch.modules.fileHistory.FileHistoryService;
import com.hofev.securelaunch.services.FileService;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileObjRepository {
    private static final String nameOfFileHistory = "fileHistory.ser";
    private static final FileObjRepository FILE_OBJ_REPOSITORY = new FileObjRepository();
    private Map<String, FileObj> fileObjList;
    private boolean isLaunched = false;

    private FileObjRepository() {}

    // Загрузка репозитории
    public static void loadFileObjRepository() throws BrokenFileHistoryException {
        FILE_OBJ_REPOSITORY.loadFileObjList();
        FILE_OBJ_REPOSITORY.isLaunched = true;
    }

    public static FileObjRepository getInstance() {
        if (!FILE_OBJ_REPOSITORY.isLaunched) throw new RuntimeException("Репозитория не была загружена");
        return FILE_OBJ_REPOSITORY;
    }

    // Метод, который обновляет лист перед использованием следующими методами
    public FileObjRepository reloadList() {
        try {
            FILE_OBJ_REPOSITORY.loadFileObjList(); // Обновление базы
        } catch (BrokenFileHistoryException e) {
            throw new RuntimeException(e);
        }
        return FILE_OBJ_REPOSITORY;
    }

    // Создание нового объекта для хранения данных о файле
    public void addFileObj(File file) throws IOException {

        // Создание истории о файле
        FileObj fileObj = new FileObj(
                FileHistoryService.getHashOfContentFile(FileService.readFileContent(file)),
                file.getName()
        );

        // Загрузка файла в историю
        try {
            uploadFileObj(fileObj);
        } catch (FileObjAlreadyExistException e) {
            System.out.println(e.getMessage());
        }
    }

    // Добавление файла в историю файлов
    public void uploadFileObj(FileObj fileObj) throws FileObjAlreadyExistException {
        if (fileObjList.containsKey(fileObj.getFileName())) throw new FileObjAlreadyExistException("Файл уже существует!");
        fileObjList.put(fileObj.getFileName(), fileObj);
        uploadFileObjList();
    }

    // Ищет файл в истории файлов
    public FileObj getFileObjByName(String fileObjName) {
        return fileObjList.getOrDefault(fileObjName, null);
    }

    // Сохраняет список файлов в файл
    public void uploadFileObjList() {
         // Сериализация истории файлов
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nameOfFileHistory))) {
            oos.writeObject(fileObjList);
        } catch (IOException e) {
            System.out.println("Ошибка записи истории файлов");
        }
    }

    // Загружает список файлов из файла
    private void loadFileObjList() throws BrokenFileHistoryException {
        // Де сериализация истории файлов
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nameOfFileHistory))) {

            Object obj = ois.readObject();

            if (obj instanceof Map) {
                this.fileObjList = (Map<String, FileObj>) obj;
            } else {
                System.out.println("Ошибка структуры базы! Аварийное завершение");
                throw new IllegalArgumentException("Ошибка загрузки истории файлов");
            }

        } catch (FileNotFoundException _) {
            // Создается новый список, если такого ещё нет
            System.out.println("ERROR: Файл отсутствует - создается новая база");
            this.fileObjList = new HashMap<>();
            uploadFileObjList(); // Сразу создание истории
        } catch (StreamCorruptedException e) {
            // Повреждение внутреннего файла
            throw new BrokenFileHistoryException("Повреждена история файлов!");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            this.fileObjList = new HashMap<>();
        }
    }
}
