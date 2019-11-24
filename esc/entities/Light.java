package entities;


import components.PositionComponent;
import javafx.scene.paint.Color;
import settings.Settings;

/**
 * light entity
 */
public class Light extends Entity {
    // settings
    Settings settings = Settings.getInstance();

    // debug
    private boolean debug = Settings.getDebug("Light@color");

    public Light(double x, double y, double z) {
        super();

        // create random color
        int randomness = 50;
        int groundLightness = 100;
        int r = (int) (Math.random()*randomness) + groundLightness;
        int g = (int) (Math.random()*randomness) + groundLightness;
        int b = (int) (Math.random()*randomness) + groundLightness;
        Color.rgb(r, g,  b);
        if(debug) System.out.println("light color: "+r+","+g+","+b);

        addComponent(new PositionComponent(x, y));
        //addComponent(new LightComponent(color));
        //addComponent(new RenderComponent());
    }

/*
 * public void setMaterial(PhongMaterial material) {
 * 
 * }
 */
 }