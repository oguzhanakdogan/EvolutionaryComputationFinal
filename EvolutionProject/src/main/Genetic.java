package main;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

public class Genetic {

    public static final int THREAD_NUM = 8;

    ArrayList<TheImage> IMAGE;
    ArrayList<TheImage> selectedImage;
    int numberOfSquare;
    int numberOfImage;
    double selectionRate;
    double mutuationRate;

    BufferedImage realImage;
    TheImage bestPicture;

    public Genetic(int numberOfImage, int numberOfSquare, BufferedImage realImage, double selectionRate, double mutuationRate){
        this.numberOfSquare =numberOfSquare;
        this.numberOfImage =numberOfImage;
        this.realImage=realImage;
        this.selectionRate=selectionRate;

        this.mutuationRate=mutuationRate;
        this.IMAGE = new ArrayList<>();
        this.selectedImage = new ArrayList<>();

        initialize();
    }

    public void initialize(){

        IMAGE.clear();

        //picture population is created
        for(int i = 0; i< numberOfImage; i++){
            IMAGE.add(new TheImage(realImage.getWidth(), realImage.getHeight()));
        }

        //each shape is created and added to array of picture named as "shape"
        //sizes of shapes are randomized while creating object of shapes
        for(int i = 0; i< numberOfImage; i++){
                for(int j = 0; j< numberOfSquare; j++){
                    Square p=new Square(IMAGE.get(i));
                    IMAGE.get(i).addShapeItem(p);
                }
        }
    }

    public void fitnessCalculation(){

        // Set Best picture
        if (bestPicture != null)
            IMAGE.set(0, bestPicture);

        // SINGLE-THREAD
    	/*
        for(int i=0; i<picture.size(); i++){
            try {
				picture.get(i).fitness= 1.0 - ImageUtil.differenceImage(picture.get(i).getImage(), realImage);
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
        */

        // MULTI-THREAD [8]
        FitnessCalculator t[] = new FitnessCalculator[THREAD_NUM];

        for (int i=0; i<THREAD_NUM; i++) {
            t[i] = new FitnessCalculator(i, THREAD_NUM, IMAGE, realImage);
        }

        for (int i=0; i<THREAD_NUM; i++) {
            t[i].start();
        }

        for (int i=0; i<THREAD_NUM; i++) {
            try {
                t[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }



    }

    public void Selection(){
        // Sort best to worst
        Collections.sort(IMAGE);

        // Do selection
        selectedImage.clear();
        for (int i = 0; i <= (int)(IMAGE.size() * selectionRate) ; i++) {
            selectedImage.add(IMAGE.get(i));
        }

    }

    public void crossOver(){

        TheImage p1;
        TheImage p2;

        for (int i = 0; 2*i+1 < IMAGE.size(); i++) {

            p1 = selectedImage.get( RandomGenerator.getRandomInt(selectedImage.size()) ); //selected random picture to cross over

            do {
                p2 = selectedImage.get( RandomGenerator.getRandomInt(selectedImage.size()) ); //selected random picture to cross over
            } while (p1 == p2);

            int crossOverPoint = RandomGenerator.getRandomInt(numberOfSquare);

            //crossing over
            for (int j = 0; j < p1.getShapeCount() && j < p2.getShapeCount(); j++) {
                if (j < crossOverPoint) {
                    IMAGE.get(2 * i).setShapeItem(j, p1.getShapeItem(j) );
                    IMAGE.get(2 * i + 1).setShapeItem(j, p2.getShapeItem(j));
                }
                else {
                    IMAGE.get(2 * i).setShapeItem(j, p2.getShapeItem(j));
                    IMAGE.get(2 * i + 1).setShapeItem(j, p1.getShapeItem(j));
                }
            }
        }
    }

    public void mutuation(){
        for (TheImage p : IMAGE) {
            for (int i=0; i<p.getShapeCount(); i++) {
                if(RandomGenerator.getRandomFloat() < mutuationRate) {
                    // Move shape item to end of the list
                    Square paintable = p.getShapeItem(i);
                    p.removeShapeItem(i);
                    paintable.randomize(p);
                    //p.setShapeItem(i, paintable);
                    p.insertShapeItem(RandomGenerator.getRandomInt(p.getShapeCount()),paintable);

                }
            }
        }

    }

    public TheImage getBest(){
        Collections.sort(selectedImage);
        bestPicture = selectedImage.get(0);
        System.out.println("SelectedBest.fitness = " + bestPicture.fitnessVal);   // TODO: test
        return bestPicture;
    }

}

class FitnessCalculator extends Thread {
    int id, step;
    ArrayList<TheImage> picture;
    BufferedImage realImage;

    public FitnessCalculator(int id, int step, ArrayList<TheImage> picture, BufferedImage realImage) {
        super();
        this.id = id;
        this.step = step;
        this.picture = picture;
        this.realImage = realImage;
    }

    public void run() {
        for(int i=id; i<picture.size(); i+=step){
            try {
                picture.get(i).fitnessVal = 1.0 - ImageProcessor.ImageDifferencesInRGBVal(picture.get(i).getImage(), realImage); // HSB
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}