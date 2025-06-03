package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.animation.AnimationRenderer;
import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;
import at.fhv.sysarch.lab3.pipeline.pull.*;
import at.fhv.sysarch.lab3.utils.MatrixUtils;
import com.hackoeur.jglm.Mat4;
import javafx.animation.AnimationTimer;

public class PullPipelineFactory {
    public static AnimationTimer createPipeline(PipelineData pd) {
        // TODO: pull from the source (model)

        // TODO 1. perform model-view transformation from model to VIEW SPACE coordinates
        PullModelViewTransformationFilter modelViewTransformationFilter = new PullModelViewTransformationFilter(pd);

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
        // viewport and computation of the praction
        return new AnimationRenderer(pd) {

            // rotation variable goes in here
            private float animationRotation = 0f;

            @Override
            protected void render(float fraction, Model model) {
                // compute rotation in radians
                animationRotation += (float) (fraction * Math.toRadians(10));

                // create new model rotation matrix using pd.getModelRotAxis and Matrices.rotate
                Mat4 newRot = MatrixUtils.createRotationMatrix(pd.getModelRotAxis(), animationRotation);

                // compute updated model-view tranformation
                modelViewTransformationFilter.setRotationMatrix(newRot);

                // TODO update model-view filter

                // TODO trigger rendering of the pipeline
            }
        };
    }
}