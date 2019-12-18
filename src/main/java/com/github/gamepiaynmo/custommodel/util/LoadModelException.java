package com.github.gamepiaynmo.custommodel.util;

public class LoadModelException extends RuntimeException {
    private String fileName;
    public LoadModelException(String fileName, Exception cause) {
        super(cause);
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
