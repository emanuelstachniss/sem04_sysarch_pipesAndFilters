package at.fhv.sysarch.lab3.pipeline.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.utils.MatrixUtils;
import com.hackoeur.jglm.Mat4;

public class ViewTransformFilter implements PushFilter<Face, Face> {

    private final Mat4 viewTransformMatrix;
    private PushFilter<Face, ?> successor;

    public ViewTransformFilter(Mat4 viewTransformMatrix) {
        this.viewTransformMatrix = viewTransformMatrix;
    }

    @Override
    public void setSuccessor(PushFilter<Face, ?> successor) {
        this.successor = successor;
    }

    @Override
    public void push(Face f) {
        if (f != null) {
            this.successor.push(MatrixUtils.multiplyVectorWithMatrix(viewTransformMatrix,f));
        }
        else {
            this.successor.push(null);
        }
    }
}