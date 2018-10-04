package com.cfs.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame implements ActionListener {

    static final int WIDTH = 800;
    static final int HEIGHT = 600;

    private Timer timer = new Timer(100, this);

    void createAndShowGUI(){
        this.setSize(WIDTH, HEIGHT);

        Panel panel = new Panel();
        this.add(panel);

        timer.start();
        this.setVisible(true);
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
        sort = new Sort();
    }

    @Override
    public void paintComponent(Graphics g){
        int[] arr = sort.getSort();
        int width = sort.getWidth();
        Graphics2D g2d = (Graphics2D) g;

        Rectangle[] rects = new Rectangle[arr.length];

        for(int i = 0; i < rects.length; i++){
            rects[i] = new Rectangle(i * width, GUI.HEIGHT - arr[i], width, arr[i]);
        }
        g2d.setColor(Color.DARK_GRAY);
        g2d.setBackground(Color.WHITE);
        for(Rectangle rect : rects){
            g2d.fill(rect);
        }
        sort.stepInsertion();
    }
}

class Sort {

    private int[] vals;
    private int[] sort;

    private int width = 20;

    Sort(){
        vals = new int[GUI.WIDTH / width];
        int stepY = GUI.HEIGHT / vals.length;
        int j = vals.length;
        for(int i = 0; i < vals.length; i++){
            vals[i] = stepY * j--;
        }
        sort = vals.clone();
    }

    Sort(int[] vals){
        this.vals = vals;
        int max = 0;
        for(int x : vals){
            if(x > max){
                max = x;
            }
        }
        int stepY = GUI.HEIGHT / max;
        for(int i = 0; i < vals.length; i++){
            vals[i] = stepY * i;
        }
        sort = vals.clone();
    }

    private int insertionIterator = 1;
    void stepInsertion(){
        for(int j = insertionIterator; j > 0; j--){
            if(vals[j] < vals[j-1]){
                int temp = vals[j];
                vals[j] = vals[j-1];
                vals[j-1] = temp;
            }
        }
        insertionIterator++;
    }

    int[] getSort(){
        return sort;
    }
    int getWidth(){
        return width;
    }
}