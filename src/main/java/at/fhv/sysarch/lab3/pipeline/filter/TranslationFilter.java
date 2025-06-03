package at.fhv.sysarch.lab3.pipeline.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.utils.MatrixUtils;
import com.hackoeur.jglm.Mat4;

public class TranslationFilter implements PushFilter<Face, Face> {

    private final Mat4 translationMatrix;
    private PushFilter<Face, ?> successor;

    public TranslationFilter(Mat4 translationMatrix) {
        this.translationMatrix = translationMatrix;
    }

    @Override
    public void setSuccessor(PushFilter<Face, ?> successor) {
        this.successor = successor;
    }

    @Override
    public void push(Face f) {
        if (f != null) {
            this.successor.push(MatrixUtils.multiplyVectorWithMatrix(translationMatrix, f));
        }
        else {
            this.successor.push(null);
        }
    }
}
