package at.fhv.sysarch.lab3.pipeline.filter;

import at.fhv.sysarch.lab3.obj.Face;
import com.hackoeur.jglm.Vec3;

public class BackfaceCullingFilter implements PushFilter {

    private PushFilter successor;

    @Override
    public void setSuccessor(PushFilter successor) {
        this.successor = successor;
    }

    @Override
    public void push(Face f) {
        // Compute face center (ignores w)
        Vec3 center = new Vec3(
                (f.getV1().getX() + f.getV2().getX() + f.getV3().getX()) / 3f,
                (f.getV1().getY() + f.getV2().getY() + f.getV3().getY()) / 3f,
                (f.getV1().getZ() + f.getV2().getZ() + f.getV3().getZ()) / 3f
        );

        // Compute average normal
        Vec3 avgNormal = new Vec3(
                (f.getN1().getX() + f.getN2().getX() + f.getN3().getX()) / 3f,
                (f.getN1().getY() + f.getN2().getY() + f.getN3().getY()) / 3f,
                (f.getN1().getZ() + f.getN2().getZ() + f.getN3().getZ()) / 3f
        ).getUnitVector();

        float dot = avgNormal.dot(center);  // dot(N, V), where V is just center (camera at origin)

        if (dot < 0) {
            successor.push(f);  // it's a front face, keep it
        }
        // otherwise, it's a backface → culled
    }
}
