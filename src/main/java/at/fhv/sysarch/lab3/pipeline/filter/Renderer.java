package at.fhv.sysarch.lab3.pipeline.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.rendering.RenderingMode;
import com.hackoeur.jglm.Vec4;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Renderer implements PushFilter {

    private final Color color;
    private final GraphicsContext gc;
    private final RenderingMode rm;

    public Renderer(GraphicsContext gc, Color color, RenderingMode rm) {
        this.gc = gc;
        this.color = color;
        this.rm = rm;
    }

    @Override
    public void setSuccessor(PushFilter successor) {
        // IGNORE
    }

    @Override
    public void push(Face f) {
        Vec4 v1 = f.getV1();
        Vec4 v2 = f.getV2();
        Vec4 v3 = f.getV3();

//        Draw each line either filled or wireframe render mode
        if (rm == RenderingMode.FILLED) {

            double[] xPoints = {v1.getX(), v2.getX(), v3.getX()};
            double[] yPoints = {v1.getY(), v2.getY(), v3.getY()};
            gc.setFill(color);
            gc.fillPolygon(xPoints, yPoints, 3);

        } else if (rm == RenderingMode.WIREFRAME) {
            gc.setStroke(color);

            gc.strokeLine(v1.getX(), v1.getY(), v2.getX(), v2.getY());
            gc.strokeLine(v2.getX(), v2.getY(), v3.getX(), v3.getY());
            gc.strokeLine(v3.getX(), v3.getY(), v1.getX(), v1.getY());

        } else if (rm == RenderingMode.POINT) {
            gc.setFill(color);

            gc.fillOval(v1.getX() - 0.5, v1.getY() - 0.5, 1, 1);
            gc.fillOval(v2.getX() - 0.5, v2.getY() - 0.5, 1, 1);
            gc.fillOval(v3.getX() - 0.5, v3.getY() - 0.5, 1, 1);
        }
    }
}
