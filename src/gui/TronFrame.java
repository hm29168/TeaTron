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
	
	public TronFrame(TronWorld world, int cellSize) {
		this.world = world;
		initComponents(cellSize);
	}

	public void initComponents(int cellSize){
		//Create and set up the window.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        
        display = new DisplayPanel(world, cellSize);
        control = new ControlPanel(display, world);
        
        container.add(display, BorderLayout.CENTER);
        container.add(control, BorderLayout.PAGE_END);
        
		//Display the window.
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
	}
	
	public void repaint(){
		display.repaint();
		super.repaint();
	}
}
