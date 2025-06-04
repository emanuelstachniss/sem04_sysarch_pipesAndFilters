package at.fhv.sysarch.lab3.pipeline.pull;


import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.rendering.RenderingMode;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PullRenderer implements PullFilter<Pair<Face, Color>> {

    private final GraphicsContext gc;
    private final RenderingMode rm;
    private final PullFilter<Pair<Face, Color>> source;

    public PullRenderer(GraphicsContext gc, RenderingMode rm, PullFilter<Pair<Face, Color>> source) {
        this.gc = gc;
        this.rm = rm;
        this.source = source;
    }

    @Override
    public Pair<Face, Color> pull() {
        return null;
    }

    @Override
    public Boolean hasNext() {
        return null;
    }

    public void run() {
        while(source.hasNext()) {
            Pair<Face, Color> pair = source.pull();

            Color color = pair.snd();
            gc.setStroke(color);
            gc.setFill(color);

            Face f = pair.fst();
            var cordX = new double[]{ f.getV1().getX(), f.getV2().getX(), f.getV3().getX() };
            var cordY = new double[]{ f.getV1().getY(), f.getV2().getY(), f.getV3().getY() };

            switch (rm) {
                case POINT -> gc.fillOval(cordX[0], cordY[0], 2, 2);
                case WIREFRAME -> gc.strokePolygon(cordX, cordY, 3);
                case FILLED -> {
                    gc.fillPolygon(cordX, cordY, 3);
                    gc.strokePolygon(cordX, cordY, 3);
                }
            }
        }
    }
}
