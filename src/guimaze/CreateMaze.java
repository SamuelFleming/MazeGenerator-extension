package guimaze;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.Date;


public class CreateMaze implements ActionListener, Runnable{

    /**
     * @author sam.fleming
     * @version 5
     *
     */

    //Logical Fields
    protected Maze maze;
    private final int WIDTH = 800;
    private final int HEIGHT = 800;
    private boolean auto = true;

    private String title;
    private String author;
    private int maze_length;
    private int maze_height;
    private String date;
    private int xs;
    private int ys;
    private int xe;
    private int ye;



    //GUI Fields
    private JFrame frame = new JFrame();
    private final Font labels = new Font("Arial", Font.PLAIN, 20);

    private JPanel pnlFields;
    private JLabel mazeLength = new JLabel("x:");
    private JTextField length = new JTextField("100<x<0");
    private JLabel mazeHeight = new JLabel("y:");
    private JTextField height = new JTextField("100<y<0");
    private JLabel authorName = new JLabel("Author:");
    private JTextField Author = new JTextField("");
    private JLabel mazeTitle = new JLabel("Title:");
    private JTextField Title = new JTextField("");
    private JButton btnAutomatic = new JButton("Generate Automatically");
    private JButton btnManual = new JButton("Generate Manually");

    private JPanel pnlEnds;
    private JButton btnSubmit;
    private JButton btnDefault;
    private JLabel lblStart;
    private JTextField txt_xs;
    private JTextField txt_ys;
    private JLabel lblEnd;
    private JTextField txt_xe;
    private JTextField txt_ye;


    private JLabel CreateSign = new JLabel("Create Maze");
    private JLabel dimensions = new JLabel("Dimensions");








    CreateMaze(){

/*
        CreateMazeGUI(); //eventually DisplayGUI (from interface)
        btnAutomatic.addActionListener(this);
        btnManual.addActionListener(this);

 */
        CreateGUI();



    }

    private void CreateFields(){


        mazeTitle = new JLabel("Title: ");
        mazeTitle.setBounds(0,0, 100, 75);
        mazeTitle.setFont(labels);
        pnlFields.add(mazeTitle);
        Title = new JTextField();
        Title.setBounds(150, 0, 550, 75);
        pnlFields.add(Title);

        authorName = new JLabel("Author: ");
        authorName.setBounds(0, 100, 100, 75);
        authorName.setFont(labels);
        pnlFields.add(authorName);
        Author = new JTextField();
        Author.setBounds(150, 100, 550, 75);
        pnlFields.add(Author);

        mazeLength = new JLabel("Maze Length: ");
        mazeLength.setBounds(0, 200, 100, 75);
        pnlFields.add(mazeLength);
        length = new JTextField();
        length.setBounds(100, 215, 75, 45);
        pnlFields.add(length);

        mazeHeight = new JLabel("Maze Height: ");
        mazeHeight.setBounds(375, 200, 100, 75);
        pnlFields.add(mazeHeight);
        height = new JTextField();
        height.setBounds(475, 215, 75, 45);
        pnlFields.add(height);

        btnAutomatic = new JButton("Generate Automatically");
        btnAutomatic.setBounds(50, 300, 275, 125);
        pnlFields.add(btnAutomatic);
        btnAutomatic.addActionListener(this);
        btnManual = new JButton("Generate Manually");
        btnManual.setBounds(425, 300, 275, 125);
        pnlFields.add(btnManual);
        btnManual.addActionListener(this);

    }

    private void CreateEnds(){

        frame.setVisible(false);
        //pnlFields.removeAll();
        //CreateFields();
        DisableFields();

        lblStart = new JLabel("Start Cell (x, y): ");
        lblStart.setBounds(0, 0, 150, 75);
        lblStart.setFont(labels);
        pnlEnds.add(lblStart);
        txt_xs = new JTextField();
        txt_xs.setBounds(200, 15, 75, 45);
        pnlEnds.add(txt_xs);
        txt_ys = new JTextField();
        txt_ys.setBounds(300, 15, 75, 45);
        pnlEnds.add(txt_ys);

        lblEnd = new JLabel("End Cell (x, y): ");
        lblEnd.setBounds(0, 100, 150, 75);
        lblEnd.setFont(labels);
        pnlEnds.add(lblEnd);
        txt_xe = new JTextField();
        txt_xe.setBounds(200, 115, 75, 45);
        pnlEnds.add(txt_xe);
        txt_ye = new JTextField();
        txt_ye.setBounds(300, 115, 75, 45);
        pnlEnds.add(txt_ye);

        btnDefault = new JButton("Submit Default");
        btnDefault.setBounds(450, 15, 200, 45);
        pnlEnds.add(btnDefault);
        btnSubmit = new JButton("Submit Start/End");
        btnSubmit.setBounds(450, 115, 200, 45);
        pnlEnds.add(btnSubmit);
        btnDefault.addActionListener(this);
        btnSubmit.addActionListener(this);

        frame.add(pnlEnds);
        frame.setVisible(true);

    }

    private void DisableFields(){
        btnAutomatic.setEnabled(false);
        btnManual.setEnabled(false);
        length.setEditable(false);
        height.setEditable(false);
        Author.setEditable(false);
        Title.setEditable(false);
    }

    private void CreateGUI(){
        frame = new JFrame();
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);

        pnlFields = new JPanel();
        pnlFields.setLayout(null);
        pnlFields.setBounds(25, 25, 750, 450);//750  x 450 pixels
        CreateFields();

        pnlEnds = new JPanel();
        pnlEnds.setLayout(null);
        pnlEnds.setBounds(25, 525, 750, 200); //750 x 200 pixels
        //CreateEnds();

        frame.add(pnlFields);
        //frame.add(pnlEnds);
        frame.setVisible(true);

        System.out.println("Date is: " + GetDate());

    }


    private void CreateMazeGUI(){

        frame.setSize(800, 800);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel pnlInputs = new JPanel(new GridBagLayout());
        frame.setContentPane(pnlInputs);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 1;
        constraints.weighty = 1;

/*
        //CreateMaze Sign
        constraints.gridx = 1;
        constraints.gridwidth = 5;
        constraints.gridy = 1;
        constraints.gridheight = 3;
        pnlInputs.add(CreateSign, constraints);

 */

        //"Dimensions" Text Label
        constraints.gridx = 1;
        constraints.gridwidth = 1;
        constraints.gridy = 5;
        pnlInputs.add(dimensions, constraints);
        //x dimension Label
        constraints.gridx = 2;
        constraints.gridwidth = 1;
        constraints.gridy = 5;
        pnlInputs.add(mazeLength, constraints);
        //x dimension Input
        constraints.gridx = 3;
        constraints.gridwidth = 1;
        constraints.gridy = 5;
        pnlInputs.add(length, constraints);
        //y dimension Label
        constraints.gridx = 4;
        constraints.gridwidth = 1;
        constraints.gridy = 5;
        pnlInputs.add(mazeHeight, constraints);
        //y dimension Input
        constraints.gridx = 5;
        constraints.gridwidth = 1;
        constraints.gridy = 5;
        pnlInputs.add(height, constraints);

        //MazeTitle Text Label
        constraints.gridx = 1;
        constraints.gridwidth = 1;
        constraints.gridy = 6;
        pnlInputs.add(mazeTitle, constraints);
        //MazeTitle text input
        constraints.gridx = 2;
        constraints.gridwidth = 1;
        constraints.gridy = 6;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        pnlInputs.add(Title, constraints);
        //Author Text Label
        constraints.gridx = 1;
        constraints.gridwidth = 1;
        constraints.gridy = 7;
        pnlInputs.add(authorName, constraints);
        //Author name input
        constraints.gridx = 2;
        constraints.gridwidth = 1;
        constraints.gridy = 7;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        pnlInputs.add(Author, constraints);



        //Generate manually button
        constraints.gridx = 2;
        constraints.gridwidth = 1;
        constraints.gridy = 8;
        constraints.gridheight = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        pnlInputs.add(btnAutomatic, constraints);
        //Generate automatic button
        constraints.gridx = 4;
        constraints.gridwidth = 1;
        constraints.gridy = 8;
        constraints.gridheight = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        pnlInputs.add(btnManual, constraints);

        //repaint();
        frame.setVisible(true);


    }




    private void inputHandler() throws NumberFormatException{
        /**
         * Handles incorrect user input for Title, Author and Size
         */
        //Catch exceptions related to incorrect input - display appropriate dialog
        title = Title.getText();
        author = Author.getText();
        maze_length = Integer.parseInt(length.getText());
        System.out.println("input length : " + maze_length);
        maze_height = Integer.parseInt(height.getText());
        System.out.println("input height: " + maze_height);

        date = GetDate();
        //HideGUI();
        boolean length_error = maze_length < 3 || maze_length > 100;
        boolean height_error = maze_height < 3 || maze_height > 100;
        if(length_error || height_error){
            errorDialog();
        }else{
            if (auto){
                //CreateAutomatic(title,date,author, x, y);
                CreateEnds();
            }else{
                //CreateManual(title,date,author, x, y);
                CreateEnds();
            }
        }


    }

    private void Cell_inputHandler() throws Exception{
        /**
         * Handles incorrect user input for Start and end Cell
         *
         */
        xs = Integer.parseInt(txt_xs.getText());
        ys = Integer.parseInt(txt_ys.getText());
        xe = Integer.parseInt(txt_xe.getText());
        ye = Integer.parseInt(txt_ye.getText());

        int[] startCell = {xs, ys};
        int[] endCell = {xe, ye};

        boolean start_domain = (0<=xs && xs<=(maze_length-1))&&(0<=ys && ys<=(maze_height-1));
        boolean end_domain = (0<=xe && xe<=(maze_length-1))&&(0<=ye && ye<=(maze_height-1));
        if(start_domain && end_domain){
            if (auto){
                CreateAutomatic(title,date,author, maze_length, maze_height, startCell, endCell);
            }else{
                CreateManual(title,date,author, maze_length, maze_height, startCell, endCell);
            }
        }else{
            errorDialog(); //make more general
        }

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

    private void InputExceptionHandler(){
        /**
         * Handles excpetions in inputHandler()
         */
        try{
          inputHandler();
        } catch(NumberFormatException c){
            System.out.println(c.getMessage());
            errorDialog(); // "errorDialog" unfinished
        }
    }

    private void CellExceptionHandler(){
        /**
         * Handles excpetions in Cell_inputHandler()
         */
        try{
            Cell_inputHandler();
        } catch(Exception c){
            System.out.println(c.getMessage());
            errorDialog(); // "errorDialog" unfinished
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /*
        String title;
        String author;
        int x;
        int y;

         */
        if(e.getSource()==btnAutomatic) {
            System.out.println("btn pressed, automatically generate maze");
            auto = true;
            InputExceptionHandler();
        }else if(e.getSource() == btnManual){
            System.out.println("btn pressed, manually generate maze");
            auto = false;
            InputExceptionHandler();
        }else if (e.getSource() == btnSubmit){
            CellExceptionHandler();
        }else if (e.getSource() == btnDefault){
            System.out.println("Default Pressed");
            if (auto){
                CreateAutomatic(title,date,author, maze_length, maze_height);
            }else{
                CreateManual(title,date,author, maze_length, maze_height);
            }
        }

    }

    private void errorDialog(){
        JDialog d = new JDialog(frame, "Input Error");
        d.setSize(400, 200);
        JPanel p = new JPanel();
        //p.setLayout(null);
        p.setBorder(BorderFactory.createLineBorder(Color.black));
        String errorMsg = "Uh oh, incorrect data input. Make sure your are entering integers for the dimensions " +
                "(MazeLength and MazeHeight) and cell coordinates (Start Cell and End Cell). " +
                "Ensure the maze is between a 3x3 and 100x100 in size and that the start and end cells are" +
                " within these boundaries. (Cell co-ordinates start at 0)";
        JTextArea txtaInputError = new JTextArea(errorMsg);
        txtaInputError.setSize(300, 100);
        //txtaInputError.setBounds(50, 50, 300, 100);
        txtaInputError.setLineWrap(true);
        txtaInputError.setWrapStyleWord(true);

        p.add(txtaInputError);
        d.add(p);
        d.setVisible(true);
    }

    @Override
    public void run() {

    }



    private void CreateAutomatic(String title,String date ,String author, int x, int y, int[] start, int[] end){
        /**
         * calls constructor of specified start/end AutoGenerated maze
         */
        HideGUI();
        maze = new Maze(title,date,author, x, y, start, end);
        MazeGenerator.GetInstance().NewMaze(this.maze);
        AutomaticGeneration createAuto = new AutomaticGeneration(maze);

    }

    private void CreateAutomatic(String title,String date ,String author, int x, int y){
        /**
         * calls constructor of unspecified start/end AutoGenerated maze
         */
        HideGUI();
        maze = new Maze(title,date,author, x, y);
        MazeGenerator.GetInstance().NewMaze(this.maze);
        AutomaticGeneration createAuto = new AutomaticGeneration(maze);

    }

    private void CreateManual(String title,String date, String author, int x, int y, int[] start, int[] end){
        /**
         * calls constructor of specified start/end ManualGenerated maze
         */
        HideGUI();
        maze = new Maze(title,date,author, x, y, start, end);
        MazeGenerator.GetInstance().NewMaze(this.maze);
        ManualGeneration createManual = new ManualGeneration(maze);

    }

    private void CreateManual(String title,String date, String author, int x, int y){
        /**
         * calls constructor of unspecified start/end ManualGenerated maze
         */
        HideGUI();
        maze = new Maze(title,date,author, x, y);
        MazeGenerator.GetInstance().NewMaze(this.maze);
        ManualGeneration createManual = new ManualGeneration(maze);

    }

    protected void HideGUI(){
        frame.dispose();
    }
    public void DisplayGUI(){
        frame.setVisible(true);
    }

}
