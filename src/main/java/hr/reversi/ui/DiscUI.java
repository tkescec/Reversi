package hr.reversi.ui;

import hr.reversi.util.DiscState;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

public class DiscUI {
    private final double width = 65.0;
    private final double height = 65.0;
    private final double markerWidth = width - 2;
    private final double markerHeight = height - 2;
    private final double radius = 25.0;
    private final double markerRadius = 5.0;

    /** Makes disc filled with color defined by state.
     *
     * @param discState disc state.
     * @return Circle object.
     */
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

    /** Adds spotlight effect to discs.
     *
     * @param disc disc object.
     * @param discState disc state.
     * @return disc with spotlight effect.
     */
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

    /** DiscMarker class.
     *  Contains view for disc markers.
     */
    public class DiscMarker {

        /**DiscMarker container. */
        public DiscMarker() {

        }

        /** Creates disc marker for flipped discs.
         *
         * @return Circle object.
         */
        public Circle flipDiscMarker() {
            Circle circle = new Circle();
            circle.setCenterX(width);
            circle.setCenterY(height);
            circle.setRadius(markerRadius);
            circle.setFill(Color.RED);

            return circle;
        }
    }

    /** MoveMarker class.
     *  Contains view for move markers.
     */
    public class MoveMarker{

        /**MoveMarker container. */
        public MoveMarker() {

        }

        /** Makes valid move marker.
         * It is used to highlight square on board grid.
         * @return rectangle object.
         */
        public Rectangle validMoveMarker() {
            final double opacity = 0.30;
            final double borderOpacity = 1.0;
            final int strokeWidth = 2;
            Rectangle rectangle = new Rectangle(markerWidth,
                    markerHeight, Color.web("#9DC8E4", opacity));
            rectangle.setStroke(Color.web("#9DC8E4", borderOpacity));
            rectangle.setStrokeWidth(strokeWidth);
            rectangle.setStrokeType(StrokeType.INSIDE);
            return rectangle;
        }
    }
}
