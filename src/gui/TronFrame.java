package gui;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.JFrame;

import world.TronWorld;

public class TronFrame extends JFrame{
	TronWorld world;
	DisplayPanel display;
	ControlPanel control;
	
	int runSpeed;
	int cellSize;
	
	public TronFrame(TronWorld world, int cellSize, int runSpeed) {
		this.world = world;
		this.cellSize = cellSize;
		this.runSpeed = runSpeed;
		initComponents();
	}

	public void initComponents(){
		//Create and set up the window.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        
        //create the display (visual) and control (button) panels
        display = new DisplayPanel(world, cellSize);
        control = new ControlPanel(display, world, runSpeed);
        
        container.add(display, BorderLayout.CENTER);
        container.add(control, BorderLayout.PAGE_END);
        
		//pack it up
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
	}
	
	public void repaint(){
		display.repaint();
		super.repaint();
	}
}
