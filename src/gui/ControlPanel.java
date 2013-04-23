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
	
	public ControlPanel(DisplayPanel display, TronWorld world){
		super(new FlowLayout(FlowLayout.LEADING, 4, 4));
		this.display = display;
		this.world = world;
		
		createGUI();
	}
	
	public void createGUI(){
		JButton stepButton, runButton, stopButton;
		
		stepButton = new JButton(new AbstractAction("Step"){
			public void actionPerformed(ActionEvent e) {
				world.step();
				display.repaint();
			}
		});
		
		runButton = new JButton(new AbstractAction("Run"){
			public void actionPerformed(ActionEvent e) {
				timer = new Timer();
				TimerTask runTask = new TimerTask(){
					public void run() {
						world.step();
						display.repaint();
					}		
				};
				timer.schedule(runTask, 0, 500);
			}
		});
		
		stopButton = new JButton(new AbstractAction("Stop"){
			public void actionPerformed(ActionEvent e) {
				timer.cancel();
			}
		});
		
		add(stepButton);
		add(runButton);
		add(stopButton);
		
		setOpaque(false);
	}
}
