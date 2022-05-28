package Vue;

import Global.Configuration;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.Objects;

public class Imager {

    public static Image getImageBuffer(String path) {
        InputStream in = Configuration.chargerFichier(path);

        try {
            return ImageIO.read(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Image getScaledImage(String path, int width, int height) {
        Image image = getImageBuffer(path);
        return getScaledImage(Objects.requireNonNull(image), width, height);
    }

    public static Image getScaledImage(Image image, int width, int height) {
        double scaleX = ((double)width / (double)image.getWidth(null));
        double scaleY = ((double)height / (double)image.getHeight(null));
        if (scaleX < 1 && scaleY < 1)
            return getDownscaledImage(image, width, height);

        AffineTransform xform =  AffineTransform.getScaleInstance(scaleX, scaleY);
        image = new AffineTransformOp(xform, AffineTransformOp.TYPE_BICUBIC).filter((BufferedImage) image, null);
        return image;
    }

    private static Image getDownscaledImage(Image image, int width, int height) {
        return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

    public static ImageIcon getImageIcon(String path) {
        return new ImageIcon(Objects.requireNonNull(getImageBuffer(path)));
    }
}
