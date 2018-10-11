package com.cfs.threaded;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GUI extends JFrame implements ActionListener, Runnable {

    static final int WIDTH = 800;
    static final int HEIGHT = 600;

    private final Timer timer = new Timer(30, this);

    @SuppressWarnings("Duplicates")
    private void createAndShowGUI(){
        this.setSize(WIDTH, HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Panel panel = new Panel();
        this.add(panel);

        timer.start();
        this.setVisible(true);
    }

    @Override
    public void run(){
        this.createAndShowGUI();
    }

    @Override
    public void actionPerformed(ActionEvent e){
        this.revalidate();
        this.repaint();
    }
}

class Panel extends JPanel {

    private Sort sort;
    private int width;
    private int height;

    Panel(){
        this.setSize(GUI.WIDTH, GUI.HEIGHT);
        sort = new Sort(this.getWidth(), this.getHeight());
        sort.start();
        width = sort.getWidth();
        height = sort.getHeight();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> sort.interrupt()));
    }

    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        int[] sort = this.sort.getSort();
        boolean[] accessed = this.sort.getAccessed();

        for(int i = 0; i < sort.length; i++){
            if(accessed[i]){
                g2d.setColor(Color.RED);
            } else {
                g2d.setColor(Color.DARK_GRAY);
            }
            Rectangle rectangle = new Rectangle(i * this.width, this.getHeight() - sort[i] * height,
                    width, sort[i] * height);
            g2d.fill(rectangle);
        }

        this.sort.resetAccessed();
    }
}

class Sort extends Thread {

    private int[] sort;
    private boolean[] accessed;

    private int width = 20;
    private int height = 20;

    Sort(int width, int height) {
        sort = new int[width / this.width];
        accessed = new boolean[sort.length];
        int max = height / this.height;
        Random random = new Random();
        for (int i = 0; i < sort.length; i++) {
            sort[i] = random.nextInt(max);
        }
    }

    @Override
    public void run(){
        try {
            while (!this.isInterrupted()) {
                stepInsertion();
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private int insertionIterator = 1;
    @SuppressWarnings("Duplicates")
    private void stepInsertion() throws InterruptedException {
        accessed = new boolean[sort.length];
        if(insertionIterator >= sort.length){
            return;
        }
        for(int j = insertionIterator; j > 0; j--){
            if(sort[j] < sort[j-1]){
                int temp = sort[j];
                sort[j] = sort[j-1];
                sort[j-1] = temp;
            }
            accessed[j] = true;
            accessed[j - 1] = true;
        }
        insertionIterator++;
        Thread.sleep(100);
    }

    void resetAccessed(){
        accessed = new boolean[accessed.length];
    }
    boolean[] getAccessed(){
        return accessed;
    }
    int[] getSort(){
        return sort;
    }
    int getWidth(){
        return width;
    }
    int getHeight(){
        return height;
    }
}
