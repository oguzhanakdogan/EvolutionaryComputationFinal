package main;
import java.awt.Color;
import java.util.Random;

public class RandomGenerator {

    private static Random rand = null;

    // Prevent constructing RandomGenerator
    private RandomGenerator() {}

    private static void checkInstance() {
        if (rand == null) {
            rand = new Random();
        }
    }


     //Generates random Color in RGB value.
    public static Color getRandomColor() {
        checkInstance();
        return Color.getHSBColor(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
    }


    public static int getRandomInt(int upperLimit) {
        checkInstance();
        return rand.nextInt(upperLimit);
    }


    public static float getRandomFloat() {
        checkInstance();
        return rand.nextFloat();
    }



}