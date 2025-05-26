package at.fhv.sysarch.lab3.pipeline.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.utils.MatrixUtils;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Vec4;

public class ScaleFilter implements PushFilter {

    private final Mat4 scaleMatrix;
    private PushFilter successor;

    public ScaleFilter(Mat4 scaleMatrix) {
        this.scaleMatrix = scaleMatrix;
    }

    @Override
    public void setSuccessor(PushFilter successor) {
        this.successor = successor;
    }

    @Override
    public void push(Face f) {
        this.successor.push(MatrixUtils.multiplyVectorWithMatrix(scaleMatrix, f));
    }
}
