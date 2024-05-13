package com.codehouse.main;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Main {

    public static void main1(String[] args) {
        String text = "Hello, World!";
        String imagePath = "C:\\Users\\Prakash\\Documents\\Automation\\image.png";
        generateImage(text, imagePath);

    }

    public static void generateImage(String text, String imagePath) {
        int width = 300;
        int height = 100;
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


    public static void main(String[] args) {
        String format = "AM";
        int time;
        for (int i = 0;     i < 10; i++) {
            if ((9+i) ==12) format = "PM";
            System.out.println(((9+i)>12? 9+i-12: 9+i) + ":30 "+ format);
        }
    }
}
