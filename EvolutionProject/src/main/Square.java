package main;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Square {

    private float x, y, width, height;
    private Color color;

    public Square() {

    }

    private Square(float x, float y, float width, float height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;

    }

    public Square(TheImage p) {
        this();
        randomize(p);
    }

    public void paint(BufferedImage bi) {


            // No.2
            // In case the buffered image supports transparency
            Graphics2D g2d = bi.createGraphics();

            // Transparency is created on all the filled pixels
            Color transparent = new Color(color.getRed(), color.getGreen(), color.getBlue(), 127);
            g2d.setColor(transparent);
            g2d.setComposite(AlphaComposite.SrcOver);
            g2d.fill(new Rectangle2D.Float(x, y, width, height));
            g2d.dispose();

    }

    public void randomize(TheImage p) {
        x = RandomGenerator.getRandomFloat() * p.getWidth();
        y = RandomGenerator.getRandomFloat() * p.getHeight();
        width = RandomGenerator.getRandomFloat() * p.getWidth() / 4;
        height = RandomGenerator.getRandomFloat() * p.getHeight() / 4;
        color = RandomGenerator.getRandomColor();
    }


    public Square copy() {
        return new Square(x, y, width, height, color);
    }


}