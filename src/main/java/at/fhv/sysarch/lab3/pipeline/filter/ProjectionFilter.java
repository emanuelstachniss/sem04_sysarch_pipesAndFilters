package at.fhv.sysarch.lab3.pipeline.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.utils.MatrixUtils;
import com.hackoeur.jglm.Mat4;
import javafx.scene.paint.Color;

public class ProjectionFilter implements PushFilter<Pair<Face, Color>, Pair<Face, Color>> {

    private final Mat4 projectionMatrix;
    private PushFilter<Pair<Face, Color>, ?> successor;

    public ProjectionFilter(Mat4 projectionMatrix) {
        this.projectionMatrix = projectionMatrix;
    }

    @Override
    public void setSuccessor(PushFilter<Pair<Face, Color>, ?> successor) {
        this.successor = successor;
    }

    @Override
    public void push(Pair<Face, Color> pair) {
        this.successor.push(new Pair<>(MatrixUtils.multiplyVectorWithMatrix(projectionMatrix, pair.fst()), pair.snd()));
    }
}
