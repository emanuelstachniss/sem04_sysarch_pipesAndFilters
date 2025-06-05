package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.animation.AnimationRenderer;
import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.push.*;
import at.fhv.sysarch.lab3.utils.MatrixUtils;
import com.hackoeur.jglm.Mat4;

import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;


public class PushPipelineFactory {
    public static AnimationTimer createPipeline(PipelineData pd) {

        PushModelSourceFilter sourceModel = new PushModelSourceFilter();

        PushFilter<Face, Face> scaleFilter = new PushScaleFilter(new Mat4(1));
        PushRotationFilter rotFilter = new PushRotationFilter(MatrixUtils.createRotationMatrix(pd.getModelRotAxis(), 0));
        PushFilter<Face, Face> translationFilter = new PushTranslationFilter(pd.getModelTranslation());
        PushFilter<Face, Face> viewTransformFilter = new PushViewTransformFilter(pd.getViewTransform());

        PushFilter<Face, Face> backfaceCullingFilter = new PushBackfaceCullingFilter();
        PushFilter<Face, Face> depthSortingFilter = new PushDepthSortingFilter();

        PushFilter<Face, Pair<Face, Color>> colorFilter = new PushColorFilter(pd.getModelColor());
        PushFilter<Pair<Face, Color>, Pair<Face, Color>> lightingFilter = new PushLightingFilter(pd.getLightPos().getUnitVector());

        PushFilter<Pair<Face, Color>, Pair<Face, Color>> projectionFilter = new PushProjectionFilter(pd.getProjTransform());
        PushFilter<Pair<Face, Color>, Pair<Face, Color>> perspectiveFilter = new PushPerspectiveDivisionFilter();
        PushFilter<Pair<Face, Color>, Pair<Face, Color>> viewPortTransformFilter = new PushViewPortTransformFilter(pd.getViewportTransform());
        PushFilter<Pair<Face, Color>, Pair<Face, Color>> renderer = new PushRenderer(pd.getGraphicsContext(), pd.getRenderingMode());


        sourceModel.setSuccessor(scaleFilter);

        scaleFilter.setSuccessor(rotFilter);

        rotFilter.setSuccessor(translationFilter);

        translationFilter.setSuccessor(viewTransformFilter);

        viewTransformFilter.setSuccessor(backfaceCullingFilter);

        backfaceCullingFilter.setSuccessor(depthSortingFilter);

        depthSortingFilter.setSuccessor(colorFilter);

        // lighting can be switched on/off
        if (pd.isPerformLighting()) {
            colorFilter.setSuccessor(lightingFilter);
            lightingFilter.setSuccessor(projectionFilter);
        } else {
            colorFilter.setSuccessor(projectionFilter);
        }

        projectionFilter.setSuccessor(perspectiveFilter);

        perspectiveFilter.setSuccessor(viewPortTransformFilter);

        viewPortTransformFilter.setSuccessor(renderer);

        return new AnimationRenderer(pd) {
            private float animationRotation = 0f;

            @Override
            protected void render(float fraction, Model model) {

                animationRotation += (float) (fraction * Math.toRadians(10));
                Mat4 newRot = MatrixUtils.createRotationMatrix(pd.getModelRotAxis(), animationRotation);

                rotFilter.setRotationMatrix(newRot);

                sourceModel.run(model);

            }
        };
    }
}