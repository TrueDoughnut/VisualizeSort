package com.cfs.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GUI extends JFrame implements ActionListener {

    static final int WIDTH = 1200;
    static final int HEIGHT = 600;

    private Timer timer = new Timer(100, this);

    private Panel panel;
    private JMenuBar menuBar;

    void createAndShowGUI(){
        this.setSize(WIDTH, HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new Panel();
        this.add(panel);

        //menuBar = new JMenuBar();
        //this.add(menuBar);

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
    private int width;
    private int height;

    Panel(){
        sort = new Sort(this.getWidth(), this.getHeight());
        width = sort.getWidth();
        height = sort.getHeight();
    }

    @Override
    public void paintComponent(Graphics g){
        int[] arr = sort.getSort();

        Graphics2D g2d = (Graphics2D) g;

        Rectangle[] rects = new Rectangle[arr.length];

        for(int i = 0; i < rects.length; i++){
            rects[i] = new Rectangle(i * width, GUI.HEIGHT - arr[i] * height, width, arr[i] * height);
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

    private int width = 10;
    private int height = 10;

    Sort(int width, int height){
        vals = new int[width / this.width];
        int max = height / this.height;
        Random random = new Random();
        for(int i = 0; i < vals.length; i++){
            vals[i] = random.nextInt(max);
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
        if(insertionIterator >= sort.length){
            return;
        }
        for(int j = insertionIterator; j > 0; j--){
            if(sort[j] < sort[j-1]){
                int temp = sort[j];
                sort[j] = sort[j-1];
                sort[j-1] = temp;
            }
        }
        insertionIterator++;
    }

    void stepBubble(){
        for(int i = 0; i < vals.length - 1; i++){
            if(sort[i] > sort[i+1]){
                int temp = sort[i];
                sort[i] = sort[i+1];
                sort[i+1] = temp;
            }
        }
    }

    private int selectionPos = 0;
    void stepSelection(){
        if(selectionPos >= sort.length){
            return;
        }
        int min = sort[selectionPos];
        int temp = min;
        int x = -1;
        for(int i = selectionPos; i < sort.length; i++){
            if(sort[i] < min){
                min = sort[i];
                x = i;
            }
        }
        sort[selectionPos] = min;

        if(x >= 0){
            sort[x] = temp;
        }

        selectionPos++;
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