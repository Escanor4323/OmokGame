package GUIOmokGameConsole;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class ImageHandler {

    private final BufferedImage image;

    public ImageHandler(String path) throws IOException {
        image = loadImageResource(path);
    }

    public ImageHandler(String path, int x, int y) throws IOException {
        BufferedImage originalImage = loadImageResource(path);
        image = resize(originalImage, x, y);
    }

    public BufferedImage getImage() {
        return image;
    }

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

    private BufferedImage loadImageResource(String path) throws IOException {
        BufferedImage loadedImage = ImageIO.read(Objects.requireNonNull(getClass().getResource(path)));
        if (loadedImage == null) {
            throw new IOException("Image resource not found: " + path);
        }
        return loadedImage;
    }
}
