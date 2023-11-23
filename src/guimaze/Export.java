package guimaze;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.Buffer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author bradley.mcgrath
 * @version 3
 */

public class Export implements ActionListener, Runnable {

    protected Maze maze;

    JPanel pnlDisplay;
    JPanel pnlOptimal;
    private final int displayLength = 500;
    private final int displayHeight = 500;

    public static final int WIDTH = 800;
    public static final int HEIGHT = 800;



    JLabel headerLabel = new JLabel("Export");
    JButton btnBack = new JButton("Submit");
    JFrame frame;
    ByteArrayInputStream blob;
    FileInputStream in;
    byte[] imageInByte;
    ByteArrayInputStream bais;
    ByteArrayOutputStream baos;
    FileInputStream fis;
    File file;
    //changes

    private PreparedStatement addMaze;

    public static final String CREATE_TABLE_1 =
            "CREATE TABLE IF NOT EXISTS mazes ("
                    + "idx INTEGER PRIMARY KEY /*!40101 AUTO_INCREMENT */ NOT NULL UNIQUE,"
                    + "title VARCHAR(30),"
                    + "length VARCHAR(30),"
                    + "height VARCHAR(20),"
                    + "author VARCHAR(10),"
                    + "date VARCHAR(30),"
                    + "cells BLOB" + ");";





    private static final String INSERT_MAZE = "INSERT INTO mazes (title, length, height, author, date) VALUES (?, ?, ?, ?, ?);";


    private static final String INSERT_MAZES = "INSERT INTO mazes (title, length, height, author, date, cells) VALUES (?, ?, ?, ?, ?, ?);";




    private Connection connection;
    ///changes
    public void addMaze() {

        try {
            /* BEGIN MISSING CODE */
            addMaze.setString(1, maze.title);
            addMaze.setString(2, String.valueOf(maze.length));
            addMaze.setString(3, String.valueOf(maze.height));
            addMaze.setString(4, maze.author);
            addMaze.setString(5, maze.createDate);
           // addMaze.setBytes(6, imageInByte);
            addMaze.setBinaryStream(6, fis, (int) file.length());
            addMaze.execute();
            /* END MISSING CODE */
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    //changes

    public Export(Maze maze){
        super();
        this.maze = maze;

        connection = JDBCConnection.getInstance();


        try {
            Statement st = connection.createStatement();
            st.execute(CREATE_TABLE_1);

            /* BEGIN MISSING CODE */
            addMaze = connection.prepareStatement(INSERT_MAZES);
            tempDraw(maze);
            addMaze();
            /* END MISSING CODE */
        } catch (SQLException ex) {
            ex.printStackTrace();
        }


       // ExportGUI();
    }

    public Export(Maze maze, Boolean check){

        //OPTIMAL
        super();
        this.maze = maze;

        connection = JDBCConnection.getInstance();


        try {
            Statement st = connection.createStatement();
            st.execute(CREATE_TABLE_1);

            /* BEGIN MISSING CODE */
            addMaze = connection.prepareStatement(INSERT_MAZES);
            if(check){
                tempDrawOptimal(maze);
            }else{
                tempDraw(maze);
            }
            //tempDraw(maze);
            addMaze();

            /* END MISSING CODE */
        } catch (SQLException ex) {
            ex.printStackTrace();
        }


        //ExportGUI();
    }

    public void tempDraw(Maze maze){
        pnlDisplay = new JPanel();
        pnlDisplay.setLayout(null);
        pnlDisplay.setBounds(25, 25, displayLength, displayHeight);
        maze.Draw(pnlDisplay);

        try {
            System.out.println("IMAGE METHOD SHOULD BE CALLED");
            image(pnlDisplay, false);
        } catch (IOException e) {
            System.out.println("IMAGE METHOD NOT CALLED");
            e.printStackTrace();
        }


    }

    public void tempDrawOptimal(Maze maze){
        //tempDraw(maze);
        pnlDisplay = new JPanel();
        pnlDisplay.setLayout(null);
        pnlDisplay.setBounds(25, 25, displayLength, displayHeight);
        maze.Draw(pnlDisplay);

        try {
            System.out.println("IMAGE METHOD SHOULD BE CALLED");
            image(pnlDisplay, false);
        } catch (IOException e) {
            System.out.println("IMAGE METHOD NOT CALLED");
            e.printStackTrace();
        }

        pnlOptimal = new JPanel();
        pnlOptimal.setLayout(null);
        pnlOptimal.setBounds(25, 25, displayLength, displayHeight);
        maze.Draw(pnlOptimal, this.maze.getSolution());

        try {
            System.out.println("IMAGE METHOD SHOULD BE CALLED");
            image(pnlOptimal, true);
        } catch (IOException e) {
            System.out.println("IMAGE METHOD NOT CALLED");
            e.printStackTrace();
        }


    }

    public BufferedImage image(JPanel panel, boolean opt) throws IOException {
        int w = 500;
        int h = 500;

        BufferedImage temp = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
        Graphics2D g = temp.createGraphics();
        panel.paint(g);
        String title = "";
        if(opt){
            title = title.concat(this.maze.title+"_optimal");
        }else{
            title = this.maze.title;
        }
        ImageIO.write(temp,"png",new File(title+".png"));


        file = new File(title+".png");
        fis = new FileInputStream(file);



        g.dispose();

        return temp;
    }





    private void ExportGUI(){


        frame = new JFrame("Export GUI");
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

      //  JPanel bottomPanel = new JPanel(new GridBagLayout());
      //  bottomPanel.add(btnBack, BorderLayout.LINE_END);


        JPanel pane = new JPanel(new GridBagLayout());


        frame.setContentPane(pane);


        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 10;
        constraints.weighty = 10;

        constraints.gridx = 1;
        constraints.gridwidth = 2;
        constraints.gridy = 0;
        headerLabel.setFont(new Font("Test", Font.PLAIN, 50));
        pane.add(headerLabel, constraints);

        //BUTTONs
        constraints.anchor = GridBagConstraints.PAGE_END;
        constraints.gridwidth = 1;
        constraints.gridx = 3;
        constraints.gridy = 2;
        constraints.ipady = 40;
        btnBack.setFont(new Font("Test",Font.PLAIN,20));
        pane.add(btnBack, constraints);


        //BUTTON ACTION LISTENERS

        btnBack.addActionListener(this);

        //VIEW FRAME

        frame.setVisible(true);

    }





    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==btnBack){
            System.out.println("RETURN TO HOME SCREEN");
            HideGUI();
            MazeGenerator.GetInstance().ShowGUI();
        }
    }

    @Override
    public void run() {
        //ExportGUI();
    }

    private void HideGUI(){
        frame.dispose();
    }
    public void DisplayGUI(){
        frame.setVisible(true);
    }
}
