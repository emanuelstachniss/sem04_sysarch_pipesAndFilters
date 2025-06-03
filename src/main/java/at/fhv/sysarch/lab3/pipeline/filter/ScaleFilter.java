package at.fhv.sysarch.lab3.pipeline.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.utils.MatrixUtils;
import com.hackoeur.jglm.Mat4;

public class ScaleFilter implements PushFilter<Face, Face> {

    private final Mat4 scaleMatrix;
    private PushFilter<Face, ?> successor;

    public ScaleFilter(Mat4 scaleMatrix) {
        this.scaleMatrix = scaleMatrix;
    }

    @Override
    public void setSuccessor(PushFilter<Face, ?> successor) {
        this.successor = successor;
    }

    @Override
    public void push(Face f) {
        if (f != null) {
            this.successor.push(MatrixUtils.multiplyVectorWithMatrix(scaleMatrix, f));
        }
        else {
            this.successor.push(null);
        }
    }
}
