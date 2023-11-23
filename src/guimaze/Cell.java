package guimaze;

import javax.swing.*;
import java.awt.*;
import java.awt.BorderLayout;


public class Cell {

    public boolean live;
    public String[] wallPos = {"NORTH", "SOUTH", "EAST", "WEST"};
    private Wall[] walls;
    public int[] coords;

    private JPanel pnlCell;

    public Cell(int[] xy){
        /**
         * constructs and instance of a Cell of specified coordinates
         */

        coords = xy;
        walls = new Wall[4];
        live = true;
        initializeWalls();

    }

    private void initializeWalls(){
        /**
         * initaialises the cells walls
         */
        for (int i = 0; i < 4; i++){
            walls[i] = new Wall(wallPos[i]);
        }

    }

    /*
    public void Draw(JPanel pane, int cellPixels, int x, int y){
        /**
         * @param pane - maze display panel
         * @param cellPixels - dimension of single square cell
         * @param x - length position of cell on 'pane'
         * @param y - height position of cell on 'pane'
         *//*
        pnlCell = new JPanel();
        int lengthPos = (x*cellPixels);
        int heightPos = (y*cellPixels);
        //System.out.println("Display dimensions: " + pane.getWidth() + " " + pane.getHeight());
        pnlCell.setBounds(lengthPos, heightPos, cellPixels, cellPixels);
        //System.out.println("in display pane, x=" + lengthPos + " y=" + heightPos + " and is " + cellPixels + " square");
        pnlCell.setBackground(Color.WHITE);


        pnlCell.setLayout(new BorderLayout());

        for (int i = 0; i < 4; i ++){
            walls[i].Draw(cellPixels);

            switch(i){
                case 0:
                    pnlCell.add(walls[i], BorderLayout.NORTH);
                    break;
                case 1:
                    pnlCell.add(walls[i], BorderLayout.SOUTH);
                    break;
                case 2:
                    pnlCell.add(walls[i], BorderLayout.EAST);
                    break;
                case 3:
                    pnlCell.add(walls[i], BorderLayout.WEST);
                    break;

            }




            //cell.add(walls[i], BorderLayout.wallPos[i]);
            //this functionality is more robust, just don't know how to implement
        }

        pane.add(pnlCell);

    }
    */



    public void Draw(JPanel pane, int cellPixels, int x, int y, int indicator){
        /**
         * Displays a cells onto a specified JPanel
         *
         * @param pane - maze display panel
         * @param cellPixels - dimension of single square cell
         * @param x - length position of cell on 'pane'
         * @param y - height position of cell on 'pane'
         */
        pnlCell = new JPanel();
        Color background;
        int lengthPos = (x*cellPixels);
        int heightPos = (y*cellPixels);
        //System.out.println("Display dimensions: " + pane.getWidth() + " " + pane.getHeight());
        pnlCell.setBounds(lengthPos, heightPos, cellPixels, cellPixels);
        //System.out.println("in display pane, x=" + lengthPos + " y=" + heightPos + " and is " + cellPixels + " square");
        if (indicator == 1){
            background = Color.GREEN;
            //pnlCell.setBackground(Color.GREEN);
        }else if (indicator == 2){
            background = Color.RED;
            //pnlCell.setBackground(Color.RED);
        }else if (indicator == 3){
            background = Color.LIGHT_GRAY;
            //pnlCell.setBackground();
        }else{
            background = Color.WHITE;
            //pnlCell.setBackground(Color.WHITE);
        }
        pnlCell.setBackground(background);



        pnlCell.setLayout(new BorderLayout());

        for (int i = 0; i < 4; i ++){
            walls[i].Draw(cellPixels, background);

            switch(i){
                case 0:
                    pnlCell.add(walls[i], BorderLayout.NORTH);
                    break;
                case 1:
                    pnlCell.add(walls[i], BorderLayout.SOUTH);
                    break;
                case 2:
                    pnlCell.add(walls[i], BorderLayout.EAST);
                    break;
                case 3:
                    pnlCell.add(walls[i], BorderLayout.WEST);
                    break;

            }




            //cell.add(walls[i], BorderLayout.wallPos[i]);
            //this functionality is more robust, just don't know how to implement
        }

        pane.add(pnlCell);

    }

    public int NumWallsEnabled(){
        /**
         * @return - the total number of walls of the cell
         */
        int tally = 0;
        for (int i = 0; i < 4; i ++){
            if(walls[i].enabled){
                tally += 1;
            }
        }
        return tally;
    }

    public void break_Wall(int wall){
        /**
         * breaks wall
         * @param wall - integer correlating to the specified wall
         */
        walls[wall].Disable();
    }

    public void add_Wall(int wall){
        /**
         * enables wall
         * @param wall - integer correlating to the specified wall
         */
        walls[wall].Enable();
    }

}