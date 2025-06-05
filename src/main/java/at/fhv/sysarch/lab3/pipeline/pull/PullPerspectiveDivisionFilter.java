package at.fhv.sysarch.lab3.pipeline.pull;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import com.hackoeur.jglm.Mat4;
import javafx.scene.paint.Color;

import static at.fhv.sysarch.lab3.utils.MatrixUtils.divideVectorByWeight;
import static at.fhv.sysarch.lab3.utils.MatrixUtils.multiplyVectorWithMatrix;


public class PullPerspectiveDivisionFilter implements PullFilter<Pair<Face, Color>> {

    private final Mat4 viewPortMatrix;
    private final PullFilter<Pair<Face, Color>> source;

    public PullPerspectiveDivisionFilter(Mat4 viewPortMatrix, PullFilter<Pair<Face, Color>> source) {
        this.viewPortMatrix = viewPortMatrix;
        this.source = source;
    }

    @Override
    public Pair<Face, Color> pull() {
        Pair<Face, Color> pair = source.pull();
        Face weightFace = divideVectorByWeight(pair.fst());
        return new Pair<>(multiplyVectorWithMatrix(viewPortMatrix, weightFace), pair.snd());
    }

    @Override
    public Boolean hasNext() {
        return source.hasNext();
    }
}
