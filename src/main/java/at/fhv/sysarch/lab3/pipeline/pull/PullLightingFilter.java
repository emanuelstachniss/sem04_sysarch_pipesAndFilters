package at.fhv.sysarch.lab3.pipeline.pull;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import com.hackoeur.jglm.Vec3;
import javafx.scene.paint.Color;

public class PullLightingFilter implements PullFilter<Pair<Face, Color>> {

    private final Vec3 unitVector;
    private final PullFilter<Pair<Face, Color>> source;

    public PullLightingFilter(PullFilter<Pair<Face, Color>> source, Vec3 unitVector) {
        this.source = source;
        this.unitVector = unitVector;
    }

    @Override
    public Pair<Face, Color> pull() {
        Pair<Face, Color> pair = source.pull();
        Face f = pair.fst();
        Color c = pair.snd();
        float shading = f.getN1().toVec3().dot(unitVector);
        return new Pair<>(f, c.deriveColor(0, 1, shading, 1));
    }

    @Override
    public Boolean hasNext() {
        return source.hasNext();
    }
}
