package main;
import java.io.File;
import java.io.IOException;
import java.awt.geom.AffineTransform;
import java.awt.image.DataBufferByte;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class ImageProcessor {


    public static BufferedImage resizeImage(BufferedImage image, int newX, int newY, boolean keepRatio) {
        double scaleofX = (double)newX / image.getWidth();
        double scaleofY = (double)newY / image.getHeight();

        if (keepRatio) {
            if (scaleofX > scaleofY) {
                scaleofX = scaleofY;
            }
            else
            {
                scaleofY = scaleofX;
            }
        }

        AffineTransform affineTransform = new AffineTransform();
        affineTransform.scale(scaleofX, scaleofY);
        AffineTransformOp scaleOp =
                new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_BILINEAR);
        return  scaleOp.filter(image, null);
    }

    public static BufferedImage loadPNG(String filename) {
        BufferedImage map = null;
        try {
            map = ImageIO.read(new File(filename));
        } catch (IOException e) {
            System.err.println(filename + " file not found!");
        }
        return map;
    }



    public static double ImageDifferencesInRGBVal(BufferedImage image1, BufferedImage image2) throws Exception {

        final byte[] pixels1 = ((DataBufferByte) image1.getRaster().getDataBuffer()).getData();
        final byte[] pixels2 = ((DataBufferByte) image2.getRaster().getDataBuffer()).getData();
        final boolean hasAlphaChannel1 = image1.getAlphaRaster() != null;
        final boolean hasAlphaChannel2 = image2.getAlphaRaster() != null;
        float[] rgb1 = new float[3];
        float[] rgb2 = new float[3];
        double distance = 0;
        int imagePixelCount;

        // Find pixel lengths
        int pixelLength1 = 3;
        int pixelLength2 = 3;

        if (hasAlphaChannel1) {
            pixelLength1 = 4;
        }

        if (hasAlphaChannel2) {
            pixelLength2 = 4;
        }

        // Error check
        if (pixels1.length / pixelLength1 != pixels2.length / pixelLength2) {
            throw new Exception("Image size are not equal!");
        }

        // Find image size
        imagePixelCount = pixels1.length / pixelLength1;

        for (int pixel1 = 0, pixel2 = 0; pixel1 < pixels1.length && pixel2 < pixels2.length; pixel1 += pixelLength1, pixel2 += pixelLength2) {

            if (hasAlphaChannel1) {
                // results to rgb1
                rgb1[0] = (float)((int)pixels1[pixel1 + 3] & 0xff) / 256.0f;
                rgb1[1] = (float)((int)pixels1[pixel1 + 2] & 0xff) / 256.0f;
                rgb1[2] = (float)((int)pixels1[pixel1 + 1] & 0xff) / 256.0f;

            }
            else
            {
                // results to rgb1
                rgb1[0] = (float)((int)pixels1[pixel1 + 2] & 0xff) / 256.0f;
                rgb1[1] = (float)((int)pixels1[pixel1 + 1] & 0xff) / 256.0f;
                rgb1[2] = (float)((int)pixels1[pixel1 + 0] & 0xff) / 256.0f;
            }


            if (hasAlphaChannel2) {
                // results to rgb2
                rgb2[0] = (float)((int)pixels2[pixel2 + 3] & 0xff) / 256.0f;
                rgb2[1] = (float)((int)pixels2[pixel2 + 2] & 0xff) / 256.0f;
                rgb2[2] = (float)((int)pixels2[pixel2 + 1] & 0xff) / 256.0f;
            }
            else
            {
                // results to rgb2
                rgb2[0] = (float)((int)pixels2[pixel2 + 2] & 0xff) / 256.0f;
                rgb2[1] = (float)((int)pixels2[pixel2 + 1] & 0xff) / 256.0f;
                rgb2[2] = (float)((int)pixels2[pixel2 + 0] & 0xff) / 256.0f;
            }

            float dR = rgb1[0]-rgb2[0];
            float dG = rgb1[1]-rgb2[1];
            float dB = rgb1[2]-rgb2[2];
            distance += Math.sqrt(dR*dR + dG*dG + dB*dB);
        }

        return distance / imagePixelCount;
    }

}