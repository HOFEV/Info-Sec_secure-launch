package com.hofev.securelaunch.repositories;

import com.hofev.securelaunch.exceptions.FileObjNotFoundException;
import com.hofev.securelaunch.models.FileObj;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileObjRepository {
    private static final String nameOfFileHistory = "fileHistory.ser";
    private Map<String, FileObj> FILE_OBJ_LIST;

    public FileObjRepository() {
        loadFileObjMap();
    }

    // Добавление файла в историю файлов
    public void uploadFileObj(FileObj fileObj) {
        FILE_OBJ_LIST.put(fileObj.getFileName(), fileObj);
        saveFileHistoryMap();
    }

    // Ищёт файл в истории файлов
    public FileObj getFileObjByName(String fileObjName) throws FileObjNotFoundException {
        if (FILE_OBJ_LIST.containsKey(fileObjName)) return FILE_OBJ_LIST.get(fileObjName);
        else throw new FileObjNotFoundException("Файла не существует!");
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

    // Выгружает список файлов обратно в файл
    public void updateFileObjOnFile() {
        saveFileHistoryMap();
    }

    // Обновляет список файлов
    private void loadFileObjMap() {
        this.FILE_OBJ_LIST = loadFileObjFromFile();
    }

    // Загружает список файлов
    private Map<String, FileObj> loadFileObjFromFile() {
        // Де сериализация истории файлов
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nameOfFileHistory))) {

            Object obj = ois.readObject();

            if (obj instanceof Map) {
                return (Map<String, FileObj>) obj;
            } else {
                throw new IllegalArgumentException("Ошибка загрузки истории файлов");
            }

        } catch (IOException | ClassNotFoundException _) {
            return new HashMap<>();
        }
    }
}
