package main;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class TheImage implements Comparable<TheImage>{
    public static final Color BACKGROUND_COLOR = Color.WHITE;
    private ArrayList<Square> squareArrayList;
    private BufferedImage image;
    private int width;
    private int height;
    private boolean isFresh;
    double fitnessVal;


    public TheImage(int width, int height) {
        this.width = width;
        this.height = height;
        this.squareArrayList = new ArrayList<>();
        isFresh = false;
    }

    public BufferedImage getTheImage() {

        if (isFresh) {
            return image;
        }

        image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);

        Graphics2D graphics = image.createGraphics();

        graphics.setPaint ( (Paint)BACKGROUND_COLOR );
        graphics.fillRect ( 0, 0, image.getWidth(), image.getHeight() );

        for (Square p : squareArrayList) {
            if (p != null) {
                p.paint(image);
            }
        }

        return image;
    }


    public BufferedImage getImage() {
        return getTheImage();
    }

    public void addShapeItem(Square p) {
        isFresh = false;
        squareArrayList.add(p.copy());
    }

    public void insertShapeItem(int index, Square p) {
        isFresh = false;
        squareArrayList.add(index, p.copy());
    }

    public Square getShapeItem(int index) {
        isFresh = false;
        return squareArrayList.get(index);
    }

    public void setShapeItem(int index, Square p) {
        isFresh = false;
        squareArrayList.set(index, p.copy());
    }

    public int getShapeCount() {
        return squareArrayList.size();
    }

    public void removeShapeItem(int index) {
        isFresh = false;
        squareArrayList.remove(index);
    }

    public int compareTo(TheImage object){
        //Picture p=(Picture) object;

        if(fitnessVal ==object.fitnessVal){
            return 0;
        }
        else if(fitnessVal >object.fitnessVal)
            return -1;
        else
            return 1;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }



}