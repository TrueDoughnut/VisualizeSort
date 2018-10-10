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

    @SuppressWarnings("Duplicates")
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
        this.setSize(GUI.WIDTH, GUI.HEIGHT);
        sort = new Sort(this.getWidth(), this.getHeight());
        width = sort.getWidth();
        height = sort.getHeight();
    }

    @Override
    public void paintComponent(Graphics g){
        int[] arr = sort.getSort();
        boolean[] accessed = sort.getAccessed();

        Graphics2D g2d = (Graphics2D) g;

        g2d.setBackground(Color.WHITE);

        for(int i = 0; i < arr.length; i++){
            if(accessed[i]){
                g2d.setColor(Color.RED);
            } else {
                g2d.setColor(Color.DARK_GRAY);
            }
            g2d.fill(new Rectangle(i * width, this.getHeight() - arr[i] * height, width, arr[i] * height));
        }

        sort.stepSelection();
    }
}

class Sort {

    private int[] vals;
    private int[] sort;
    private boolean[] accessed;

    private int width = 20;
    private int height = 20;

    Sort(int width, int height){
        vals = new int[width / this.width];
        accessed = new boolean[vals.length];
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
        accessed = new boolean[vals.length];
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
    }

    void stepBubble(){
        accessed = new boolean[vals.length];
        for(int i = 0; i < vals.length - 1; i++){
            if(sort[i] > sort[i+1]){
                int temp = sort[i];
                sort[i] = sort[i+1];
                sort[i+1] = temp;
            }
            accessed[i] = true;
            accessed[i + 1] = true;
        }
    }

    private int selectionPos = 0;
    void stepSelection(){
        accessed = new boolean[vals.length];
        if(selectionPos >= sort.length){
            return;
        }
        int min = sort[selectionPos];
        accessed[selectionPos] = true;
        int temp = min;
        int x = -1;
        for(int i = selectionPos; i < sort.length; i++){
            if(sort[i] < min){
                min = sort[i];
                x = i;
            }
            accessed[i] = true;
        }
        sort[selectionPos] = min;

        if(x >= 0){
            sort[x] = temp;
            accessed[x] = true;
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
    boolean[] getAccessed(){
        return accessed;
    }
}