package at.fhv.sysarch.lab3.pipeline.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.rendering.RenderingMode;
import com.hackoeur.jglm.Vec4;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Renderer implements PushFilter<Pair<Face, Color>, Pair<Face, Color>> {

    private final GraphicsContext gc;
    private final RenderingMode rm;

    public Renderer(GraphicsContext gc, RenderingMode rm) {
        this.gc = gc;
        this.rm = rm;
    }

    @Override
    public void setSuccessor(PushFilter<Pair<Face, Color>, ?> successor) {
        // IGNORE
    }

    @Override
    public void push(Pair<Face, Color> pair) {
        Color color = pair.snd();
        gc.setStroke(color);
        gc.setFill(color);

        Face f = pair.fst();
//        var cordX = new double[]{ f.getV1().getX(), f.getV2().getX(), f.getV3().getX() };
//        var cordY = new double[]{ f.getV1().getY(), f.getV2().getY(), f.getV3().getY() };
//
//        switch (rm) {
//            case POINT -> gc.fillOval(cordX[0], cordY[0], 2, 2);
//            case WIREFRAME -> gc.strokePolygon(cordX, cordY, 3);
//            case FILLED -> {
//                gc.fillPolygon(cordX, cordY, 3);
//                gc.strokePolygon(cordX, cordY, 3);
//            }
//        }

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
