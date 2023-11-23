package guimaze;

import javax.swing.*;
import java.awt.*;

public class Wall extends JPanel{

    public boolean enabled;
    private String position;

    public Wall(boolean direction){

        this.enabled = direction;
    }

    public Wall(String position){
        this.position = position;
        enabled = true;
    }

    public void Draw(int cellPixels){
        //int smaller = (cellPixels/20);
        int smaller = 1;
        switch (position){
            case "NORTH":
                this.setPreferredSize(new Dimension(cellPixels, smaller));
                break;
            case "SOUTH":
                this.setPreferredSize(new Dimension(cellPixels, smaller));
                break;
            case "EAST":
                this.setPreferredSize(new Dimension(smaller, cellPixels));
                break;
            case "WEST":
                this.setPreferredSize(new Dimension(smaller, cellPixels));
                break;
        }
        if (enabled){
            setBackground(Color.BLACK);
        }else{
            setBackground(Color.WHITE);
        }
    }

    public void Draw(int cellPixels, Color background){
        //int smaller = (cellPixels/20);
        int smaller = 1;
        switch (position){
            case "NORTH":
                this.setPreferredSize(new Dimension(cellPixels, smaller));
                break;
            case "SOUTH":
                this.setPreferredSize(new Dimension(cellPixels, smaller));
                break;
            case "EAST":
                this.setPreferredSize(new Dimension(smaller, cellPixels));
                break;
            case "WEST":
                this.setPreferredSize(new Dimension(smaller, cellPixels));
                break;
        }
        if (enabled){
            setBackground(Color.BLACK);
        }else{
            setBackground(background);
        }
    }

    public void Disable(){
        enabled = false;
    }

    public void Enabled(){

    }

    public void Enable(){
        enabled = true;
    }



}
