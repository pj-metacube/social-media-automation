package com.codehouse.util;


import com.codehouse.constant.Constants;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImageProcessor {
    public static String FOLDER_BASE_PATH = Constants.FACEBOOK_IMAGE_NAME_FOLDER_PATH + Constants.FacebookPage.SEEMA_BHABHI;

    public static void main(String[] args) {
        // Set the input and output directories
        String inputDir = FOLDER_BASE_PATH + "\\RAW_Images";
        String outputDir = FOLDER_BASE_PATH + "\\FINAL_Images";

        processImageWithWatermark(inputDir, outputDir);
        renameFiles(FOLDER_BASE_PATH + "\\FINAL_Images", Constants.IMAGE_FILE_BASE_NAME, 1);
    }

    /**
     * Process image with re-saving and watermark
     * @param inputDir
     * @param outputDir
     */
    public static void processImageWithWatermark(String inputDir, String outputDir) {
        // Create the output directory if it doesn't exist
        File outputDirectory = new File(outputDir);
        if (!outputDirectory.exists()) {
            outputDirectory.mkdirs();
        }

        // Get a list of all files in the input directory
        File[] files = new File(inputDir).listFiles();

        // Iterate over each file
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    String fileName = file.getName();
                    // Check if the file is an image
                    if (fileName.endsWith(".jpg") || fileName.endsWith(".png") || fileName.endsWith(".jpeg")) {
                        try {
                            // Read the original image
                            BufferedImage originalImage = ImageIO.read(file);

                            // Add watermark
                            String watermarkText = "@Cute-Bhabhi";
                            int watermarkFontSize = 20;
                            Font watermarkFont = new Font("Arial", Font.BOLD, watermarkFontSize);
                            Graphics2D g = (Graphics2D) originalImage.getGraphics();
                            g.setFont(watermarkFont);
                            g.setColor(Color.BLUE);
                            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
                            g.rotate(Math.toRadians(0), originalImage.getWidth() / 2, originalImage.getHeight() / 2);
                            g.drawString(watermarkText, 20, 50);
                            g.dispose();

                            // Save the modified image to the output directory
                            Path outputPath = Paths.get(outputDir, fileName);
                            ImageIO.write(originalImage, "png", outputPath.toFile());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }


    /**
     * Rename the images with specific pattern in name and start count
     * @param folderPath
     * @param baseName
     * @param startNumber
     */
    public static void renameFiles(String folderPath, String baseName, int startNumber) {
        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        if (files != null) {
            int counter = startNumber;
            for (File file : files) {
                if (file.isFile() && (file.getName().endsWith(".jpg") || file.getName().endsWith(".jpeg") || file.getName().endsWith(".png"))) {
                    String extension = file.getName().substring(file.getName().lastIndexOf("."));
                    String newFileName = baseName + "_" + counter + extension;
                    File newFile = new File(folderPath, newFileName);
                    file.renameTo(newFile);
                    counter++;
                }
            }
            System.out.println("Images renamed successfully!");
        } else {
            System.out.println("Folder is empty or does not exist.");
        }
    }
}
