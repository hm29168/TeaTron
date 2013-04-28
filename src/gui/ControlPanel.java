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
	private TronRunner runner;
	private DisplayPanel display;
	private Timer timer;
	
	private int delay;
	
	public ControlPanel(DisplayPanel display, TronRunner runner, int runSpeed){
		super(new FlowLayout(FlowLayout.LEADING, 4, 4));
		this.display = display;
		this.runner = runner;
		delay = runSpeed;
		
		createGUI();
	}
	
	public void createGUI(){
		JButton runButton, resetButton;
		
		//creates a timer that continuously calls the step() method of the world after a certain delay (runSpeed)
		runButton = new JButton(new AbstractAction("Run"){
			public void actionPerformed(ActionEvent e) {
				runner.getWorld().run(delay);
			}
		});
		
		resetButton = new JButton(new AbstractAction("Reset"){
			public void actionPerformed(ActionEvent e) {
				runner.reset();
			}
		});
		
		
		//add em onto the control panel (this)
		add(runButton);
		add(resetButton);
		
		//transparency
		setOpaque(false);
	}
}
