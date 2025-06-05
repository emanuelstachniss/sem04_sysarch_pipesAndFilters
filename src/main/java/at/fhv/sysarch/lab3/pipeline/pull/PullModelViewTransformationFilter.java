package at.fhv.sysarch.lab3.pipeline.pull;

import at.fhv.sysarch.lab3.obj.Face;
import com.hackoeur.jglm.Mat4;

import static at.fhv.sysarch.lab3.utils.MatrixUtils.multiplyVectorWithMatrix;

public class PullModelViewTransformationFilter implements PullFilter<Face> {

    private final PullFilter<Face> source;
    private final Mat4 translationMatrix;
    private final Mat4 viewTransformMatrix;
    private final Mat4 scaleMatrix = new Mat4(1);
    private Mat4 rotationMatrix;

    public PullModelViewTransformationFilter(PullFilter<Face> source, Mat4 translationMatrix, Mat4 viewTransformMatrix) {
        this.source = source;
        this.translationMatrix = translationMatrix;
        this.viewTransformMatrix = viewTransformMatrix;
    }

    @Override
    public Face pull() {
        Face f = source.pull();
        Face scaledFace = multiplyVectorWithMatrix(scaleMatrix, f);
        Face rotatedFace = multiplyVectorWithMatrix(rotationMatrix, scaledFace);
        Face translationFace = multiplyVectorWithMatrix(translationMatrix, rotatedFace);
        return multiplyVectorWithMatrix(viewTransformMatrix, translationFace);
    }

    @Override
    public Boolean hasNext() {
        return source.hasNext();
    }

    public void setRotationMatrix(Mat4 newRot) {
        rotationMatrix = newRot;
    }
}
