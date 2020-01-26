package UnitTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import components.AIComponent;
import components.AITargetComponent;
import components.PositionComponent;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Point2D;
import level.*;
import settings.Settings;
import systems.AISystem;

class UnitTests {
	
	

	/**
	 * Testing Level Builder (with/without templates)
	 */
	@Test
	void LevelBuilderTest() {
		
		//Needed to initialze JavaFX runtime to prevent errors
		JFXPanel jfxPanel = new JFXPanel();
		
		boolean fromFile = true;
		int lvlnum = 1;		
		LevelGenerator lvlGen = new LevelGenerator();
		LevelData lvlData;
		int roomNum = 5;
		int roomXDim = 20;
		int roomYDim = 10;
		
		lvlGen.setLayout(roomNum, roomXDim, roomYDim);
		
		
		
		lvlData = lvlGen.generateLevel(lvlnum, fromFile);		
		assertNotNull(lvlData);		
		
		
		lvlData = lvlGen.generateLevel(lvlnum, false);		
		assertNotNull(lvlData);		
		
		
		assertEquals(lvlData.getLevelNumber(),lvlnum );
		assertEquals(lvlData.getLevelRoomList().size(), roomNum);
		
		
		
	}
	
	/**
	 * Testing PicUtilsTest
	 */
	@Test
	void PicUtilsTest() {
		
		//Needed to initialze JavaFX runtime to prevent errors
		JFXPanel jfxPanel = new JFXPanel();
		
		int roomXDim = 20;
		int roomYDim = 10;
		String path = "./level/roomTemplates/";
		int[][] roomDat;
		
		
		roomDat = LevelPicUtil.getLevelDatafromPNG(path, roomXDim, roomYDim);				
		
		assertNotNull(roomDat);
		assertEquals(roomDat.length, roomXDim);
		assertEquals(roomDat[0].length, roomYDim);
		
	}
	
	//Testing player detection
	@Test
	void AISystemTest() {		
		
		//Needed to initialze JavaFX runtime to prevent errors
		JFXPanel jfxPanel = new JFXPanel();
		
		//Inside detection radius, positive vector
		
		AISystem AISys = new AISystem();		
		Point2D playPos = new Point2D(50,50);
		Point2D EnemyPos = new Point2D(75,75);
		int radius = 100;
		int movement = Settings.getSpeed();
		
		AIComponent enemy = new AIComponent(new PositionComponent(EnemyPos.getX(),EnemyPos.getY()), radius,true);		
		AITargetComponent player = new AITargetComponent(new PositionComponent(playPos.getX(),playPos.getY()), true); 
				
		
		//assertEquals( new Point2D(movement, movement),AISys.getVelocity(enemy, player));
		
		//assertTrue( AISys.IsInRadius(enemy, player));
		
		//outside detection radius, pos/neg vector
		
		Point2D playPos2 = new Point2D(50,50);
		Point2D EnemyPos2 = new Point2D(200,-200);				
		
		enemy = new AIComponent(new PositionComponent(EnemyPos2.getX(),EnemyPos2.getY()), radius,true);		
		player = new AITargetComponent(new PositionComponent(playPos2.getX(),playPos2.getY()), true); 
		
		//System.out.println(AISys.getVelocity(enemy, player));
		
		//assertEquals( new Point2D(movement, -movement),AISys.getVelocity(enemy, player));
		
		//assertFalse( AISys.IsInRadius(enemy, player));		
		
	}
	

}
