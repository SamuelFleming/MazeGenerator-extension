package guimaze;

import java.util.*;
import javax.swing.JPanel;



public class Maze{

    /**
     * @author sam.fleming
     * @version 7
     *
     */

    //constructor fields
    public String title;
    public String author;
    public String createDate;
    public String editDate;
    public int length;
    public int height;

    public Cell[][] cells;
    public List<int[]> invalidCells;
    public List<Relation> rels;


    public int[] startCell = new int[2];
    public int[] finishCell = new int[2];

    class Solution{

        private Maze maze;

        public List<int[]> path;
        public boolean solvable;
        private List<int[]> cellCoords;

        private Deque q;

        public Solution(Maze maze){
            this.maze = maze;
            this.maze.showRelations();
            cellCoords = popCellCoords();
            path = reconstructPath(this.maze.startCell, this.maze.finishCell, Solve());
        }

        private int[][][] Solve(){
            /**
             * Using breadth first search algorithm for finding shortest path, interatively works through each of the
             * distance classes sand stores the parent pf each cell in 'prev'. It does this starting at the start cell
             * until it reaches the end cell to which is terminates and returns 'prev'.
             */
            q = new LinkedList<int[]>();
            q.add(this.maze.startCell);
            Boolean[][] visited = initialiseVisited();
            visited[startCell[0]][startCell[1]] = true;

            int[][][] prev = initialisePrev();
            while(!q.isEmpty()){
                int[] node = (int[])q.getFirst();
                System.out.println("New node: (" + node[0] + "," + node[1] + ")");
                q.remove(node);//get next in queue and remove it from queue

                //find it's list of integers
                List<int[]> neighbours = getNeighbours(node);

                for(int i = 0; i < neighbours.size(); i++){
                    int[] next = neighbours.get(i);
                    int x = next[0];
                    int y = next[1];
                    if(!visited[x][y]){
                        System.out.println("Queue (" + x + "," + y + ")");
                        q.add(next);
                        visited[x][y] = true;
                        prev[x][y] = node;
                    }
                }
            }
            System.out.println("Prev Size: " + prev.length);
            return prev;

        }


        private List<int[]> reconstructPath(int[] start, int[] end, int[][][] prev){
            /**
             * With the collection prev, it finds the optimal route by starting at the end cell, and iteratively finding
             * it's previous cell and stroing in a list 'path_'. Once it reaches the start cell it returns 'path_'
             *
             * @param start - the start cell
             * @param end - the start cell
             * @param prev - all cells previous cell
             *
             * @return path_ - a list of cell coordinates that are in the optimal route
             */
            int[] current;
            List<int[]> path_ = new ArrayList<>();

            for(current = end; current != null; current = prev[current[0]][current[1]]){
                path_.add(current);
            }
            Collections.reverse(path_);
            if(path_.get(0) == start){
                //path = path_;
                solvable = true;
            }else{
                //path = null;
                solvable = false;
            }
            print_path(path_, "Path: ");
            return path_;
        }

        private void print_path(List<int[]> path, String str){
            /**
             * - used in development - to print entire collection 'enteredCells'
             */
            System.out.println(str);
            for (int i = 0; i < path.size(); i++){
                System.out.print(" (" + path.get(i)[0] + "," + path.get(i)[1] + "), ");
            }
            System.out.println("Start Cell: (" + this.maze.startCell[0] + "," + this.maze.startCell[1] + ")");
        }

        private List<int[]> getNeighbours(int[] node){
            /**
             * With a specific cell co-ordinate, gets all of it's adjacent cells in a generated maze
             *
             * @param node - a cell x,y to find it's adjacent (entereable) cells
             */
            System.out.println();
            System.out.println("Get Neighbours of : (" + node[0] + "," + node[1] + ")");
            List<int[]> neighbours = new ArrayList<int[]>();
            for (int i = 0; i < this.maze.rels.size(); i++){
                Relation checked_rel = this.maze.rels.get(i);
                //System.out.print("Checked rel: ");
                checked_rel.printRel();
                if(checked_rel.rel.get(0)[0] == node[0] && checked_rel.rel.get(0)[1] == node[1]){
                    //System.out.println("(" + checked_rel.rel.get(1)[0] + "," + checked_rel.rel.get(1)[1] + ") is a neighbour");
                    neighbours.add(checked_rel.rel.get(1));
                }else if(checked_rel.rel.get(1)[0] == node[0] && checked_rel.rel.get(1)[1] == node[1]){
                    //System.out.println("(" + checked_rel.rel.get(0)[0] + "," + checked_rel.rel.get(0)[1] + ") is a neighbour");
                    neighbours.add(checked_rel.rel.get(0));
                }else{
                    //System.out.println("No Neighbour");
                }
            }
            System.out.println("Node (" + node[0] + "," + node[1] + "):");
            print_path(neighbours, " has Neighbours: ");
            return neighbours;
        }

        private List<int[]> popCellCoords(){
            List<int[]> cellCoords = new ArrayList<int[]>();
            for (int i = 0; i < this.maze.length; i++){
                for( int j = 0; j < this.maze.height; j++){
                    int[] cell = {i, j};
                    cellCoords.add(cell);
                }
            }
            return cellCoords;
        }

        private Boolean[][] initialiseVisited(){
            Boolean[][] visited = new Boolean[this.maze.length][this.maze.height];
            for (int i = 0; i < this.maze.length; i++){
                for( int j = 0; j < this.maze.height; j++){
                    visited[i][j] = false;
                }
            }
            return visited;
        }

        private int[][][] initialisePrev(){
            int[][][] prev = new int[this.maze.length][this.maze.height][2];
            for (int i = 0; i < this.maze.length; i++){
                for( int j = 0; j < this.maze.height; j++){
                    prev[i][j] = null;
                }
            }
            return prev;
        }

    }


    public Maze(String title,String date,String author, int length, int height){
        /**
         * Constructs an instance of Maze where the start and end cell are not specified
         */
        this.title = title;
        this.author = author;
        this.length = length;
        this.height = height;
        this.createDate = date;
        this.editDate = date;
        cells = new Cell[length][height];
        startCell[0] = 0;
        startCell[1] = 0;
        finishCell[0] = length - 1;
        finishCell[1] = height -1;

        populateMazeArray();

    }

    public Maze(String title,String date,String author, int length, int height, int[]start, int[]end){
        /**
         * Constructs an instance of Maze where start and end cell are specified
         */
        this.title = title;
        this.author = author;
        this.length = length;
        this.height = height;
        this.createDate = date;
        this.editDate = date;
        cells = new Cell[length][height];
        startCell[0] = start[0];
        startCell[1] = start[1];
        finishCell[0] = end[0];
        finishCell[1] = end[1];

        populateMazeArray();

    }

    public void print_rels(){
        /**
         * used in developement / testing
         */
        System.out.println("Maze Relations:");
        for (int i = 0; i < this.rels.size(); i++){
            rels.get(i).printRel();
        }
    }

    private List<int[]> GetCellCoords(){
        /**
         * used in developement / testing
         */
        List<int[]> cellCoords = new ArrayList<int[]>();
        for (int i = 0; i < length; i++){
            for (int j = 0; i < height; i++){
                int[] cell = {i, j};
                if (cells[i][j].live){
                    cellCoords.add(cell);
                }
            }
        }
        return cellCoords;
    }

    public void showRelations(){
        /**
         * used in developement / testing - prints all relations (open walls) of a generated maze
         */
        System.out.println("Relations: ");
        for (int i = 0; i < rels.size(); i++){
            String str = "";
            Relation rela = rels.get(i);
            int[] first = rela.rel.get(0);
            int[] second = rela.rel.get(1);
            str = str.concat("("+ first[0] + "," + first[1] + "), (" + second[0] + "," + second[1] + ")");
            System.out.println(str);

        }
    }

    public void populateMazeArray(){
        /**
         * Initializes the maze by closing all of its walls (no relations)
         */
        invalidCells = new ArrayList<int[]>();
        rels = new ArrayList<Relation>();

        for (int len = 0; len < length; len ++){
            //System.out.println("len =" + len);
            for (int hei = 0; hei < height; hei++ ){
                //System.out.println("hei =" + hei);
                int[] coords = {len, hei};
                //System.out.println("Cell at pos; " + len + ", " + hei + " created");
                this.cells[len][hei] = new Cell(coords);

            }
        }
    }

    public float DeadEnd_Percentage(){
        /**
         * Calculates the dead end percentage of the maze and returns as a float
         *
         * @return - dead end percentage
         */
        float percentage = 0;

        int total = Total_DeadEnd();

        System.out.println("Dead End Cells Total:  " + total);
        float totalValid = (float)NumValidCells();
        float f_total = (float)total;
        percentage = (f_total/totalValid)*100;
        System.out.println("Dead end Percentage: " + percentage);
        return percentage;
    }

    public int Total_DeadEnd(){
        /**
         * Gets the total number of dead end cells of a maze (dead end is where a cell has 3 walls)
         *
         * @return - number of cells as integer
         */
        int tally = 0;
        for (int i = 0; i < length; i++){
            for (int j = 0; j < height; j++){
                if (cells[i][j].NumWallsEnabled() == 3 && cells[i][j].live){
                    tally += 1;
                }
            }

        }
        return tally;
    }

    public String TotalCellOptimal(){
        return "[not yet implemented]";
    }

    public int Total_CellOptimal(){
        /**
         * Gets the total number of  cells in a maze's optimal solution returns as an int
         *
         * @return - number of cells
         */
        Solution solve = new Solution(this);
        System.out.println("Optimal Solve Size: " + solve.path.size());
        return solve.path.size();
    }

    public List<int[]> getSolution(){
        /**
         * Gets the maze's optimal solution as list of its relevent cell coordinates
         *
         * @return - list of cell coordinates
         */

        Solution solution = new Solution(this);
        return solution.path;
    }

    public int NumValidCells(){
        /**
         * Gets the number of Valid cells in a Maze
         *
         * @return - number of cells
         */
        int num;
        num = (length * height) - invalidCells.size();
        return num;
    }

    public void Draw(JPanel pane){
        /**
         * Draws the Maze using a series of javax.swing components onto a specified JPanel
         *
         * @param pane - Jpanel to draw maze onto
         *
         */

        pane.removeAll();


        int pane_x = pane.getWidth();
        int pane_y = pane.getHeight();
        int longest_side;

        //checks that displaypanel is square
        if (pane_x != pane_y){
            System.out.println("Invalid Display Panel");
        }

        if (length >= height){
            longest_side = length;
        }else{
            longest_side = height;
        }

        int cellPixels = (int)((double)pane_x/(double)longest_side);
            //pixel size of each cell (rounded down)


        for (int l = 0; l < this.length; l++){
            //System.out.println("l =" + l);
            for (int h = 0; h < this.height; h++ ){
                //System.out.println("h =" + h);
                this.cells[l][h].Draw(pane, cellPixels, l, h, 0);
                    //evaluates as null for some reason
            }
        }
    }

    public void Draw(JPanel pane, List<int[]> path){
        /**
         * Draws the Mazeand it's optimal solution using a series of javax.swing components onto a specified JPanel,
         *
         *
         * @param pane - Jpanel to draw maze onto
         * @param path - list of cell Coords in the Maze's optimal route
         *
         */

        pane.removeAll();


        int pane_x = pane.getWidth();
        int pane_y = pane.getHeight();
        int longest_side;

        //checks that displaypanel is square
        if (pane_x != pane_y){
            System.out.println("Invalid Display Panel");
        }

        if (length >= height){
            longest_side = length;
        }else{
            longest_side = height;
        }

        int cellPixels = (int)((double)pane_x/(double)longest_side);
        //pixel size of each cell (rounded down)


        for (int l = 0; l < this.length; l++){
            //System.out.println("l =" + l);
            for (int h = 0; h < this.height; h++ ){
                //System.out.println("h =" + h);
                int[] temp = {l, h};
                System.out.println("Temp: (" + l + "," + h + ")");
                if (startCell[0] == temp[0] && startCell[1] == temp[1]){
                    this.cells[l][h].Draw(pane, cellPixels, l, h, 1);
                }else if(finishCell[0] == temp[0] && finishCell[1] == temp[1]){
                    this.cells[l][h].Draw(pane, cellPixels, l, h, 2);
                }else if(ExistsIn(path, temp)){
                    this.cells[l][h].Draw(pane, cellPixels, l, h, 3);
                }else{
                    this.cells[l][h].Draw(pane, cellPixels, l, h, 0);
                }


                //evaluates as null for some reason
            }
        }
    }


    private boolean ExistsIn(List<int[]> path,int[] node){
        /**
         * Determines whether a specified integer pair exists in a specified list of integer pairs
         *
         * @param path - list of cell Coords in the Maze's optimal route
         * @param node - coordinates of a single cell
         */
        boolean exists = false;
        for (int i = 0; i < path.size(); i++){
            int[] current = {path.get(i)[0], path.get(i)[1]};
            if(current[0] == node[0] && current[1] == node[1]){
                return true;
            }
        }
        return false;
    }

}


