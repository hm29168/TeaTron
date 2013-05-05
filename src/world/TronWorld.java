package world;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import players.*;
import players.architecture.Bike;
import players.architecture.CustomActor;
import players.architecture.Trail;

import gui.TronFrame;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import info.gridworld.gui.WorldFrame;
import info.gridworld.world.World;

public class TronWorld extends World<CustomActor>{
	
	private int curStep;
	private int numCrashes;
	private TronFrame frame;
	private Image trailImage;
	private Timer timer;
	
	//The setMessage function is pretty cool to use in World.java
	//Also, if we really have time, we could implement user control with the keyPressed function to play against our AI :D
	
	public TronWorld(int width, int height){
		super(new TronGrid<CustomActor>(width, height));
		curStep = 0;
		numCrashes = 0;
		trailImage = null;
	    try {
			trailImage = ImageIO.read(new File(getClass().getResource("/players/architecture/Trail.gif").toURI()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    //Need this function so don't have to cast everytime
    public TronGrid<CustomActor> getGrid(){
    	return (TronGrid<CustomActor>) super.getGrid();
    }	
    
    //simulates the game until last man standing
    public void run(int runSpeed){
    	//stop the current task
    	if (timer != null){timer.cancel();}
    	
    	//make a new timer and task for it to run
    	timer = new Timer();
		TimerTask runTask = new TimerTask(){
			public void run() {
				if (bikesLeft() <= 1){
					timer.cancel();
					endGame();
				}
				else{
					step();
					if(frame != null){
						frame.repaint();
					}
				}
				
			}		
		};
		timer.schedule(runTask, 0, runSpeed);
    }
    
    public void endGame(){
    	System.out.println("Game end.");
    	/*try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.exit(0);*/
    }
    
    public int bikesLeft(){
    	int numBikes = 0;
		
		Grid<CustomActor> gr = getGrid();
		for(Location loc : gr.getOccupiedLocations()){
			CustomActor a = gr.get(loc);
			if (a instanceof Bike){
				numBikes ++;
			}
		}
		
		return numBikes;
    }

    public void step() {
    	//System.out.println("Step[" + curStep + "]");
        Grid<CustomActor> gr = getGrid();
        LinkedList<CustomActor> actors = new LinkedList<CustomActor>();
        LinkedList<Bike> bikes = new LinkedList<Bike>();
        HashMap<Bike, Location> proposedLocations = new HashMap<Bike, Location>();
        
        for (Location loc : gr.getOccupiedLocations())
            actors.add(gr.get(loc)); //Obtain all of the actors in our grid
        
        for (CustomActor a : actors) {
        	if (a instanceof Bike){
        		bikes.add((Bike) a);
        		proposedLocations.put((Bike) a, proposedMove((Bike) a));
        	}
        	if(a instanceof Trail) {
        		((Trail) a).act();
        	}
        }
        curStep ++;
        
        resolveConflicts(actors, bikes, proposedLocations);
    }
    
    public void resolveConflicts(LinkedList<CustomActor> actors, 
    							 LinkedList<Bike> bikes, 
    							 HashMap<Bike, Location> proposedLocations) {
    	
    	LinkedList<Bike> crashedBikes = new LinkedList<Bike>();
    	TronGrid<CustomActor> grid = getGrid();
    	
    	for(Bike b : bikes) {
    		Location location = grid.getLocation(b);
    		Location newLocation = proposedLocations.get(b);
    		
    		if(!grid.isValid(newLocation)) { //Going out of the grid (Want to add trail if do this???)
                crashedBikes.add(b);
                continue;
    		}
    		
    		if(newLocation.equals(location)) { //Staying in the same place or error occurred in move function
    			crashedBikes.add(b);
    			continue;
    		}
    		
    		//Check if there are any conflicts in the proposedLocations
    		proposedLocations.remove(b);
    		if(proposedLocations.containsValue(newLocation)){
    			crashedBikes.add(b); 
    			proposedLocations.put(b, newLocation); //gotta have this here 'cause the continue statement stop errthing
    			continue;
    		}
    		proposedLocations.put(b, newLocation);
    		
    		//This is a pretty inefficient function
    		for(CustomActor a : actors) {
    			//If you run into a current actor (including other bikes b/c that's where their trail will be)
    			if(proposedLocations.get(b).equals(a.getLocation())) {
    				crashedBikes.add(b);
    				break;
    			}
    		}
    	}
    	
    	for(Bike b: bikes) {
    		if(crashedBikes.contains(b)) crashBike(b);
    		else moveBike(b, proposedLocations.get(b));
    	}
    	
    	if (crashedBikes.size() > 0){
    		numCrashes += crashedBikes.size();
	    	System.out.println(crashedBikes + " has/have crashed for [" + ((4 - numCrashes) + 1) + "]th place.");
    	}
    }
    
    private Location proposedMove(Bike b){
    	TronGrid<CustomActor> grid = getGrid();
    	Location location = grid.getLocation(b);
    	Location newLocation = location;
    	
    	try {
    		int newDirection = b.move();
    		
    		//fix the direction and make sure its a right angle
    		while(newDirection < 0){newDirection += 360;}
        	if (newDirection % 90 == 0){
	        	b.setDirection(newDirection);
	        	newLocation = location.getAdjacentLocation(newDirection);
        	}
    	} catch (Exception e) {
    	}
    	
    	return newLocation;
    }
    
    public void moveBike(Bike b, Location newLocation) { //This function is only called when we know for certain there is no conflict
    	TronGrid<CustomActor> grid = getGrid();
    	Location location = grid.getLocation(b);
    	
    	//use grid.remove(location) only when you are moving, not when actually pulling the Actor from the Grid
    	remove(location);
        put(newLocation, b);
        put(location, new Trail(grid, trailImage, b.getColor()));
    }
    
    public void crashBike(Bike b){
    	Location location = b.getLocation();
    	remove(location);
    	put(location, new Trail(getGrid(), trailImage, b.getColor()));
    	//System.out.println(b + " has crashed at step [" + curStep + "]");
    }

    /**
     * Adds an actor to this world at a given location.
     * @param loc the location at which to add the actor
     * @param occupant the actor to add
     */
    public void add(Location loc, CustomActor occupant)
    {
        TronGrid<CustomActor> grid = getGrid();
        grid.remove(loc);
        grid.put(loc, occupant);
    }

    /**
     * Adds an occupant at a random empty location.
     * @param occupant the occupant to add
     */
    public void add(CustomActor occupant)
    {
        Location loc = getRandomEmptyLocation();
        if (loc != null)
            add(loc, occupant);
    }
    
    public void put(Location location, CustomActor occupant){
    	if (occupant != null){
	    	getGrid().put(location, occupant);
    	}
    }

    /**
     * Removes an actor from this world.
     * @param loc the location from which to remove an actor
     * @return the removed actor, or null if there was no actor at the given
     * location.
     */
    public CustomActor remove(Location loc)
    {
        CustomActor occupant = getGrid().get(loc);
        if (occupant == null)
            return null;
        getGrid().remove(loc);
        return occupant;
    }
    
    public void setFrame(TronFrame frame){
    	this.frame = frame; //now that I know what the frame is
    	frame.setWorld(this); //update the frame on who I am
    }
}
