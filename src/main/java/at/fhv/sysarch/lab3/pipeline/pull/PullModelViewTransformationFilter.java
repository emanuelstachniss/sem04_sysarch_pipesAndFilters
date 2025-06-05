package at.fhv.sysarch.lab3.pipeline.pull;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import com.hackoeur.jglm.Mat4;

import static at.fhv.sysarch.lab3.utils.MatrixUtils.multiplyVectorWithMatrix;

public class PullModelViewTransformationFilter implements PullFilter<Face> {

    private PullFilter<Face> source;
    private Mat4 rotationMatrix;

    public PullModelViewTransformationFilter(PullFilter<Face> source) {
        this.source = source;
    }

    @Override
    public Face pull() {
        return multiplyVectorWithMatrix(rotationMatrix, source.pull());
    }

    @Override
    public Boolean hasNext() {
        return source.hasNext();
    }

    public void setRotationMatrix(Mat4 newRot) {
        rotationMatrix = newRot;
    }
}
