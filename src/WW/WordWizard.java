package WW;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;

public class WordWizard extends JFrame 
{

    public WordWizard()
    {
        add(new GamePanel());
        setTitle("Word Wizard");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(640, 480);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);

    }

    public static void main (String[] args)
    {
        new WordWizard();
    }
}

