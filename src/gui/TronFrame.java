package gui;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.JFrame;

import world.TronWorld;

public class TronFrame extends JFrame{
	private TronRunner runner;
	private DisplayPanel display;
	private ControlPanel control;
	
	private int runSpeed;
	private int cellSize;
	
	public TronFrame(TronRunner runner, int cellSize, int runSpeed) {
		this.runner = runner;
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
        display = new DisplayPanel(runner.getWorld(), cellSize);
        control = new ControlPanel(display, runner, runSpeed);
        
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
	
	public void setWorld(TronWorld world){
		if (display != null){
			display.setWorld(world);
		}
	}
}
