package players.architecture;

import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.awt.Color;

import world.TronGrid;

/**
 * Our version of Grid World's Actor class
 * Most notably, it removes location controls and gives it to our TronGrid
 * 
 * NOTE THAT YOU STILL NEED TO STUDY THE NORMAL GRIDWORLD API INSTEAD OF OUR HACKED ONE
 *
 */
public abstract class CustomActor
{
    private TronGrid<CustomActor> grid;
    private int direction;
    private Color color;

    /**
     * Constructs a blue actor that is facing north.
     */
    public CustomActor(TronGrid<CustomActor> gr)
    {
        color = Color.BLUE;
        direction = Location.NORTH;
        grid = gr;
    }

    /**
     * Gets the color of this actor.
     * @return the color of this actor
     */
    public Color getColor()
    {
        return color;
    }

    /**
     * Sets the color of this actor.
     * @param newColor the new color
     */
    public final void setColor(Color newColor)
    {
        color = newColor;
    }

    /**
     * Gets the current direction of this actor.
     * @return the direction of this actor, an angle between 0 and 359 degrees
     */
    public final int getDirection()
    {
        return direction;
    }

    /**
     * Sets the current direction of this actor.
     * @param newDirection the new direction. The direction of this actor is set
     * to the angle between 0 and 359 degrees that is equivalent to
     * <code>newDirection</code>.
     */
    public final void setDirection(int newDirection)
    {
        direction = newDirection % Location.FULL_CIRCLE;
        if (direction < 0)
            direction += Location.FULL_CIRCLE;
    }

    /**
     * Gets the grid in which this actor is located.
     * @return the grid of this actor, or <code>null</code> if this actor is
     * not contained in a grid
     */
    public final Grid<CustomActor> getGrid()
    {
        return grid;
    }

    /**
     * Gets the location of this actor.
     * @return the location of this actor, or <code>null</code> if this actor is
     * not contained in a grid
     */
    public final Location getLocation()
    {
        return grid.getLocation(this);
    }
    
    protected final void removeSelfFromGrid(){
    	grid.remove(getLocation());
    	this.grid = null;
    }

    /**
     * Creates a string that describes this actor.
     * @return a string with the location, direction, and color of this actor
     */
    public String toString()
    {
        return getClass().getName() + "[location=" + getLocation() + ",direction="
                + direction + ",color=" + color + "]";
    }
}