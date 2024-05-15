package com.codehouse.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Utils {
    public static List<String> readNameFromDisk(String fileNamePath) {
        // Create a list to store the lines
        List<String> fileNameList = new ArrayList<>();

        try {
            // Create FileReader object
            FileReader fileReader = new FileReader(fileNamePath);

            // Wrap FileReader in BufferedReader for efficient reading
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;

            // Read lines until the end of file
            while ((line = bufferedReader.readLine()) != null) {
                // Add each line to the list
                fileNameList.add(line);
            }

            // Close the BufferedReader
            bufferedReader.close();
        } catch (IOException e) {
            // Handle exceptions, e.g., file not found
            e.printStackTrace();
        }
        return fileNameList;
    }

    /**
     *
     * @param folderPath
     * @param filePath
     * @param fileType
     * @param limit
     */
    public static void saveFileNamesToTextFile(String folderPath, String filePath, String fileType, int limit) {
        // Create a File object representing the directory
        File directory = new File(folderPath);

        // Create a FileWriter to write to the output file
        try (FileWriter fileWriter = new FileWriter(filePath); BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

            // Recursively traverse the directory and its subdirectories
            List<String> filePaths = traverseDirectory(directory, fileType); // Limit to 25 lines

            // Sort the image paths
            Collections.sort(filePaths, new NumericFileNameComparator());

            // Write the sorted image paths to the output file
            for (int i = 0; i < (Math.min(filePaths.size(), limit)); i++) {
                if (i > 0) {
                    bufferedWriter.newLine();
                }
                bufferedWriter.write(filePaths.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("File paths have been written to \n " + filePath);
    }

    private static List<String> traverseDirectory(File directory, String fileType) {
        List<String> filePaths = new ArrayList<>();
        int linesRead = 0;

        // Get list of files and directories within the specified directory
        File[] files = directory.listFiles();

        if (files != null) {
            // Iterate over each file and directory
            for (File file : files) {
                if (file.isDirectory()) {
                    // If it's a directory, recursively traverse it
                    List<String> subDirectoryFiles = traverseDirectory(file, fileType);
                    filePaths.addAll(subDirectoryFiles);
                    linesRead += subDirectoryFiles.size();
                } else if (file.isFile()) {
                    // If it's a file, check if it's an image file
                    if ("IMAGE".equalsIgnoreCase(fileType)) {
                        if (isImageFile(file)) {
                            // Add the absolute path of the image file to the list
                            filePaths.add(file.getAbsolutePath());
                            linesRead++;
                        }
                    }
                    if ("VIDEO".equalsIgnoreCase(fileType)) {
                        if (isVideoFile(file)) {
                            // Add the absolute path of the image file to the list
                            filePaths.add(file.getAbsolutePath());
                            linesRead++;
                        }
                    }
                }
            }
        }

        return filePaths;
    }

    private static boolean isImageFile(File file) {
        // Check if the file extension is an image extension
        String name = file.getName().toLowerCase();
        return name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png") || name.endsWith(".gif") || name.endsWith(".bmp");
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

    public static void moveImages(String imagePathFile, String destinationFolder) {
        // Ensure destination folder exists
        File destFolder = new File(destinationFolder);
        if (!destFolder.exists()) {
            destFolder.mkdirs();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(imagePathFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                File imageFile = new File(line);
                if (imageFile.exists()) {
                    Path sourcePath = imageFile.toPath();
                    Path destinationPath = Paths.get(destinationFolder, imageFile.getName());

                    try {
                        Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                        System.out.println("Moved: " + imageFile.getName());
                    } catch (IOException e) {
                        System.err.println("Failed to move file: " + imageFile.getName());
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("File not found: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
