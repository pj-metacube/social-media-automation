package com.codehouse.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class TextToImage {

    public static void main(String[] args) {
        String text = "Hello, World! Hello, World! Hello, World! Hello, World! Hello, World! Hello, World! Hello, World! Hello, World! Hello, World! Hello, World! Hello, World! Hello, World! Hello, World! ";
        String imagePath = "C:\\Users\\Prakash\\Documents\\Automation\\image.png";;
        generateImage(text, imagePath);
    }

    public static void generateImage(String text, String imagePath) {
        int aspectRatioWidth = 9;
        int aspectRatioHeight = 16;
        int height = 400; // Arbitrary height, you can adjust as needed
        int width = (height * aspectRatioWidth) / aspectRatioHeight;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        // Set background color
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        // Set font and color for the text
        g2d.setColor(Color.BLACK);
        Font font = new Font("Arial", Font.BOLD, 20);
        g2d.setFont(font);

        // Calculate text position
        int textX = (width - g2d.getFontMetrics().stringWidth(text)) / 2;
        int textY = height / 2;

        // Draw the text
        g2d.drawString(text, textX, textY);
        g2d.dispose();

        // Save the image to a file
        try {
            File output = new File(imagePath);
            File parentDir = output.getParentFile();
            if (!parentDir.exists() && !parentDir.mkdirs()) {
                throw new IOException("Couldn't create directory: " + parentDir);
            }
            ImageIO.write(image, "png", output);
            System.out.println("Image saved successfully at: " + imagePath);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
