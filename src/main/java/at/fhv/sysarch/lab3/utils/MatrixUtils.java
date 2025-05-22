package at.fhv.sysarch.lab3.utils;

import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;

public class MatrixUtils {

    public static Mat4 translationMatrix(Vec3 trans) {
        // Mat4 transHand = new Mat4(new Vec4(1, 0, 0, 0),
        //                 new Vec4(0, 1, 0, 0),
        //                 new Vec4(0, 0, 1 ,0),
        //                 new Vec4(trans, 1)); // important: w of translation vector has to be 1!

        return Mat4.MAT4_IDENTITY.translate(trans);
    }

    public static Mat4 viewportMatrix(int width, int height) {
        // NOTE: JavaFX coordinate system starts top left corner,
        // therefore need to invert y axis
        float xmax = width;
        float xmin = 0;
        float ymax = 0;
        float ymin = height;

        return new Mat4(new Vec4((xmax - xmin) / 2f, 0, 0, 0),
                        new Vec4(0, (ymax - ymin) / 2, 0, 0),
                        new Vec4(0, 0, 0.5f, 0),
                        new Vec4((xmax + xmin) / 2f, (ymax + ymin) / 2f, 0.5f, 1f));
    }

    // Creates a scaling matrix for a given scaling vector
    public static Mat4 createScalingMatrix(Vec3 scale) {
        return new Mat4(
                new Vec4(scale.getX(), 0, 0, 0),
                new Vec4(0, scale.getY(), 0, 0),
                new Vec4(0, 0, scale.getZ(), 0),
                new Vec4(0, 0, 0, 1)
        );
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
}