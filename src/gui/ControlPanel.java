package gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;

import world.TronWorld;

public class ControlPanel extends JPanel {
	TronWorld world;
	DisplayPanel display;
	Timer timer;
	
	int delay;
	
	public ControlPanel(DisplayPanel display, TronWorld world, int runSpeed){
		super(new FlowLayout(FlowLayout.LEADING, 4, 4));
		this.display = display;
		this.world = world;
		delay = runSpeed;
		
		createGUI();
	}
	
	public void createGUI(){
		JButton runButton, resetButton;
		
		//creates a timer that continuously calls the step() method of the world after a certain delay (runSpeed)
		runButton = new JButton(new AbstractAction("Run"){
			public void actionPerformed(ActionEvent e) {
				world.run(delay);
			}
		});
		
		//add em onto the control panel (this)
		//add(stepButton);
		add(runButton);
		//add(stopButton);
		
		//transparency
		setOpaque(false);
	}
}
