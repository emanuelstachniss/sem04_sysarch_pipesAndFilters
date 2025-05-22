package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.animation.AnimationRenderer;
import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;
import at.fhv.sysarch.lab3.utils.MatrixUtils;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Vec;
import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;

public class PushPipelineFactory {
    public static AnimationTimer createPipeline(PipelineData pd) {
        // TODO: push from the source (model)

        // TODO 1. perform model-view transformation from model to VIEW SPACE coordinates

        // TODO 2. perform backface culling in VIEW SPACE

        // TODO 3. perform depth sorting in VIEW SPACE

        // TODO 4. add coloring (space unimportant)

        // lighting can be switched on/off
        if (pd.isPerformLighting()) {
            // 4a. TODO perform lighting in VIEW SPACE

            // 5. TODO perform projection transformation on VIEW SPACE coordinates
        } else {
            // 5. TODO perform projection transformation
        }

        // TODO 6. perform perspective division to screen coordinates

        // TODO 7. feed into the sink (renderer)

        // returning an animation renderer which handles clearing of the
        // viewport and computation of the fraction
        return new AnimationRenderer(pd) {
            GraphicsContext gc = pd.getGraphicsContext();
            private float rotationAngle = 0f;
            // TODO rotation variable goes in here

            /** This method is called for every frame from the JavaFX Animation
             * system (using an AnimationTimer, see AnimationRenderer).
             * @param fraction the time which has passed since the last render call in a fraction of a second
             * @param model    the model to render
             */
            @Override
            protected void render(float fraction, Model model) {
                gc.setStroke(pd.getModelColor());

                // Set up model transformation parameters
                Vec3 modelPos = pd.getModelPos();
                Vec3 modelScale = new Vec3(100, 100, 100);

                Vec3 yAxis = new Vec3(1, 0, 0);

                // Use a class field to accumulate rotation angle (in radians)
                // For example, add: private float rotationAngle = 0; at the class level
                rotationAngle += fraction * Math.PI; // Adjust speed as needed

                Mat4 scaleMatrix = MatrixUtils.createScalingMatrix(modelScale);
                Mat4 rotationMatrix = MatrixUtils.createRotationMatrix(yAxis, rotationAngle);
                Mat4 translationMatrix = MatrixUtils.translationMatrix(modelPos);

                Mat4 modelMatrix = translationMatrix.multiply(rotationMatrix).multiply(scaleMatrix);

                Mat4 viewMatrix = pd.getViewTransform();
                Mat4 projectionMatrix = pd.getProjTransform();

                Mat4 mvpMatrix = projectionMatrix.multiply(viewMatrix).multiply(modelMatrix);

                for (Face face : model.getFaces()) {
                    Vec4 v1 = mvpMatrix.multiply(face.getV1());
                    Vec4 v2 = mvpMatrix.multiply(face.getV2());
                    Vec4 v3 = mvpMatrix.multiply(face.getV3());

                    gc.strokeLine(v1.getX(), v1.getY(), v2.getX(), v2.getY());
                    gc.strokeLine(v2.getX(), v2.getY(), v3.getX(), v3.getY());
                    gc.strokeLine(v3.getX(), v3.getY(), v1.getX(), v1.getY());
                }
            }

        };
    }
}