package level;

import javafx.scene.paint.Color;


/**
 * @author Roman
 * singleton class with tile codes (color for streaming from png and int value for internal use)
 */
public final class TileCode {
	
	 //tile codes 
    public static final int wall =  	255;
    public static final Color wallRGB = Color.BLACK;
    //corner tiles?
    
    public static final int ground =  	100;
    public static final Color groundRGB = Color.WHITE;
    
    public static final int door =  	150;
    public static final Color doorRGB = Color.DIMGREY;
    
    public static final int enemy = 	20;    
    public static final Color enemyRGB = Color.DARKRED;
    
    public static final int item =		10;
    public static final Color itemRGB = Color.YELLOW;
    
    public static final int playerspawn =		33;
    public static final Color playerspawnRGB = Color.GREEN;
    
    public static final int goal =		133;
    public static final Color goalRGB = Color.BLUE;    
   
	
	private TileCode () {  }	

}

