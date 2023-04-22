package com.app.cryptography.model;

public class EncryptComponents {

    private int id;
    private String keyNo;
    private String iv;

    private FileModel fileModel;


    public EncryptComponents(String keyNo, String iv) {
        this.keyNo = keyNo;
        this.iv = iv;
    }

    public EncryptComponents() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKeyNo() {
        return keyNo;
    }

    public void setKeyNo(String keyNo) {
        this.keyNo = keyNo;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public FileModel getFileModel() {
        return fileModel;
    }

    public void setFileModel(FileModel fileModel) {
        this.fileModel = fileModel;
    }
}
