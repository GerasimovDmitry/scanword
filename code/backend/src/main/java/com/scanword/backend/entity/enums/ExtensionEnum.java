package com.scanword.backend.entity.enums;

public interface ExtensionEnum {
    enum Pics implements ExtensionEnum {
        JPG,
        PNG
    }
    enum Sound implements ExtensionEnum {
        MP3,
        WAV
    }

    static String getExtension(String fileName) {
        String extension = "";

        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i+1);
        }
        return extension.toUpperCase();
    }

    static String cutOffExtension(String fileName) {
        return fileName.substring(0, fileName.lastIndexOf('.'));
    }

    static boolean isValidExtension(String extension) {
        return isPic(extension.toUpperCase()) || isSound(extension.toUpperCase());
    }

    static boolean isPic(String extension) throws IllegalArgumentException {
        try {
            ExtensionEnum.Pics.valueOf(extension);
        }
        catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    static boolean isSound(String extension) throws IllegalArgumentException {
        try {
            ExtensionEnum.Sound.valueOf(extension);
        }
        catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }
}
