package at.fhv.sysarch.lab3.pipeline.filter;

import at.fhv.sysarch.lab3.obj.Face;
import com.hackoeur.jglm.Vec3;

public class BackfaceCullingFilter implements PushFilter<Face, Face> {

    private PushFilter<Face, ?> successor;

    @Override
    public void setSuccessor(PushFilter<Face, ?> successor) {
        this.successor = successor;
    }

    @Override
    public void push(Face f) {
        if (f != null) {
            if (f.getV1().dot(f.getN1()) < 0) {
                successor.push(f);
            }
        }
        else {
            successor.push(null);
        }
    }
}
