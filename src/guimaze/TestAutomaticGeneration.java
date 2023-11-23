package guimaze;


import static org.junit.jupiter.api.Assertions.*;
//import org.junit.api.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestAutomaticGeneration {

    Maze maze;
    AutomaticGeneration autogen;

    @BeforeEach
    public void ConstructMaze(){
        String title = "TestMaze";
        String date = "01/01/2000";
        String author = "Programmer";
        int length = 3;
        int height = 3;
        maze = new Maze(title, date, author,length, height);
        maze.populateMazeArray();

        autogen = new AutomaticGeneration(maze);
        autogen.maze = new Maze(title, date, author,length, height);
    }


    @Test
    public void TestMoveIsValid(){
        //moves (North, south, east, west)
        int[] node = {0, 0};
        int move = 0;
        autogen.setCurrentCell(node);
        boolean actual = false;

        assertEquals(autogen.MoveIsValid(move), actual);

    }




}
