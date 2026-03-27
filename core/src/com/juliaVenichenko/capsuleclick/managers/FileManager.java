package com.juliaVenichenko.capsuleclick.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class FileManager {
//    private static final String f = "files/hats_data.txt";

    public void writeToFile(int value, String FILE_NAME) {
        FileHandle file = Gdx.files.local(FILE_NAME);
        file.writeString(String.valueOf(value), false); // false - перезаписывает файл
    }

    public int readFromFile(String FILE_NAME) {
        FileHandle file = Gdx.files.local(FILE_NAME);
        if (file.exists()) {
            String line = file.readString();
            return Integer.parseInt(line);
        }
        return 0; // значение по умолчанию
    }
}