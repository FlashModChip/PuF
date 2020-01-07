package settings;

import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.shape.Box;

import java.util.HashMap;

/**
 * settings for the game
 * singleton
 * just getters ...
 */
public class Settings {

    private static Settings settings = new Settings();

    // title of the game
    private static final String TITLE = "I&o";
    // window dimensions
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = WINDOW_WIDTH / 4 * 3;
    // generic blocksize
    private static final int BLOCKSIZE = 50;
    // player movement-speed
    private static final int SPEED = 1;

    // player jump-velocity
   // private static final int JUMP = -15; Brauchen wir nicht ??


    // gravity strength
    private static final double GRAVITY = 0.5;
    // use textures on blocks
    private static final boolean USE_TEXTURES = true;
    // enable audio player
    private static final boolean PLAY_SOUNDS = true;

    // movement system
    // safe spot for self-collision
    private static Point2D SAFE_SPOT = new Point2D(0, 0);
    // fallback size for dummy box
    private static Box fallBackSize = new Box(5,5,5);
    // set the size of the smallest entity here
    // stepsize is used during collision detection
    private static int stepsize = BLOCKSIZE;

    private static final HashMap<String, Boolean> DEBUG = new HashMap<>();

    private Settings() {

    }

    public static Settings getInstance() {
        DEBUG.put("LevelLoader@entityCreation", false);
        DEBUG.put("SystemManager@init", false);
        DEBUG.put("SystemManager@update", false);
        DEBUG.put("MovementSystem@dummy", false);
        DEBUG.put("MovementSystem@stepsize", false);
        DEBUG.put("Light@color", false);
        DEBUG.put("AudioPlayer@notify", true);
        return settings;
    }

    public static String getTitle() {
        return TITLE;
    }

    public static int getWindowWidth() {
        return WINDOW_WIDTH;
    }

    public static int getWindowHeight() {
        return WINDOW_HEIGHT;
    }

    public static int getBlocksize() {
        return BLOCKSIZE;
    }

    public static Boolean getUseTextures() {
        return USE_TEXTURES;
    }

    public static Boolean getDebug(String key) {
        return DEBUG.get(key);
    }

    public static int getSpeed() {
        return SPEED;
    }

   // Brauchen wir nicht, springt nicht
  //  public static int getJump() {
  //      return JUMP;
  //  }

    public static double getGravity() {
        return GRAVITY;
    }

    public static boolean getPlaySounds() {
        return PLAY_SOUNDS;
    }

    public static Point2D getSafeSpot() {
        return SAFE_SPOT;
    }

    public static Box getFallBackSize() {
        return fallBackSize;
    }


    public static int getStepsize() {
        return stepsize;
    }

}
