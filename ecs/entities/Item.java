package entities;

import java.util.HashMap;
import java.util.Random;

import components.*;
import javafx.geometry.Point2D;

/**
 * item entity
 * @author franzi
 */
public class Item extends Entity {



    public Item() {
        super();
        init(0,0);
    }


    public Item(double x, double y) {
        super();
        init(x, y);
    }

    /**
     * construction
     * @param x
     * @param y
     */
    private void init(double x, double y) {
        addComponent(new PositionComponent(x, y));
        //Temporare Zufallsmechanismus für Items entweder Health oder Weapon
        Random zufallsItem = new Random();
        int zufallsZahlItem = zufallsItem.nextInt(2);

        if(zufallsZahlItem==0){
            addComponent(new Sprite("pic/potion",new Point2D(x, y),false));
            addComponent(new HealthComponent(2000.0));
        } else {
            addComponent(new Sprite("pic/shield", new Point2D(x,y), false));
            addComponent(new WeaponComponent(10.0));
        }
        addComponent(new RenderComponent());

    }

    /**
     * add material
     * @param material
     */
    /*
     * @Override public void setMaterial(PhongMaterial material) { ((ShapeComponent)
     * this.getComponent(ShapeComponent.class)).setMaterial(material); }
     */
}
