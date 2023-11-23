package guimaze;

import static org.junit.jupiter.api.Assertions.*;
//import org.junit.api.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestMaze {

    Maze maze;

    @BeforeEach
    public void ConstructMaze(){
        String title = "TestMaze";
        String date = "01/01/2000";
        String author = "Programmer";
        int length = 5;
        int height = 5;
        maze = new Maze(title, date, author,length, height);
        maze.populateMazeArray();



    }


    @Test
    public void CellsExist(){
        int len = maze.cells.length;
        int supposed_len = maze.length * maze.height;
        System.out.println("Actual Length: " + len);
        System.out.println("Supposed Length: "+ supposed_len);
        assertEquals(len, supposed_len);
    }

    @Test
    public void SimpleTest(){
        int x = 5;
        int y = 5;
        assertEquals(x, y);
    }


}
