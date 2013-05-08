package gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ControlPanel extends JPanel {
	private TronRunner runner;
	
	private int delay;
	
	public ControlPanel(TronRunner runner, int runSpeed){
		super(new FlowLayout(FlowLayout.LEADING, 4, 4));
		this.runner = runner;
		delay = runSpeed;
		
		createGUI();
	}
	
	public void createGUI(){
		JButton runButton, resetButton, autoRunButton;
		
		autoRunButton = new JButton(new AbstractAction("AutoRun"){
			public void actionPerformed(ActionEvent e) {
				boolean hasBeenReset = true;
				
				while(!runner.getWorld().hasBeenWon()) {
					if(hasBeenReset && !runner.getWorld().isGameDone()) {
						runner.getWorld().run(1); //very small amount of delay
						hasBeenReset = false;
					} 
					
					if(runner.getWorld().isGameDone() && !hasBeenReset ) {
						runner.reset();
						hasBeenReset = true;
					}
				}
			}
		});
		
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
		add(autoRunButton);
		add(runButton);
		add(resetButton);
		
		//transparency
		setOpaque(false);
	}
}