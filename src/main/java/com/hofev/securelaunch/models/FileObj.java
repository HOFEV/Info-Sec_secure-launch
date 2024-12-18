package com.hofev.securelaunch.models;

import java.io.File;
import java.io.Serializable;

public class FileObj implements Serializable {
    private String hashData;
    private String fileName;

    public FileObj(String hashData, String fileName) {
        this.hashData = hashData;
        this.fileName = fileName;
    }

    public String getHashData() {
        return hashData;
    }

    public void setHashData(String hashData) {
        this.hashData = hashData;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
