package guimaze;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonGui extends JFrame implements ActionListener, Runnable {

    private JButton createButton(String str) {
        //Create a JButton object and store it in a local var
        JButton var = new JButton(str);
        //Set the button text to that passed in str

        //Add the frame as an actionListener
        var.addActionListener(this);

        //Return the JButton object
        return var;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void run() {

    }
}
