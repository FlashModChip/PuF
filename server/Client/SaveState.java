package Client;

import java.util.ArrayList;

import entities.*;
import level.*;

/**
 * Save state for transfer to server
 * @author Roman
 *
 */
public class SaveState {
	
    public LevelData level;
    public ArrayList<Entity> enemies;
    public Player player;

    SaveState(LevelData lvl, ArrayList<Entity> ens, Player pl) 
    {
        this.level = lvl;
        this.enemies = ens;
        this.player = pl;
    }

}
