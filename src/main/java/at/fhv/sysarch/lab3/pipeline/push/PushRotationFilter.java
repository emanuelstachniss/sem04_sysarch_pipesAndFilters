package at.fhv.sysarch.lab3.pipeline.push;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.utils.MatrixUtils;
import com.hackoeur.jglm.Mat4;

public class PushRotationFilter implements PushFilter<Face, Face> {

    private Mat4 rotationMatrix;
    private PushFilter<Face, ?> successor;

    public PushRotationFilter(Mat4 rotationMatrix) {
        this.rotationMatrix = rotationMatrix;
    }

    public void setRotationMatrix(Mat4 newRotationMatrix) {
        this.rotationMatrix = newRotationMatrix;
    }

    @Override
    public void setSuccessor(PushFilter<Face, ?> successor) {
        this.successor = successor;
    }

    @Override
    public void push(Face f) {
        if (f != null) {
            this.successor.push(MatrixUtils.multiplyVectorWithMatrix(rotationMatrix, f));
        }
        else {
            this.successor.push(null);
        }
    }
}
