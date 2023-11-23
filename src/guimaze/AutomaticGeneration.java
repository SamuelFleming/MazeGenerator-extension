package guimaze;

import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Random;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AutomaticGeneration extends CreateMaze implements ActionListener, Runnable{
    /**
     * @author sam.fleming
     * @version 6
     *
     */

    //Logical Fields
    private ArrayList<int[]> enteredCells = new ArrayList<>();
    private int[] currentCell = new int[2];
    private final ArrayList<int[]> directions = new ArrayList<int[]>(
            Arrays.asList(new int[]{0, -1}, new int[]{0,1}, new int[]{1,0}, new int[]{-1,0} ));
    private final String[] directions_str = {"NORTH", "SOUTH", "EAST", "WEST"};

    private List<Integer> nextDirect = new ArrayList<>();

    //GUI Fields
    private JFrame frame;
    private JPanel displayPanel;
    private final int displayLength = 500;
    private final int displayHeight = 500;
    private boolean displayOptimal;

    private JPanel buttonPanel;
    private JButton btnInsertImg;
    private JButton btnSubmit;
    private JButton btnRegen;
    private JButton btnOptimal;

    private JPanel labelPanel;
    private JLabel optimalSolve;
    private JLabel lbloptimal;
    private JLabel deadEnds;
    private JLabel lbldead;
    private JLabel lblStart;
    private JLabel lblFinish;


    public int[] getCurrentCell(){
        return currentCell;
    }

    public void setCurrentCell(int[] node){
        currentCell = node;
    }

    private void CreateGUI(){ //will eventually be from GUI interface

        super.HideGUI();
        displayOptimal = false;
        displayPanel = new JPanel();
        displayPanel.setLayout(null);
        //displayPanel.setBackground(Color.GREEN);
        displayPanel.setBounds(25,25,displayLength, displayHeight);
        //displayPanel.add(new JLabel("[Area for working Maze]"));

        //this.maze.Draw(displayPanel);//old
        Draw();

        buttonPanel = new JPanel(new GridLayout(1, 3));
        //buttonPanel.setBackground(Color.RED);
        buttonPanel.setBounds(25, 600, 700, 150);
        createButtons();


        labelPanel = new JPanel(new GridLayout(5,2));
        labelPanel.setBounds(550, 25, 225, 500);
        createLabels();

        frame = new JFrame("Automatic Maze Generation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setSize(800, 800);
        //frame.setResizable(false);


        frame.add(labelPanel);
        frame.add(buttonPanel);
        frame.add(displayPanel);
        frame.setVisible(true);


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
        lbloptimal = new JLabel(opt);

        String deadper_1 = "Dead End Cells (%): ";
        String deadper = "";
        float dead_per = DeadEndPercentage();
        if(solve_percentage == 0){
            deadper = "No Dead Ends";
        }else{
            deadper = deadper.concat(deadper_1 + Float.toString(dead_per) + "%");
        }
        lbldead = new JLabel(deadper);

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
        //labelPanel.add(optimalSolve);
        labelPanel.add(lbloptimal);
        //labelPanel.add(deadEnds);
        labelPanel.add(lbldead);
        labelPanel.add(btnOptimal);

    }

    private void createButtons(){

        btnRegen = new JButton("Regenerate");
        btnInsertImg = new JButton("Insert Image");
        btnSubmit = new JButton("Submit");
        buttonPanel.add(btnRegen);
        buttonPanel.add(btnInsertImg);
        buttonPanel.add(btnSubmit);

        btnRegen.addActionListener(this);
        btnInsertImg.addActionListener(this);
        btnSubmit.addActionListener(this);
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

        //this.maze.Draw(displayPanel);//old
        Draw();
        createButtons();
        createLabels();

        frame.setVisible(true);

    }

    private void Draw(){

        if(displayOptimal){
            this.maze.Draw(displayPanel, this.maze.getSolution());
        }else{
            this.maze.Draw(displayPanel);
        }

    }

    public AutomaticGeneration(Maze maze) {
        /**
         * Constructs an automatic genertion for a specified maze
         *
         * @param maze - Reference to Maze instance just created to now be developed
         */
        super();
        this.maze = maze;
        this.maze.editDate = GetDate();
        //initializeMazeArray();


        AutoGenInitialize();


        AutoGenerate();
        CreateGUI();
    }



    private void AutoGenInitialize(){
        /**
         * initialises the nessesary variables for the generation
         */
        //currentCell = this.maze.startCell; - doesn't work - reference type logical errors
        currentCell[0] = this.maze.startCell[0];
        currentCell[1] = this.maze.startCell[1];

        //initialize enteredCells
        enteredCells = new ArrayList<>();
        int[] cell_add = new int[2];
        enteredCells.add(cell_add);
        cell_add[0] = currentCell[0];
        cell_add[1] = currentCell[1];
        Reset_nextDirect();
    }
    private String GetDate(){
        /**
         * Gets the current date and returns as string
         *
         * @return date in DD/MM/YYYY
         */
        String str = "";
        int day = LocalDate.now().getDayOfMonth();
        int month = LocalDate.now().getMonthValue();
        int year = LocalDate.now().getYear();
        str = str.concat(day + "/"+ month +"/"+ year);
        //str = str.concat(year + "/"+ month +"/"+ day);
        return str;
    }

    public void initializeMazeArray() {
        this.maze.cells = new Cell[this.maze.length][this.maze.height];
    }



    private void ResetFields(){
        this.enteredCells = new ArrayList<int[]>();
        this.currentCell = new int[2];
    }



    private float OptimalPercentage(){
        /**
         * @return - percentage of cells in the optimal (shortest) solution to maze
         */

        float total = this.maze.Total_CellOptimal();
        float percentage = total/validCells_size() * 100;
        //OR float percentage = total/(this.maze.length * this.maze.height);
        return percentage;
    }

    private float DeadEndPercentage(){
        /**
         * @return - percentage of cells that are a dead end (have 3 walls present)
         */

        return this.maze.DeadEnd_Percentage();
    }

    @Override
    public void run() {

    }


    private void Regen(){
        /**
         * Regenerates the maze to update the GUI
         */
        System.out.println("pressed 'Regen'");
        maze.populateMazeArray();
        AutoGenInitialize();
        AutoGenerate();
        updateFrame();

        this.maze.showRelations();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==btnRegen){
            Regen();
        }
        if(e.getSource()==btnInsertImg){
            System.out.println("pressed 'insert img'");
        }
        if(e.getSource()==btnSubmit){
            System.out.println("pressed 'submit'");
            HideGUI();
            MazeGenerator.GetInstance().ShowGUI();
        }
        if(e.getSource()==btnOptimal){
            System.out.println("pressed 'Optimal Route'");
            ToggleOptimal();
            updateFrame();
        }

    }

    private void ToggleOptimal(){
        if(displayOptimal){
            displayOptimal = false;
        }else{
            displayOptimal = true;
        }
    }


    private void AutoGenerate(){
        /**
         * Condition controlled loop excecutes 'AutoMoves' which populate collection enteredCells to automatically
         * generate the maze. Will stop performing moves once maze is fully generated.
         */
        //implement this when img/block cells are implemented
        System.out.println("Invalid Cells: " + this.maze.invalidCells.size());
        System.out.println("Entered Cells: " + enteredCells.get(0)[0] + "," + enteredCells.get(0)[1]);
        while (enteredCells.size() <= maze.NumValidCells()-1){

            AutoMove();

        }
        System.out.println("Num Entered Cells: " + enteredCells.size());
        Check_enteredCells_dupes();


    }

    //originally where NumValidCells() was called
    private int validCells_size(){
        /**
         * @return - the number of cells in maze that aren't disabled
         */
        int num;
        num = (maze.length * maze.height) - this.maze.invalidCells.size();
        return num;
    }


    private void AutoMove(){
        /**
         * From its starting state, currentCell, randomly generates directions to move into, break the respective
         * walls, and add new cell to eneteredCells . If no directions are available, will randomly change
         * currentCell to try again
         */
        //System.out.println("New AutoMove");
        boolean done = false;
        Reset_nextDirect();
        while (!nextDirect.isEmpty()&&!done){
            int move = GetRandomMove(nextDirect);
            //System.out.println("Move is (" + move + ") " + directions_str[move] + " " + directions.get(move)[0] + "," + directions.get(move)[1]);
            if (MoveIsValid(move)){
                break_exit_wall(move);
                break_entry_wall(move);

                int[] old = {currentCell[0], currentCell[1]};

                make_next_current(move);

                AddTo_enteredCells();
                int[] current_pass = {currentCell[0], currentCell[1]};
                Relation new_rel = new Relation(old, current_pass);
                this.maze.rels.add(new_rel);

                done = true;
            }else{
                Replace_nextDirect(move);
            }

        }
        if (!done){
            change_current_cell();
            Reset_nextDirect();
        }
    }

    private void AddTo_enteredCells(){
        /**
         * adds the currentCell to enteredCells avoiding reference type mishaps
         */
        int[] new_cell = new int[2];
        enteredCells.add(new_cell);
        new_cell[0] = currentCell[0];
        new_cell[1] = currentCell[1];
    }

    private void change_current_cell(){
        /**
         * After no available directions, the currentCell changed to a radnom
         * one that already has been entered - so next move can ensue
         */
        Random rand = new Random();
        int rand_cell_index = rand.nextInt(enteredCells.size());
        int[] cell = enteredCells.get(rand_cell_index);
        currentCell[0] = cell[0];
        currentCell[1] = cell[1];
        //currentCell = enteredCells.get(rand_cell_index);
        //currentCell[0] = enteredCells.get(rand_cell_index)[0];
        //currentCell[1] = enteredCells.get(rand_cell_index)[1];
        //System.out.println("New Random Current: " + currentCell[0] + currentCell[1]);
    }

    private void check_enteredCells(){
        /**
         * [used in development / testing]
         */
        for (int i = 0; i < enteredCells.size(); i++){
            System.out.print("Index: " + i + ",   ");
            System.out.print(" (" + enteredCells.get(i)[0] + "," + enteredCells.get(i)[1] + "), ");
        }
    }

    private void make_next_current(int move){
        /**
         * Makes the next cell (the checking cell) the now current cell after completing a move
         *
         * @param move - the integer index for a move either N,S,E or W (ref directions_str)
         */
        //check_enteredCells();
        //print_entered();
        int x = directions.get(move)[0] + currentCell[0];
        int y = directions.get(move)[1] + currentCell[1];
        currentCell[0] = x;
        currentCell[1] = y;
        System.out.println("");
        //print_entered();
        //check_enteredCells();

        //All elements of enteredCells are references/storages of the same instance 'currentCell'
            //- therefore whenever currentCell is changes, all elements in enteredCells are changed
    }

    private void print_entered(){
        /**
         * - used in development - to print entire collection 'enteredCells'
         */
        for (int i = 0; i < enteredCells.size(); i++){
            System.out.print(" (" + enteredCells.get(i)[0] + "," + enteredCells.get(i)[1] + "), ");
        }
        System.out.println("Start Cell: (" + this.maze.startCell[0] + "," + this.maze.startCell[1] + ")");
    }


    
    private void break_exit_wall(int move){
        /**
         * Disables the wall fom the exiting cell
         *
         * @param move - the integer index for a move either N,S,E or W (ref directions_str)
         */
        this.maze.cells[currentCell[0]][currentCell[1]].break_Wall(move);
        //System.out.println("Break wall " + move + " at cell: " + currentCell[0] + "," + currentCell[1]);
    }
    
    private void break_entry_wall(int move){
        /**
         * Disables the wall in the entering cell
         *
         * @param move - the integer index for a move either N,S,E or W (ref directions_str)
         */
        int wall = 0;
        switch (move){
            case 0:
                wall = 1;
                break;
            case 1:
                wall = 0;
                break;
            case 2:
                wall = 3;
                break;
            case 3:
                wall = 2;
                break;
        }
        int next_x = currentCell[0]+ directions.get(move)[0];
        int next_y = currentCell[1]+ directions.get(move)[1];
        this.maze.cells[next_x][next_y].break_Wall(wall);
        //System.out.println("Break wall " + wall + " at cell: " + next_x + "," + next_y);
    }

    private int GetRandomMove(List<Integer> nextDirect){
        /**
         * @param nextDirect - the arrayList of components for each directional move
         * @return - the integer index for a move either N,S,E or W (ref directions_str)
         */
        Random rand = new Random();
        int randDirection = nextDirect.get(rand.nextInt(nextDirect.size()));
        return randDirection;
    }



    public boolean MoveIsValid(int move){ //int move is the direction
        /**
         * @param move - the integer index for a move either N,S,E or W (ref directions_str)
         * @return - indicator whether checked cell can be entered
         */
        /*
        move is invalid when
            - move points outside of domain
            - move points to an invalid cell (img or blacked out)
            - move points to an already entered cell
         ie, move is onlt valid when
            - move points to an unentered valid cell within domain
         */
        int[] moveCoords = directions.get(move);
        int next_x = currentCell[0] + moveCoords[0];
        int next_y = currentCell[1] + moveCoords[1];


        if (moveInEntered(moveCoords, next_x, next_y)){
            //System.out.println("Invalid, Move is Already Entered");
            return false;
        }else if (!moveInDomain(next_x, next_y)){
            //System.out.println("Invalid, Move is outside domain");
            return false;
        }else if(!moveIsAvail(moveCoords, next_x, next_y)){
            //System.out.println("Invalid, Move is not available");
            return false;
        }else{
            //System.out.println("Move is Valid");
            return true;
        }
    }

    private boolean moveInEntered(int[] Coords, int next_x, int next_y){
        /**
         * @param Coords - index of new x,y components for checked cell from 'directions'
         * @param next_x - the length coordinate for the cell being checked
         * @param next_y - the height coordinate for the cell being checked
         * @return - indicator whether next checked cell has already been entered
         */

        //int[] xy = {next_x, next_y};
        boolean already_entered = CoordsExistsIn(Coords);

        if(already_entered){
            return true;
        }else{
            //System.out.println("Cell " + next_x +","+ next_y + " isn't in entered");
            return false;
        }

    }

    private boolean moveInDomain(int next_x, int next_y){
        /**
         * @param next_x - the length coordinate for the cell being checked
         * @param next_y - the height coordinate for the cell being checked
         * @return - indicator whether checked cell is within maze length&width domain
         */
        boolean x = 0<=next_x && next_x <= this.maze.length-1;
        boolean y = 0<=next_y && next_y <= this.maze.height-1;
        if(x && y){
            return true;
        }else{
            return false;
        }

    }

    private boolean moveIsAvail(Object Coords, int next_x, int next_y){
        /**
         * @param Coords
         * @param next_x - the length coordinate for the cell being checked
         * @param next_y - the height coordinate for the cell being checked
         * @return - indicator whether checked cell is not disabled
         */
        return true;
    }

    private void Reset_nextDirect(){
        for (int j = nextDirect.size(); j < 4; j++){
            nextDirect.add(0);
        }
        for (int i = 0; i < 4; i++){
            nextDirect.set(i, i);
        }

    }

    private void Replace_nextDirect(int move){
        /**
         * @param move - the integer index for a move either N,S,E or W (ref directions_str)
         */
        for (int i = 0; i < nextDirect.size(); i++){
            if (nextDirect.get(i) == move){
                nextDirect.remove(i);
            }
        }
    }

    private boolean CoordsExistsIn(int[] Coords){
        /**
         * @param Coords - index of new x,y components for checked cell from 'directions'
         * @return - Indicator whether checked cell is already entered
         */
        int[] next = {currentCell[0] + Coords[0], currentCell[1] + Coords[1]};
        for (int i = 0; i < enteredCells.size(); i++){
            //System.out.print("enteredCells[" + i + "]: (" + enteredCells.get(i)[0] + "," + enteredCells.get(i)[1] + ")");
            //System.out.println("  vs  " + "Checked Cell: (" + next[0] + "," + next[1] + ")");
            if((next[0] == enteredCells.get(i)[0])&&(next[1] == enteredCells.get(i)[1])){
                return true;
            }
        }
        return false;
    }

    private void Check_enteredCells_dupes(){
        /**
         * method used in development
         */
        ArrayList<int[]> dupes = new ArrayList<>();
        int[] cell_tally = new int[enteredCells.size()];
        String str = "";
        for (int i = 0; i < enteredCells.size(); i++){
            int[] tester = enteredCells.get(i);
            int tally = 0;
            for (int j = 0; j < enteredCells.size(); j++){
                int[] checker = enteredCells.get(j);
                if(checker[0] == tester[0] && checker[1] == tester[1]){
                    tally += 1;
                }
            }
            cell_tally[i] = tally;
        }
        for (int i = 0; i < cell_tally.length; i++){
            if (cell_tally[i] > 1){
                int[] next = new int[2];
                dupes.add(next);
                next[0] = enteredCells.get(i)[0];
                next[1] = enteredCells.get(i)[1];
            }
        }
        for (int k = 0; k < dupes.size(); k++){
            str = str.concat(", (" + dupes.get(k)[0] + "," + dupes.get(k)[1] + ")");
        }
        double dupl = (dupes.size()/2);
        //System.out.print(dupl + " duplicates in entered Cells: ");
        //System.out.println(str);
    }


    protected void HideGUI(){
        frame.dispose();
    }
    public void DisplayGUI(){
        frame.setVisible(true);
    }

}