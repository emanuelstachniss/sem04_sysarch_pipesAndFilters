package at.fhv.sysarch.lab3.pipeline.pull;


import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import javafx.scene.paint.Color;

public class PullRenderer implements PullFilter<Pair<Face, Color>> {

    private final PipelineData pd;

    public PullRenderer(PipelineData pd) {
        this.pd = pd;
    }

    @Override
    public void setSource(PullFilter<Pair<Face, Color>> source) {
        // does not have a source
    }

    @Override
    public Pair<Face, Color> pull() {
        throw new IllegalCallerException("PullRenderer cannot be pulled");
    }

    @Override
    public Boolean hasNext() {
        return null;
    }
}
