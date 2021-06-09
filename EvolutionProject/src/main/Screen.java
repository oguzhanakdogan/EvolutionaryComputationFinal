package main;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Screen {

    private JFrame jframe;
    private JPanel jpanel;
    private JLabel upperLabel;
    public JLabel imageLabel;
    private JLabel lowerLabel;
    private final int height;
    private final int width;

    public Screen(int width, int height, int posX, int posY, boolean exitOnClose) {
        this.width = width;
        this.height = height;
        initGui(posX, posY, exitOnClose);
    }

    private void initGui(int posX, int posY, boolean exitOnClose) {
        jframe = new JFrame("YapayZeka Odevi");
        jpanel = new JPanel(new BorderLayout());
        upperLabel = new JLabel();
        imageLabel = new JLabel();
        lowerLabel = new JLabel();

        // make centered
        upperLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        lowerLabel.setHorizontalAlignment(JLabel.CENTER);

        jpanel.add(upperLabel, BorderLayout.PAGE_START);
        jpanel.add(imageLabel, BorderLayout.CENTER);
        jpanel.add(lowerLabel, BorderLayout.PAGE_END);
        jframe.add(jpanel);
        jframe.setSize(width, height);
        jframe.setLocation(posX, posY);
        if (exitOnClose) {
            jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
    }


    public void show(BufferedImage bi) {
        jframe.setVisible(true);

        int realWidth = jframe.getWidth() - upperLabel.getFont().getSize() - lowerLabel.getFont().getSize();
        int realHeight = jframe.getHeight() - upperLabel.getFont().getSize() - lowerLabel.getFont().getSize() - 120;

        BufferedImage newbi = ImageProcessor.resizeImage(bi, realWidth, realHeight, true);
        imageLabel.setIcon(new ImageIcon(newbi));
        jframe.repaint();
    }


    public void show(BufferedImage bi, String upperCaption, String lowerCaption) {
        jframe.setVisible(true);
        show(bi);
        upperLabel.setText(upperCaption);
        lowerLabel.setText(lowerCaption);
    }

    public void close() {
        jframe.dispatchEvent(new WindowEvent(jframe, WindowEvent.WINDOW_CLOSING));
    }

    public void setUpperTextSize(int size) {
        upperLabel.setFont(new Font(upperLabel.getFont().getFontName(), upperLabel.getFont().getStyle(), size));
    }


    public void setLowerTextSize(int size) {
        lowerLabel.setFont(new Font(lowerLabel.getFont().getFontName(), lowerLabel.getFont().getStyle(), size));
    }


    public void setTextSize(int size) {
        setUpperTextSize(size);
        setLowerTextSize(size);
    }

    public void setTitle(String title) {
        jframe.setTitle(title);
    }



}