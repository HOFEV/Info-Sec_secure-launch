package com.hofev.securelaunch.repositories;

import com.hofev.securelaunch.models.FileObj;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileObjRepository {
    private static final String nameOfFileHistory = "fileHistory.ser";
    private static final FileObjRepository FILE_OBJ_REPOSITORY = new FileObjRepository();
    private Map<String, FileObj> FILE_OBJ_LIST;

    private FileObjRepository() {
        loadFileObjFromFile();
    }

    public static FileObjRepository getInstance() {
        return FILE_OBJ_REPOSITORY;
    }

    // Добавление файла в историю файлов
    public void uploadFileObj(FileObj fileObj) {
        FILE_OBJ_LIST.put(fileObj.getFileName(), fileObj);
        saveFileHistoryMap();
    }

    // Ищёт файл в истории файлов
    public FileObj getFileObjByName(String fileObjName) {
        return FILE_OBJ_LIST.getOrDefault(fileObjName, null);
    }

    // Проверяет существование такого файла в истории
    public boolean checkFileObj(File file) {
        return FILE_OBJ_LIST.containsKey(file.getName());
    }

    // Сохраняет список файлов в файл
    private void saveFileHistoryMap() {
         // Сериализация истории файлов
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nameOfFileHistory))) {
            oos.writeObject(FILE_OBJ_LIST);
        } catch (IOException e) {
            System.out.println("Ошибка записи истории файлов");
        }
    }

    // Загружает список файлов из файла
    private void loadFileObjFromFile() {
        // Де сериализация истории файлов
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nameOfFileHistory))) {

            Object obj = ois.readObject();

            if (obj instanceof Map) {
                this.FILE_OBJ_LIST = (Map<String, FileObj>) obj;
            } else {
                throw new IllegalArgumentException("Ошибка загрузки истории файлов");
            }

        } catch (IOException | ClassNotFoundException _) {
            this.FILE_OBJ_LIST = new HashMap<>();
        }
    }
}
