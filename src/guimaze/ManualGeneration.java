package guimaze;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManualGeneration extends CreateMaze implements ActionListener, Runnable{
    /**
     * @author bradley mcgrath
     * @version 7
     */

    //Logical Fields
    private List<int[]> enteredCells = new ArrayList<int[]>();
    private int[] currentCell = new int[2];
    private int[] nextCell = new int[2];


    //GUI Fields
    private JFrame frame;
    private JPanel displayPanel;
    private boolean displayOptimal = false;

    private final int displayLength = 500;
    private final int displayHeight = 500;

    private JPanel buttonPanel;
    private JButton btnInsertImg;
    private JButton btnSubmit;
    private JButton btnUpdate;
    private JButton btnRemoveWalls;

    private JPanel labelPanel;
    private JLabel isSolvable;
    private JLabel solveable;
    private JLabel optimalSolve;
    private JLabel optimal;
    private JLabel deadEnds;
    private JLabel dead;
    private JLabel lblStart;
    private JLabel lblFinish;
    private JButton btnOptimal;

    private String author;
    private String title;
    private int length;
    private int height;

    private ManualGenDialog GenDialog;
    // private String ;


    private JDialog wall_remove_dialog;

    private final List<int[]> directions = new ArrayList<int[]>(
            Arrays.asList(new int[]{0, 1}, new int[]{0,-1}, new int[]{-1,0}, new int[]{1,0} ));



    public ManualGeneration(Maze maze) {
        /**
         * @param maze - Reference to Maze instance just created to now be developed
         */
        super();
        this.maze = maze;
        this.maze.editDate = GetDate();

        currentCell = this.maze.startCell;
        enteredCells.add(currentCell);

        author = this.maze.author;
        title = this.maze.title;
        length = this.maze.length;
        height = this.maze.height;

        //maze.Wall

        CreateGUI();

    }



    public void updateFrame(){
        /**
         * updates content(state) of GUI elements contained in JFrame by rebuilding each panel individually
         * in the JFrame
         */
        buttonPanel.removeAll();
        labelPanel.removeAll();
        frame.setVisible(false);

        System.out.println("frame has been updated");

        this.maze.Draw(displayPanel);
        createButtons();
        createLabels();

        frame.setVisible(true);

    }

    private void createButtons(){
        btnUpdate = new JButton("Reset");
        btnInsertImg = new JButton("Insert Image");
        btnSubmit = new JButton("Submit Maze");
        btnRemoveWalls = new JButton("Remove Walls");

        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnInsertImg);
        buttonPanel.add(btnSubmit);
        buttonPanel.add(btnRemoveWalls);

        btnUpdate.addActionListener(this);
        btnInsertImg.addActionListener(this);
        btnSubmit.addActionListener(this);
        btnRemoveWalls.addActionListener(this);
    }

    private void createLabels(){

        String opt_1 = "Optimal Solve (%):";
        String opt = "";
        float solve_percentage = OptimalPercentage();
        if(solve_percentage == 0){
            opt = "Maze is Unsolvable";
        }else{
            opt = opt.concat(opt_1 + Float.toString(solve_percentage) + "%");
        }
        optimal = new JLabel(opt);
        String deadper_1 = "Dead End Cells (%): ";
        String deadper = "";
        float dead_per = DeadEndPercentage();
        if(solve_percentage == 0){
            deadper = "No Dead Ends";
        }else{
            deadper = deadper.concat(deadper_1 + Float.toString(dead_per) + "%");
        }
        dead = new JLabel(deadper);

        String start = "";
        start = start.concat("Start Cell: (" + this.maze.startCell[0] + "," + this.maze.startCell[1] + ")" );
        lblStart = new JLabel(start);

        String finish = "";
        finish = finish.concat("Finish Cell: (" + this.maze.finishCell[0] + "," + this.maze.finishCell[1] + ")");
        lblFinish = new JLabel(finish);

        btnOptimal = new JButton("Optimal Route");
        btnOptimal.addActionListener(this);

        labelPanel.add(lblStart);
        labelPanel.add(lblFinish);

        labelPanel.add(optimal);

        labelPanel.add(dead);
        labelPanel.add(btnOptimal);


    }

    private void CreateGUI(){ //will eventually be from GUI interface

        super.HideGUI();
        displayPanel = new JPanel();
        displayPanel.setLayout(null);
        displayPanel.setBackground(Color.RED);
        displayPanel.setBounds(25,25,displayLength, displayHeight);


        this.maze.Draw(displayPanel);


        buttonPanel = new JPanel(new GridLayout(1, 3));
        buttonPanel.setBackground(Color.RED);
        buttonPanel.setBounds(25, 600, 700, 150);

        createButtons();

        labelPanel = new JPanel(new GridLayout(5,2));
        labelPanel.setBounds(550, 25, 225, 500);


        createLabels();

        frame = new JFrame("Manual Maze Generation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setSize(800, 800);
        //frame.setResizable(false);


        frame.add(labelPanel);
        frame.add(buttonPanel);
        frame.add(displayPanel);
        frame.setVisible(true);


    }

    private void ResetFields(){
        this.enteredCells = new ArrayList<int[]>();
        this.currentCell = new int[2];
    }

    private float OptimalPercentage(){
        /**
         * @return - percentage of cells in the optimal (shortest) solution to maze
         */
        /*
        Random rand = new Random();
        float result = (float)rand.nextInt(100-1) + 1;
        return result;
        //dummy value - random percentage

         */

        float total = this.maze.Total_CellOptimal();
        float percentage = total/validCells_size() * 100;
        //OR float percentage = total/(this.maze.length * this.maze.height);
        return percentage;
    }

    private int validCells_size(){
        /**
         * @return - the number of cells in maze that aren't disabled
         */
        int num;
        num = (maze.length * maze.height) - this.maze.invalidCells.size();
        return num;
    }

    private float DeadEndPercentage(){
        /**
         * @return - percentage of cells that are a dead end (have 3 walls present)
         */

        return this.maze.DeadEnd_Percentage();
    }

    private List<int[]> wallDialog(){

        return new ArrayList<int[]>();
    }

    public void RemoveWalls(int[][] data){
        frame.setVisible(false);

        System.out.println("Remove walls");


        //maze.cells[2][1].break_Wall(1);
        // maze.cells[1][1].break_Wall(2);


        System.out.println("Walls should be broken");
        System.out.println("Length of array" + data.length);
        int walls = 0;
        int nextWalls = 0;



        if((data[0][0]-data[0][1]) > 1 || (data[0][1]-data[0][0]) > 1 || (data[0][2]-data[0][3]) > 1 || (data[0][3]-data[0][2]) > 1){
            System.out.println("Bigger than 1 cell apart");
            for (int i = 0; i < data.length; i++) {
                currentCell[0] = data[i][0];
                nextCell[0] = data[i][1];
                currentCell[1] = data[i][2];
                nextCell[1] = data[i][3];
                if(currentCell[0] < nextCell[0]){
                    walls = 2;
                    nextWalls = 3;
                    System.out.println("East");
                } else if (currentCell[0] > nextCell[0]){
                    walls = 3;
                    nextWalls=2;
                    System.out.println("West");
                } else if(currentCell[1] < nextCell[1]){
                    walls = 1;
                    nextWalls=0;
                    System.out.println("South");
                } else if (currentCell[1] > nextCell[1]){
                    walls = 0;
                    nextWalls=1;
                    System.out.println("North");
                }
                RemoveAlotOfWalls(currentCell,nextCell,walls,nextWalls);
            }

        } else{
            for (int i = 0; i < data.length; i++) {

                currentCell[0] = data[i][0];
                nextCell[0] = data[i][1];
                currentCell[1] = data[i][2];
                nextCell[1] = data[i][3];
                System.out.println("Current cell{0} is x1"+ currentCell[0]);
                System.out.println("Current cell{1} is y1 "+ currentCell[1]);
                System.out.println("next cell{0} is x2"+ nextCell[0]);
                System.out.println("next cell{1} is y2"+ nextCell[1]);

                if(currentCell[0] < nextCell[0]){
                    walls = 2;
                    nextWalls = 3;
                    System.out.println("East");
                } else if (currentCell[0] > nextCell[0]){
                    walls = 3;
                    nextWalls=2;
                    System.out.println("West");
                } else if(currentCell[1] < nextCell[1]){
                    walls = 1;
                    nextWalls=0;
                    System.out.println("South");
                } else if (currentCell[1] > nextCell[1]){
                    walls = 0;
                    nextWalls=1;
                    System.out.println("North");
                }



                //old code:
                maze.cells[currentCell[0]][currentCell[1]].break_Wall(walls);
                maze.cells[nextCell[0]][nextCell[1]].break_Wall(nextWalls);


                /*
                int[] old_pass = {currentCell[0], currentCell[1]};
                int[] new_pass = {nextCell[0], nextCell[1]};
                maze.cells[old_pass[0]][old_pass[1]].break_Wall(walls);
                maze.cells[new_pass[0]][new_pass[1]].break_Wall(nextWalls);
                Relation new_rel = new Relation(old_pass, new_pass);
                this.maze.rels.add(new_rel);
                this.maze.print_rels();

                 */


            }
        }

        this.maze.Draw(displayPanel);
        frame.setVisible(true);
    }

    public void RemoveAlotOfWalls(int[] current, int[] next , int move, int nextmove){
        int temp = 0;
        System.out.println("Remove alot of walls");

        if(move == 0 ||move == 1 ){

            if(current[1] > next[1]){
                temp = current[1];
            } else {
                temp = next[1];
            }
            for(int i = 0; i <= temp;i++){
                //old code
                maze.cells[current[0]][i].break_Wall(move);

                maze.cells[next[0]][i].break_Wall(nextmove);


                /*
                int[] old_pass = {current[0], i};
                int[] new_pass = {next[0], i};
                maze.cells[old_pass[0]][old_pass[1]].break_Wall(move);
                maze.cells[new_pass[0]][new_pass[1]].break_Wall(nextmove);
                Relation new_rel = new Relation(old_pass, new_pass);
                this.maze.rels.add(new_rel);
                this.maze.print_rels();

                 */

                //System.out.println("Current Cells are:" + maze.cells[current[0]][i] + "Next Cells are" + maze.cells[next[0]][next[1]+i]);
            }

            maze.cells[current[0]][current[1]].add_Wall(nextmove);
            maze.cells[next[0]][next[1]].add_Wall(move);



        } else if (move == 2 || move == 3  ){

            if(current[0] > next[0]){
                temp = current[0];
            } else {
                temp = next[0];
            }
            for(int i = 0; i <= temp;i++){
                //old code
                maze.cells[i][current[1]].break_Wall(move);
                maze.cells[i][next[1]].break_Wall(nextmove);


                /*
                int[] old_pass = { i, current[i]};
                int[] new_pass = {i, next[1]};
                maze.cells[old_pass[0]][old_pass[1]].break_Wall(move);
                maze.cells[new_pass[0]][new_pass[1]].break_Wall(nextmove);
                Relation new_rel = new Relation(old_pass, new_pass);
                this.maze.rels.add(new_rel);
                this.maze.print_rels();

                 */
            }

            maze.cells[next[0]][next[1]].add_Wall(move);
            maze.cells[current[0]][current[1]].add_Wall(nextmove);
            //maze.cells[next[0]][next[1]].add_Wall(move);

        }

        this.maze.Draw(displayPanel);
        frame.setVisible(true);

    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==btnUpdate){
            System.out.println("pressed 'Reset'");
            maze.populateMazeArray();
            updateFrame();
        }
        if(e.getSource()==btnInsertImg){
            System.out.println("pressed 'insert img'");

        }
        if(e.getSource()==btnSubmit){
            System.out.println("pressed 'submit'");

            HideGUI();
            MazeGenerator.GetInstance().ShowGUI();
        }
        if(e.getSource()==btnRemoveWalls){
            System.out.println("pressed 'Remove walls'");
            //   HideGUI();
            //  MazeGenerator.GetInstance().ShowGUI();
            GenDialog = new ManualGenDialog(maze,this);

        }
        if(e.getSource()==btnOptimal){
            System.out.println("pressed 'Optimal route'");
            ToggleOptimal();

        }


    }

    private void ToggleOptimal(){
        if(displayOptimal){
            displayOptimal = false;
        }else{
            displayOptimal = true;
        }
    }

    private String GetDate(){
        String str = "";
        int day = LocalDate.now().getDayOfMonth();
        int month = LocalDate.now().getMonthValue();
        int year = LocalDate.now().getYear();
        str = str.concat(day + "/"+ month +"/"+ year);
        return str;
    }


    protected void HideGUI(){
        frame.dispose();
    }
    public void DisplayGUI(){
        frame.setVisible(true);
    }


}
