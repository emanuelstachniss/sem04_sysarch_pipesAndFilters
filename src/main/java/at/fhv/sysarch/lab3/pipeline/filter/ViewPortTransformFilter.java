package at.fhv.sysarch.lab3.pipeline.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.utils.MatrixUtils;
import com.hackoeur.jglm.Mat4;

public class ViewPortTransformFilter implements PushFilter {

    private final Mat4 viewPortMatrix;
    private PushFilter successor;

    public ViewPortTransformFilter(Mat4 viewPortMatrix) {
        this.viewPortMatrix = viewPortMatrix;
    }

    @Override
    public void setSuccessor(PushFilter successor) {
        this.successor = successor;
    }

    @Override
    public void push(Face face) {
        this.successor.push(MatrixUtils.multiplyVectorWithMatrix(viewPortMatrix,face));
    }
}