package com.codehouse.util;

import com.codehouse.constant.Constants;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class VideoInformation {
    public static void main(String[] args) {
        renameFiles(Constants.VIDEO_NAME_FOLDER_PATH, Constants.VIDEO_FILE_BASE_NAME, 1);
    }

    public static void renameFiles(String folderPath, String baseName, int startNumber) {
        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        if (files != null) {
            int counter = startNumber;
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".mp4")) {
                    String newFileName = baseName + "_" + counter + ".mp4";
                    File newFile = new File(folderPath, newFileName);
                    file.renameTo(newFile);
                    counter++;
                }
            }
            System.out.println("Files renamed successfully!");
        } else {
            System.out.println("Folder is empty or does not exist.");
        }
    }

    public static void saveVideoNameToFile(String folderPath, String videoNameFilePath, int limit) {
        // Create a File object representing the directory
        File directory = new File(folderPath);

        // Create a FileWriter to write to the output file
        try (FileWriter fileWriter = new FileWriter(videoNameFilePath);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

            // Recursively traverse the directory and its subdirectories
            List<String> imagePaths = traverseDirectory(directory, limit); // Limit to 25 lines

            // Sort the image paths
            Collections.sort(imagePaths, new NumericFileNameComparator());

            // Write the sorted image paths to the output file
            for (int i = 0; i < imagePaths.size(); i++) {
                if (i > 0) {
                    bufferedWriter.newLine();
                }
                bufferedWriter.write(imagePaths.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("video paths have been written to " + videoNameFilePath);
    }

    private static List<String> traverseDirectory(File directory, int maxLines) {
        List<String> imagePaths = new ArrayList<>();
        int linesRead = 0;

        // Get list of files and directories within the specified directory
        File[] files = directory.listFiles();

        if (files != null) {
            // Iterate over each file and directory
            for (File file : files) {
                if (linesRead >= maxLines) {
                    break;
                }
                if (file.isDirectory()) {
                    // If it's a directory, recursively traverse it
                    List<String> subDirectoryFiles = traverseDirectory(file, maxLines - linesRead);
                    imagePaths.addAll(subDirectoryFiles);
                    linesRead += subDirectoryFiles.size();
                } else if (file.isFile()) {
                    // If it's a file, check if it's an image file
                    if (isVideoFile(file)) {
                        // Add the absolute path of the image file to the list
                        imagePaths.add(file.getAbsolutePath());
                        linesRead++;
                    }
                }
            }
        }

        return imagePaths;
    }


    private static boolean isVideoFile(File file) {
        // Check if the file extension is a video extension
        String name = file.getName().toLowerCase();
        return name.endsWith(".mp4");
    }


    private static class NumericFileNameComparator implements Comparator<String> {
        @Override
        public int compare(String s1, String s2) {
            // Extract numeric parts from file names
            int num1 = extractNumber(s1);
            int num2 = extractNumber(s2);

            // Compare numeric parts
            return Integer.compare(num1, num2);
        }

        private int extractNumber(String s) {
            // Extract numeric part from the file name
            String[] parts = s.split("_");
            String lastPart = parts[parts.length - 1];
            // Remove file extension
            String number = lastPart.substring(0, lastPart.lastIndexOf('.'));
            // Parse and return the numeric part
            return Integer.parseInt(number);
        }
    }
}