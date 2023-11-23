package guimaze;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
/**
 * @author bradley.mcgrath
 * @version 2
 */

public class Display extends JFrame implements ActionListener, Runnable {

    //Logical Fields
    protected Maze maze;

    //GUI Fields
    JFrame frame;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 800;
    JPanel pnlDisplay;
    private final int displayLength = 500;
    private final int displayHeight = 500;

    JPanel pnlLabels;
    JLabel lblHeader = new JLabel("Display");
    JLabel lblAuthor;
    JLabel lblTitle;
    JLabel lblEditDate;
    JLabel lblCreateDate;
    JLabel lblLength;
    JLabel lblHeight;
    JLabel lblOptimal;
    JLabel lblDeadEnd;

    JPanel pnlButtons;
    JButton btnBack = new JButton("Back");
    JButton btnExport = new JButton("Export");
    JButton btnRoute = new JButton("Optimal Route");
    JButton btnEdit = new JButton("Edit");


    //JPanel pane;
    //GridBagConstraints constraints;

    Display(Maze maze){
        super();
        this.maze = maze;
        CreateGUI();
        //this.maze = maze;
    }

    private void CreateDisplay(){
        pnlDisplay = new JPanel();
        pnlDisplay.setLayout(null);
        pnlDisplay.setBounds(25, 25, displayLength, displayHeight);
        this.maze.Draw(pnlDisplay);
    }

    private void CreateButtons(){
        pnlButtons = new JPanel(new GridLayout(2, 2));
        pnlButtons.setBounds(25, 550, 500, 200);
        //pnlButtons.setBackground(Color.RED);

        pnlButtons.add(btnExport);
        pnlButtons.add(btnBack);
        pnlButtons.add(btnEdit);
        pnlButtons.add(btnRoute);
    }

    private void CreateLabels(){
        pnlLabels = new JPanel(new GridLayout(8, 1));
        pnlLabels.setBounds(550, 50, 225, 600);
        //pnlLabels.setBackground(Color.BLUE);
        Font title = new Font("Arial", Font.BOLD, 35);
        Font fields = new Font ("Arial", Font.PLAIN, 15);

        lblTitle = new JLabel("'" + this.maze.title + "'");
        lblTitle.setFont(title);
        pnlLabels.add(lblTitle);
        lblAuthor = new JLabel("Author: " + this.maze.author);
        pnlLabels.add(lblAuthor);
        lblLength = new JLabel("Length: " + this.maze.length);
        pnlLabels.add(lblLength);
        lblHeight = new JLabel("Height: " + this.maze.height);
        pnlLabels.add(lblHeight);
        lblCreateDate = new JLabel ("Created on " + this.maze.createDate);
        pnlLabels.add(lblCreateDate);
        lblEditDate = new JLabel("Last Editted on: " + this.maze.editDate);
        pnlLabels.add(lblEditDate);
        lblOptimal = new JLabel(this.maze.TotalCellOptimal() + " cell solve");
        pnlLabels.add(lblOptimal);
        lblDeadEnd = new JLabel(this.maze.Total_DeadEnd() + " dead end cells");
        pnlLabels.add(lblDeadEnd);

    }

    private void CreateGUI(){

        CreateDisplay();
        CreateButtons();
        CreateLabels();

        /*
        pane = new JPanel(new GridBagLayout());
        frame.setContentPane(pane);


        constraints = new GridBagConstraints();
        constraints.weightx = 10;
        constraints.weighty = 10;

        constraints.gridx = 0;
        constraints.gridwidth = 2;
        constraints.gridy = 0;
        headerLabel.setFont(new Font("Test", Font.PLAIN, 50));
        pane.add(headerLabel, constraints);

        constraints.gridx = 3;
        constraints.gridwidth = 2;
        constraints.gridy = 0;
        headerLabel.setFont(new Font("Test", Font.PLAIN, 20));
        pane.add(TitleLabel, constraints);

        constraints.gridx = 0;
        constraints.gridwidth = 2;
        constraints.gridy = 1;
        headerLabel.setFont(new Font("Test", Font.PLAIN, 20));
        pane.add(EditDateLabel, constraints);

        constraints.gridx = 0;
        constraints.gridwidth = 2;
        constraints.gridy = 2;
        headerLabel.setFont(new Font("Test", Font.PLAIN, 20));
        pane.add(DateLabel, constraints);

        constraints.gridx = 0;
        constraints.gridwidth = 2;
        constraints.gridy = 3;
        headerLabel.setFont(new Font("Test", Font.PLAIN, 20));
        pane.add(AuthorLabel, constraints);
        //BUTTONs
        //constraints.anchor = GridBagConstraints.PAGE_END;
        constraints.gridwidth = 1;
        constraints.gridx = 3;
        constraints.gridy = 6;
        constraints.ipady = 0;
        btnBack.setFont(new Font("Test",Font.PLAIN,20));
        pane.add(btnBack, constraints);

        constraints.gridx = 3;
        constraints.gridwidth = 1;
        constraints.gridy = 3;
        constraints.ipady = 0;
        pane.add(btnExport, constraints);

        constraints.gridx = 3;
        constraints.gridwidth = 1;
        constraints.gridy = 4;
        constraints.ipady = 0;
        pane.add(btnRoute, constraints);

        constraints.gridx = 3;
        constraints.gridwidth = 1;
        constraints.gridy = 5;
        constraints.ipady = 0;
        pane.add(btnEdit, constraints);

         */


        //BUTTON ACTION LISTENERS

        btnBack.addActionListener(this);
        btnRoute.addActionListener(this);
        btnEdit.addActionListener(this);
        btnExport.addActionListener(this);

        /*
        //IMAGE MAZE
        ImageIcon icon = new ImageIcon("noroute.png");
        constraints.gridx = 3;
        constraints.gridwidth = 1;
        constraints.gridy = 1;
        constraints.ipady = 0;
        pane.add(new JLabel(icon),constraints);

         */

        //VIEW FRAME

        frame = new JFrame("Maze Display");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setSize(WIDTH, HEIGHT);

        frame.add(pnlDisplay);
        frame.add(pnlButtons);
        frame.add(pnlLabels);
        frame.setVisible(true);

    }
/*
    private void OptimalRoute(){
        // Calculate best route on the maze and displays it

        //IMAGE MAZE
        ImageIcon icon = new ImageIcon("route.png");
        constraints.gridx = 3;
        constraints.gridwidth = 1;
        constraints.gridy = 2;
        constraints.ipady = 0;
        pane.add(new JLabel(icon),constraints);
        //System.out.println("Running");

        //VIEW FRAME

        frame.setVisible(true);

    }

 */
    private void EditMaze(){
        ManualGeneration edit = new ManualGeneration(this.maze);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==btnBack){
            System.out.println("RETURN TO HOME SCREEN");
            HideGUI();
            MazeGenerator.GetInstance().ShowGUI();
        }
        if(e.getSource() == btnRoute){
            //OptimalRoute();
        }
        if(e.getSource() == btnEdit){
            HideGUI();
            EditMaze();
        }
        if(e.getSource() == btnExport){
            HideGUI();
            Export temp = new Export(maze);
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
