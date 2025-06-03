package at.fhv.sysarch.lab3.pipeline.pull;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import com.hackoeur.jglm.Mat4;

public class PullModelViewTransformationFilter implements PullFilter<Face> {

    private PullFilter<Face> source;
    private PipelineData pd;
    private Mat4 rotationMatrix;

    public PullModelViewTransformationFilter(PipelineData pd) {
        this.pd = pd;
    }

    @Override
    public void setSource(PullFilter<Face> source) {
        this.source = source;
    }

    @Override
    public Face pull() {
        return null;
    }

    @Override
    public Boolean hasNext() {
        return null;
    }

    public void setRotationMatrix(Mat4 newRot) {
        rotationMatrix = newRot;
    }
}
