package com.music.utility;
import java.io.File;

public class RenameFiles {
    public static void main(String[] args) {
        File folder = new File("D:\\Music\\Yakshagana");

        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("Invalid folder path.");
            return;
        }

        File[] files = folder.listFiles();

        if (files != null) {
            for (File oldFile : files) {
                if (oldFile.isFile() && oldFile.getName().toLowerCase().endsWith(".mp3")) {
                    String oldName = oldFile.getName();
                    		//.replace("＂", "");
                    		File newFile = new File(folder, "Yakshagana-" + oldName);

                    if (oldFile.renameTo(newFile)) {
                        System.out.println("Renamed: " + oldName + " → " + newFile.getName());
                    } else {
                        System.out.println("Failed to rename: " + oldName);
                    }
                }
            }
        }
    }
}
