package at.fhv.sysarch.lab3.pipeline.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.utils.MatrixUtils;
import com.hackoeur.jglm.Mat4;

public class RotationFilter implements PushFilter {

    private Mat4 rotationMatrix;
    private PushFilter successor;

    public RotationFilter(Mat4 rotationMatrix) {
        this.rotationMatrix = rotationMatrix;
    }

    public void setRotationMatrix(Mat4 newRotationMatrix) {
        this.rotationMatrix = newRotationMatrix;
    }

    @Override
    public void setSuccessor(PushFilter successor) {
        this.successor = successor;
    }

    @Override
    public void push(Face f) {
        this.successor.push(MatrixUtils.multiplyVectorWithMatrix(rotationMatrix, f));
    }
}
