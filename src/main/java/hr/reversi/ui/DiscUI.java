package hr.reversi.ui;

import hr.reversi.util.DiscState;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class DiscUI {
    private final double width = 75.0;
    private final double height = 75.0;
    private final double radius = 25.0;

    public Circle makeDisc(final DiscState discState) {
        Circle circle = new Circle();
        if (discState == DiscState.white) {
            // white disc
            circle.setCenterX(width);
            circle.setCenterY(height);
            circle.setRadius(radius);
            circle.setFill(Color.WHITE);
        } else if (discState == DiscState.black) {
            // black disc
            circle.setCenterX(width);
            circle.setCenterY(height);
            circle.setRadius(radius);
            circle.setFill(Color.BLACK);
        }

        circle = addSpotEffect(circle, discState);
        return circle;
    }

    public Circle addSpotEffect(final Circle disc, final DiscState discState) {
        Light.Spot light = new Light.Spot();
        final int x = 4;
        final int y = 1;
        final int z = 55;
        light.setColor(Color.WHITE);
        light.setX(x);
        light.setY(y);
        light.setZ(z);
        Lighting lighting = new Lighting();
        lighting.setLight(light);
        disc.setEffect(lighting);
        return disc;
    }
}
