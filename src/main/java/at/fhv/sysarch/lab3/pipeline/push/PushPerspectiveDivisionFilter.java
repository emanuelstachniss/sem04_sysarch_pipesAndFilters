package at.fhv.sysarch.lab3.pipeline.push;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.utils.MatrixUtils;
import javafx.scene.paint.Color;

public class PushPerspectiveDivisionFilter implements PushFilter<Pair<Face, Color>, Pair<Face, Color>> {

    private PushFilter<Pair<Face, Color>, ?> successor;

    @Override
    public void setSuccessor(PushFilter<Pair<Face, Color>, ?> successor) {
        this.successor = successor;
    }

    @Override
    public void push(Pair<Face, Color> pair) {
        this.successor.push(new Pair<>(MatrixUtils.divideVectorByWeight(pair.fst()), pair.snd()));
    }
}
