package at.fhv.sysarch.lab3.pipeline.pull;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import javafx.scene.paint.Color;

public class PullColorFilter implements PullFilter<Pair<Face, Color>> {

    private final PipelineData pd;
    private final PullFilter<Face> source;

    public PullColorFilter(PipelineData pd, PullFilter<Face> source) {
        this.pd = pd;
        this.source = source;
    }

    @Override
    public Pair<Face, Color> pull() {
        return new Pair<>(source.pull(), pd.getModelColor());
    }

    @Override
    public Boolean hasNext() {
        return source.hasNext();
    }
}
