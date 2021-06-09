package main;
import java.awt.image.BufferedImage;

public class Main {

    public static void main(String[] args) {

        // Default parameters:
        String imageFile = "src\\images\\elonmusk.jpg";
        int sizeOfPopulation = 150; 		// amount of population
        int numberOfSquare = 300;   		// amount of square
        int generation = 5000;
        double selectionRate = 0.30;
        double mutuationRate = 0.005;



        // Create original GUI
        Screen screen = new Screen(640, 640, 0, 0, true);

        // Load from file
        BufferedImage realIMG = ImageProcessor.loadPNG(imageFile);

        // Scale image to 640x480
        BufferedImage scaredORGIMG = ImageProcessor.resizeImage(realIMG, 640, 480, true);

        // Show image
        screen.show(scaredORGIMG);

        // Set textbox contents
        screen.setTitle("Image");
        screen.setTextSize(20);

        // Create generated GUI
        Screen generationSc =
                new Screen(640, 640, 650, 0, true);

        // Set textbox contents
        generationSc.setTitle("the fittest individual");
        generationSc.setTextSize(20);

        Genetic g = new Genetic(sizeOfPopulation, numberOfSquare, realIMG, selectionRate, mutuationRate);

        for (int i=0; i<generation; i++) {
            g.fitnessCalculation();
            g.Selection();

            // Shows the fittest individual
            TheImage p = g.getBest();

            generationSc.show(p.getImage(), "Gen: " + i, "Fitness val: " + String.format("%.3f", p.fitnessVal));


            g.crossOver();
            g.mutuation();
        }


        generationSc.close();
        screen.close();
        System.exit(0);

    }

}