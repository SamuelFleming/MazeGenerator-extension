package guimaze;

import javax.swing.*;

public class ManualGenDialog {

    //logical fields
    private int entry_points;

    //GUI fields

    private JDialog ui;
    private JTable table;

    ManualGenDialog(JFrame frame){
        entry_points = 2;
        DisplayGUI(frame);
    }

    private void DisplayGUI(JFrame frame){
        ui = new JDialog(frame, "Manual Wall Removal");

    }

    private Object[][] populateObject(int entryPoints){

        Object[][] obj = new Object[entryPoints][5];
        for (int i = 0; i < entryPoints; i++){
            obj[i][0] = i+1;
            obj[i][1] = "x: ";
            obj[i][2] = "";
            obj[i][0] = "y: ";
            obj[i][0] = "";
        }
        return obj;
    }

}
