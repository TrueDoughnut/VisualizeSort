package com.cfs.threaded;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GUI extends JFrame implements ActionListener, Runnable {

    static final int WIDTH = 800;
    static final int HEIGHT = 600;

    private final Timer timer = new Timer(100, this);

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
    Panel(){
        this.setSize(GUI.WIDTH, GUI.HEIGHT);
        sort = new Sort(this.getWidth(), this.getHeight());
        Thread thread = new Thread(sort);
        thread.start();
    }

    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        int[] sort = this.sort.getSort();
    }
}

class Sort implements Runnable {

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
