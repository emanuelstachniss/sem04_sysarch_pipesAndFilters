package at.fhv.sysarch.lab3.utils;

import at.fhv.sysarch.lab3.obj.Face;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;

public class MatrixUtils {

    public static Mat4 translationMatrix(Vec3 trans) {
        return Mat4.MAT4_IDENTITY.translate(trans);
    }

    public static Mat4 viewportMatrix(int width, int height) {
        // NOTE: JavaFX coordinate system starts top left corner,
        // therefore need to invert y-axis
        float xmax = width;
        float xmin = 0;
        float ymax = 0;
        float ymin = height;

        return new Mat4(new Vec4((xmax - xmin) / 2f, 0, 0, 0),
                        new Vec4(0, (ymax - ymin) / 2, 0, 0),
                        new Vec4(0, 0, 0.5f, 0),
                        new Vec4((xmax + xmin) / 2f, (ymax + ymin) / 2f, 0.5f, 1f));
    }

    // Creates a rotation matrix for a given axis and angle (in radians)
    public static Mat4 createRotationMatrix(Vec3 axis, float angleRadians) {
        float x = axis.getX();
        float y = axis.getY();
        float z = axis.getZ();
        float c = (float) Math.cos(angleRadians);
        float s = (float) Math.sin(angleRadians);
        float t = 1 - c;

        return new Mat4(
                new Vec4(t*x*x + c,   t*x*y - s*z, t*x*z + s*y, 0),
                new Vec4(t*x*y + s*z, t*y*y + c,   t*y*z - s*x, 0),
                new Vec4(t*x*z - s*y, t*y*z + s*x, t*z*z + c,   0),
                new Vec4(0,           0,           0,           1)
        );
    }

    public static Face multiplyVectorWithMatrix(Mat4 matrix, Face f){
        Vec4 v1 = matrix.multiply(f.getV1());
        Vec4 v2 = matrix.multiply(f.getV2());
        Vec4 v3 = matrix.multiply(f.getV3());

        // Also rotate the normals if present
        Vec4 n1 = matrix.multiply(f.getN1());
        Vec4 n2 = matrix.multiply(f.getN2());
        Vec4 n3 = matrix.multiply(f.getN3());

        return new Face(v1, v2, v3, n1, n2, n3);
    }

    public static Face divideVectorByWeight(Face f){
        Vec4 v1 = divide(f.getV1());
        Vec4 v2 = divide(f.getV2());
        Vec4 v3 = divide(f.getV3());

        return new Face(v1, v2, v3, f); // reuse face data like color or normal
    }

    private static Vec4 divide(Vec4 v) {
        float w = v.getW();
        if (w == 0f) return v;
        return new Vec4(
                v.getX() / w,
                v.getY() / w,
                v.getZ() / w,
                1f
        );
    }
}