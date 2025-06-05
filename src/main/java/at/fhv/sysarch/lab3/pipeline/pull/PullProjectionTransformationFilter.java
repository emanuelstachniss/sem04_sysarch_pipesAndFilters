package at.fhv.sysarch.lab3.pipeline.pull;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import com.hackoeur.jglm.Mat4;
import javafx.scene.paint.Color;

import static at.fhv.sysarch.lab3.utils.MatrixUtils.multiplyVectorWithMatrix;

public class PullProjectionTransformationFilter implements PullFilter<Pair<Face, Color>> {

    private final Mat4 transformMat;
    private final PullFilter<Pair<Face, Color>> source;

    public PullProjectionTransformationFilter(Mat4 transformMat, PullFilter<Pair<Face, Color>> source) {
        this.transformMat = transformMat;
        this.source = source;
    }

    @Override
    public Pair<Face, Color> pull() {
        Face f = source.pull().fst();
        return new Pair<>(multiplyVectorWithMatrix(transformMat, f), source.pull().snd());
    }

    @Override
    public Boolean hasNext() {
        return source.hasNext();
    }
}
