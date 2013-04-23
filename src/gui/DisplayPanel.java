package gui;

import info.gridworld.grid.Location;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.FilteredImageSource;
import java.awt.image.RGBImageFilter;
import java.util.ArrayList;

import javax.swing.JPanel;

import players.architecture.CustomActor;
import world.TronWorld;

public class DisplayPanel extends JPanel{
	TronWorld world;
	Dimension size;
	int cellSize;
	int imageSize = 48;
	double imageScale;
	
	public DisplayPanel(TronWorld world, int cellSize){
		super();
		this.world = world;
		
		int rows = world.getGrid().getNumRows();
		int cols = world.getGrid().getNumCols();
		this.cellSize = cellSize;
		imageScale = (double)(this.cellSize) / imageSize;
		
		size = new Dimension((cols * (this.cellSize + 1)) + 1, (rows * (this.cellSize + 1)) + 1);
		setPreferredSize(size);
	}
	
	public void paint(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		AffineTransform saveXForm = g2.getTransform();
		//g2.scale(imageScale, imageScale);
		drawGrid(g2);
		
		//draw each actor
		ArrayList<Location> occupiedLocations = world.getGrid().getOccupiedLocations();
		for(Location loc : occupiedLocations){
			CustomActor a = world.getGrid().get(loc);
			drawActor(g2, a);
		}
		
		g2.setTransform(saveXForm);
	}
	
	public void drawActor(Graphics2D g2, CustomActor a){
		Location loc = world.getGrid().getLocation(a);
		Image image = a.getImage();
		if (image != null){
			//translate to center, rotate, then translate back to top-left of the image
			AffineTransform at = new AffineTransform();
			int xoff = loc.getCol() * (cellSize + 1) + (cellSize / 2);
			int yoff = loc.getRow() * (cellSize + 1) + (cellSize / 2);
			at.translate(xoff, yoff);
			at.rotate(a.getDirection() * (Math.PI / 180));
			at.translate(-(cellSize / 2), -(cellSize / 2));
			
			//apply the color
			FilteredImageSource src = new FilteredImageSource(image.getSource(), new TintFilter(a.getColor()));
            Image tinted = createImage(src);
                
            //draw our image
			g2.drawImage(tinted, at, null);
		}
	}
	
	public void drawGrid(Graphics g2){
		int rows = world.getGrid().getNumRows();
		int cols = world.getGrid().getNumCols();
		int w = (int)(size.getWidth());
		int h = (int)(size.getHeight());
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, w, h);
		
		g2.setColor(Color.BLACK);
		for(int row = 0; row < rows; row ++){
			g2.drawLine(0, row * (cellSize + 1), w, row * (cellSize + 1));
		}
		
		for(int col = 0; col < cols; col ++){
			g2.drawLine(col * (cellSize + 1), 0, col * (cellSize + 1), h);
		}
	}
	
	private static class TintFilter extends RGBImageFilter
    {
        private int tintR, tintG, tintB;

        /**
         * Constructs an image filter for tinting colors in an image.
         * @param color the tint color
         */
        public TintFilter(Color color)
        {
            canFilterIndexColorModel = true;
            int rgb = color.getRGB();
            tintR = (rgb >> 16) & 0xff;
            tintG = (rgb >> 8) & 0xff;
            tintB = rgb & 0xff;
        }

        public int filterRGB(int x, int y, int argb)
        {
            // Separate pixel into its RGB coomponents.
            int alpha = (argb >> 24) & 0xff;
            int red = (argb >> 16) & 0xff;
            int green = (argb >> 8) & 0xff;
            int blue = argb & 0xff;
            // Use NTSC/PAL algorithm to convert RGB to gray level
            double lum = (0.2989 * red + 0.5866 * green + 0.1144 * blue) / 255;

            // interpolate between tint and pixel color. Pixels with
            // gray level 0.5 are colored with the tint color,
            // white and black pixels stay unchanged.
            // We use a quadratic interpolation function
            // f(x) = 1 - 4 * (x - 0.5)^2 that has
            // the property f(0) = f(1) = 0, f(0.5) = 1
            
            // Note: Julie's algorithm used a linear interpolation
            // function f(x) = min(2 - 2 * x, 2 * x);
            // and it interpolated between tint and 
            // (lum < 0.5 ? black : white)

            double scale = 1 - (4 * ((lum - 0.5) * (lum - 0.5)));
            
            red = (int) (tintR * scale + red * (1 - scale));
            green = (int) (tintG * scale + green * (1 - scale));
            blue = (int) (tintB * scale + blue * (1 - scale));
            return (alpha << 24) | (red << 16) | (green << 8) | blue;
        }
    }
}
