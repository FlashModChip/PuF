package level;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;

/// <summary>
/// Reading/saving level data from/to image files
/// </summary>
public class LevelPicUtil
{
    /// <summary>
    /// Extracts level data from pic, return 2-dimensional array
    /// </summary>
    /// <param name="dir">
    /// </param>
    /// <returns>
    /// </returns>
    public static byte[][] getLevelDatafromPNG(String path,int sizex,int sizey)
    {
//    	 Image img = new Image(path + "level"+level+"/level"+level+".png");
//         PixelReader pixelReader = img.getPixelReader();
//         
//         if((int)img.getWidth() != sizex || (int)img.getHeight() != sizey)
//         {
//        	 throw ne 
//         }
//         int imgWidth = (int) img.getWidth();
//         int imgHeight = (int) img.getHeight();
         
         
        return new byte[1][1];
    }

    /// <summary>
    /// Saving 2-dimensional Level data in png
    /// </summary>
    /// <param name="levelData">
    /// </param>
    /// <param name="dir">
    /// </param>
    public static void setLevelDataToPNG(byte[][] levelData, String dir)
    {
        //ToDo
    }
}
