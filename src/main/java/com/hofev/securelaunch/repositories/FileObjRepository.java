package com.hofev.securelaunch.repositories;

import com.hofev.securelaunch.exceptions.FileObjAlreadyExistException;
import com.hofev.securelaunch.exceptions.FileObjNotFoundException;
import com.hofev.securelaunch.models.FileObj;
import com.hofev.securelaunch.modules.fileHistory.FileHistoryService;
import com.hofev.securelaunch.services.FileService;

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

    // Создание нового объекта для хранения данных о файле
    public void createFileObj(File file) throws IOException {

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
        if (FILE_OBJ_LIST.containsKey(fileObj.getFileName())) throw new FileObjAlreadyExistException("Файл уже существует!");
        FILE_OBJ_LIST.put(fileObj.getFileName(), fileObj);
        saveFileHistoryMap();
    }

    // Ищет файл в истории файлов
    public FileObj getFileObjByName(String fileObjName) throws FileObjNotFoundException {
        if (FILE_OBJ_LIST.containsKey(fileObjName)) return FILE_OBJ_LIST.get(fileObjName);
        else throw new FileObjNotFoundException("История о файле не найдена!");
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
