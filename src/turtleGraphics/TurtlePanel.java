package turtleGraphics;

import tools.Utilities;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.*;

public class TurtlePanel extends JPanel implements ActionListener {

    private Turtle model;
    private TurtleView view;
    private JFrame frame;
    public static int FRAME_WIDTH = 2*Turtle.WORLD_SIZE;
    public static int FRAME_HEIGHT = Turtle.WORLD_SIZE+50; // add 50 for header and menu bar

    public TurtlePanel() {
        model = new Turtle();
        JPanel controlPanel = new JPanel();
        view = new TurtleView(model);

        setLayout((new GridLayout(1, 2)));
        add(controlPanel);
        add(view);

        controlPanel.setBackground(Color.LIGHT_GRAY);

        JButton northButton = new JButton("North");
        northButton.addActionListener(this);
        controlPanel.add(northButton);

        JButton eastButton = new JButton("East");
        eastButton.addActionListener(this);
        controlPanel.add(eastButton);

        JButton southButton = new JButton("South");
        southButton.addActionListener(this);
        controlPanel.add(southButton);

        JButton westButton = new JButton("West");
        westButton.addActionListener(this);
        controlPanel.add(westButton);

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(this);
        controlPanel.add(clearButton);

        JButton penButton = new JButton("Pen");
        penButton.addActionListener(this);
        controlPanel.add(penButton);

        JButton colorButton = new JButton("Color");
        colorButton.addActionListener(this);
        controlPanel.add(colorButton);

        frame = new JFrame();
        Container cp = frame.getContentPane();
        cp.add(this);
        frame.setJMenuBar(createMenuBar());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Turtle Graphics");
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
    }

    protected JMenuBar createMenuBar() {
        JMenuBar result = new JMenuBar();
        // add file, edit, and help menus
        JMenu fileMenu =
                Utilities.makeMenu("File", new String[] {"New",  "Save", "Open", "Quit"}, this);
        result.add(fileMenu);

        JMenu editMenu =
                Utilities.makeMenu("Edit", new String[] {"North", "East", "South", "West",
                                                                "Clear", "Pen", "Color"}, this);
        result.add(editMenu);

        JMenu helpMenu =
                Utilities.makeMenu("Help", new String[] {"About", "Help"}, this);
        result.add(helpMenu);

        return result;
    }

    public void display() { frame.setVisible(true); }

    public void actionPerformed(ActionEvent ae) {
        String cmmd = ae.getActionCommand();
        if (cmmd == "Save") {
            try {
                //String fName = Utilities.ask("File Name?");
                String fName = Utilities.getFileName(null, false);
                ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fName));
                os.writeObject(model);
                os.close();
            } catch (Exception err) {
                Utilities.error(err);
            }
        } else if (cmmd == "Open") {
            try {
                if(!Utilities.confirm("WARNING: You may have some unsaved changes. " +
                        "Are you sure you want to proceed?")) {
                    String fName = Utilities.getFileName(null, true);
                    ObjectInputStream is = new ObjectInputStream(new FileInputStream(fName));
                    //model.removePropertyChangeListener(this);
                    model = (Turtle) is.readObject();
                    //this.model.initSupport();
                    //model.addPropertyChangeListener(this);
                    view.setModel(model);
                    is.close();
                }
            } catch (Exception err) {
                Utilities.error(err.getMessage());
            }
        } else if (cmmd == "New") {
            model = new Turtle();
            view.setModel(model);
        } else if (cmmd == "Quit") {
            //Utilities.saveChanges(model);
            if(!Utilities.confirm("WARNING: You may have some unsaved changes. Are you sure you want to quit?"))
                System.exit(1);
        } else if (cmmd == "About") {
            Utilities.inform("Cyberdellic Designs Turtle Graphics, 2021. All rights reserved.");
        } else if (cmmd == "Help") {
            Utilities.inform(new String[] {"Click or select from the following edit options to move the turtle.",
                    "",
                    "North: Move the turtle a distance to the north.",
                    "East: Move the turtle a distance to the east.",
                    "South: Move the turtle a distance to the south.",
                    "West: Move the turtle a distance to the west.",
                    "Clear: Erase the drawing.",
                    "Pen: Toggle the pen up and down. Up/white-don't draw. Down/black-draw.",
                    "Color: Set the color of the turtle's path."});
        } else if (cmmd == "North") {
            int d = askDistance();
            model.move(d,"NORTH");
        } else if (cmmd == "East") {
            int d = askDistance();
            model.move(d,"EAST");
        } else if (cmmd == "South") {
            int d = askDistance();
            model.move(d,"SOUTH");
        } else if (cmmd == "West") {
            int d = askDistance();
            model.move(d,"WEST");
        } else if (cmmd == "Clear") {
            model.clearPath();
        } else if (cmmd == "Pen") {
            model.setPenUp(!model.isPenUp());
        } else if (cmmd == "Color") {
            Color c = JColorChooser.showDialog(null, "Choose a color", model.getColor());
            model.changeColor(c);
        } else  {
            Utilities.error("Unrecognized command: " + cmmd);
        }
    }

    public int askDistance() {
        String distance = Utilities.ask("How far should the turtle move?");
        int d = Integer.parseInt(distance);
        return d;
    }

    public static void main(String[] args) {
        TurtlePanel app = new TurtlePanel();
        app.display();
    }
}
