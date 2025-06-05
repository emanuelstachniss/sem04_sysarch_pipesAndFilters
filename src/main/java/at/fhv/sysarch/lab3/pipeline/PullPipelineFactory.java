package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.animation.AnimationRenderer;
import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.pull.*;
import at.fhv.sysarch.lab3.utils.MatrixUtils;
import com.hackoeur.jglm.Mat4;
import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;

public class PullPipelineFactory {
    public static AnimationTimer createPipeline(PipelineData pd) {

        // pull from the source (model)
        PullSourceModel source = new PullSourceModel();

        // perform model-view transformation from model to VIEW SPACE coordinates
        PullModelViewTransformationFilter modelViewTransformationFilter = new PullModelViewTransformationFilter(source);

        // perform backface culling in VIEW SPACE
        PullFilter<Face> backfaceFilter = new PullBackfaceCullingFilter(modelViewTransformationFilter);

        // perform depth sorting in VIEW SPACE
        PullFilter<Face> depthSortingFilter = new PullDepthSortingFilter(backfaceFilter);

        // add coloring (space unimportant)
        PullFilter<Pair<Face, Color>> colorFilter = new PullColorFilter(pd, depthSortingFilter);

        // lighting can be switched on/off
//        if (pd.isPerformLighting()) {
//            // 4a. TODO perform lighting in VIEW SPACE
//
//            // 5. TODO perform projection transformation on VIEW SPACE coordinates
//        } else {
//            // 5. TODO perform projection transformation
//        }

        PullFilter<Pair<Face, Color>> projectionTransformationFilter = new PullProjectionTransformationFilter(pd.getProjTransform(), colorFilter);

        // perform perspective division to screen coordinates
        PullFilter<Pair<Face, Color>> perspectiveDivisionFilter = new PullPerspectiveDivisionFilter(pd.getViewportTransform(), projectionTransformationFilter);

        // feed into the sink (renderer)
        PullRenderer renderer = new PullRenderer(pd.getGraphicsContext(), pd.getRenderingMode(), perspectiveDivisionFilter);

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

                // update model-view filter
                source.updateModel(model);

                // trigger rendering of the pipeline
                renderer.run();
            }
        };
    }
}