package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.animation.AnimationRenderer;
import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.filter.*;
import at.fhv.sysarch.lab3.utils.MatrixUtils;
import com.hackoeur.jglm.Mat4;

import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;


public class PushPipelineFactory {
    public static AnimationTimer createPipeline(PipelineData pd) {

        PushFilter<Model, Face> sourceModel = new ModelSourceFilter();

        PushFilter<Face, Face> scaleFilter = new ScaleFilter(new Mat4(1));
        PushFilter<Face, Face> rotFilter = new RotationFilter(MatrixUtils.createRotationMatrix(pd.getModelRotAxis(), 0));
        PushFilter<Face, Face> translationFilter = new TranslationFilter(pd.getModelTranslation());

        PushFilter<Face, Face> viewTransformFilter = new ViewTransformFilter(pd.getViewTransform());

        PushFilter<Face, Face> backfaceCullingFilter = new BackfaceCullingFilter();
        PushFilter<Face, Face> depthSortingFilter = new DepthSortingFilter();

        PushFilter<Face, Pair<Face, Color>> colorFilter = new PushColorFilter(pd.getModelColor());
        PushFilter<Pair<Face, Color>, Pair<Face, Color>> lightingFilter = new PushLightingFilter(pd.getLightPos().getUnitVector());

        PushFilter<Pair<Face, Color>, Pair<Face, Color>> projectionFilter = new ProjectionFilter(pd.getProjTransform());
        PushFilter<Pair<Face, Color>, Pair<Face, Color>> perspectiveFilter = new PerspectiveDivisionFilter();
        PushFilter<Pair<Face, Color>, Pair<Face, Color>> viewPortTransformFilter = new ViewPortTransformFilter(pd.getViewportTransform());
        PushFilter<Pair<Face, Color>, Pair<Face, Color>> renderer = new Renderer(pd.getGraphicsContext(), pd.getRenderingMode());


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
                ((RotationFilter) rotFilter).setRotationMatrix(newRot);

                ((ModelSourceFilter) sourceModel).run(model);

            }
        };
    }
}