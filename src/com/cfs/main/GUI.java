package com.cfs.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Random;

public class GUI extends JFrame implements ActionListener {

    static final int WIDTH = 800;
    static final int HEIGHT = 600;

    private Timer timer = new Timer(100, this);

    private Panel panel;

    void createAndShowGUI(){
        this.setSize(WIDTH, HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new Panel();
        this.add(panel);

        timer.start();
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(panel.getRepaint()) {
            this.revalidate();
            this.repaint();
        }
    }

    private Timer getTimer(){
        return timer;
    }

}

class Panel extends JPanel {

    private Sort sort;
    private int width;

    private boolean repaint = true;

    Panel(){
        sort = new Sort();
        width = sort.getWidth();
    }

    @Override
    public void paintComponent(Graphics g){
        int[] arr = sort.getSort();
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

        repaint = sort.stepSelection();
    }

    boolean getRepaint(){
        return repaint;
    }
}

class Sort {

    private int[] vals;
    private int[] sort;

    private int width = 1;

    Sort(){
        vals = new int[GUI.WIDTH / width];
        int stepY = GUI.HEIGHT / vals.length;
        Random random = new Random();
        for(int i = 0; i < vals.length; i++){
            vals[i] = stepY * random.nextInt(vals.length);
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

    private int startingPosition = 0;

    boolean stepInsertion(){
        boolean change = false;
        for(int i = startingPosition + 1; i < vals.length; i++){
            if(sort[i] < sort[i-1]){
                int temp = sort[i];
                sort[i] = sort[i-1];
                sort[i-1] = temp;
                change = true;
            }
        }
        return change;
    }

    boolean stepBubble(){
        boolean change = false;
        for(int i = startingPosition; i < vals.length - 1; i++){
            if(sort[i] > sort[i+1]){
                int temp = sort[i];
                sort[i] = sort[i+1];
                sort[i+1] = temp;
                change = true;
            }
        }
        return change;
    }

    private int selectionPos = 0;
    boolean stepSelection(){
        int min = sort[selectionPos];
        int temp = min;
        int x = -1;
        boolean change = false;
        for(int i = selectionPos; i < sort.length; i++){
            if(sort[i] < min){
                min = sort[i];
                x = i;
                change = true;
            }
        }
        sort[selectionPos] = min;
        try {
            sort[x] = temp;
        } catch(ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }
        selectionPos++;
        return change;
    }

    int[] getSort(){
        return sort;
    }
    int getWidth(){
        return width;
    }
}