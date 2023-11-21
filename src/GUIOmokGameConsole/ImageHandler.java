package GUIOmokGameConsole;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * handles loading and manipulating images, which could be used for visual representations of the board, stones, and other
 * graphical elements.
 */

public class ImageHandler {

    private final BufferedImage image;

    /**
     * Sets up the "image" feature by loading it.
     * @param path represents the path to an image
     * @throws IOException If a path is not found
     */
    public ImageHandler(String path) throws IOException {
        image = loadImageResource(path);
    }

    /**
     * Sets up the "image" feature with the desired resized.
     * @param path represents the path to the image
     * @param x the width of the resized image
     * @param y the length of the resized image
     * @throws IOException if a path is not found
     */
    public ImageHandler(String path, int x, int y) throws IOException {
        BufferedImage originalImage = loadImageResource(path);
        image = resize(originalImage, x, y);
    }

    /**
     * Get the image
     * @return the current image stored in the object
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * Resizes any image given to the given parameters
     * @param img image to be resized
     * @param newW width of resized image
     * @param newH length of resized image
     * @return a buffered image to the new size
     */
    private BufferedImage resize(BufferedImage img, int newW, int newH) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage dimg = new BufferedImage(newW, newH, img.getType());
        Graphics2D g = dimg.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);
        g.dispose();
        return dimg;
    }

    /**
     * Loads and creates a buffered image
     * @param path represents the path was the image is stored
     * @return a new buffered image
     * @throws IOException if the path is non-existing
     */
    private BufferedImage loadImageResource(String path) throws IOException {
        BufferedImage loadedImage = ImageIO.read(Objects.requireNonNull(getClass().getResource(path)));
        if (loadedImage == null) {
            throw new IOException("Image resource not found: " + path);
        }
        return loadedImage;
    }
}
