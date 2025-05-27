package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.animation.AnimationRenderer;
import at.fhv.sysarch.lab3.obj.Model;
import at.fhv.sysarch.lab3.pipeline.filter.*;
import at.fhv.sysarch.lab3.utils.MatrixUtils;
import com.hackoeur.jglm.Mat4;

import javafx.animation.AnimationTimer;


public class PushPipelineFactory {
    public static AnimationTimer createPipeline(PipelineData pd) {

        PushFilter sourceModel = new ModelSourceFilter();
        PushFilter scaleFilter = new ScaleFilter(new Mat4(1));
        PushFilter rotFilter = new RotationFilter(MatrixUtils.createRotationMatrix(pd.getModelRotAxis(), 0));
        PushFilter translationFilter = new TranslationFilter(pd.getModelTranslation());
        PushFilter viewTransformFilter = new ViewTransformFilter(pd.getViewTransform());
        PushFilter backfaceCullingFilter = new BackfaceCullingFilter();
        PushFilter depthSortingFilter = new DepthSortingFilter();
        PushFilter projectionFilter = new ProjectionFilter(pd.getProjTransform());
        PushFilter perspectiveFilter = new PerspectiveDivisionFilter();
        PushFilter viewPortTransformFilter = new ViewPortTransformFilter(pd.getViewportTransform());
        PushFilter renderer = new Renderer(pd.getGraphicsContext(), pd.getModelColor(), pd.getRenderingMode());


        sourceModel.setSuccessor(scaleFilter);

        scaleFilter.setSuccessor(rotFilter);

        rotFilter.setSuccessor(translationFilter);

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

        translationFilter.setSuccessor(viewTransformFilter);

        viewTransformFilter.setSuccessor(backfaceCullingFilter);

        backfaceCullingFilter.setSuccessor(depthSortingFilter);

        depthSortingFilter.setSuccessor(projectionFilter);

        projectionFilter.setSuccessor(perspectiveFilter);

        perspectiveFilter.setSuccessor(viewPortTransformFilter);

        viewPortTransformFilter.setSuccessor(renderer);

        // TODO 6. perform perspective division to screen coordinates
        // TODO 7. feed into the sink (renderer)

        return new AnimationRenderer(pd) {
            private float animationRotation = 0f;

            @Override
            protected void render(float fraction, Model model) {

                animationRotation += (float) (fraction * Math.toRadians(10));
                Mat4 newRot = MatrixUtils.createRotationMatrix(pd.getModelRotAxis(), animationRotation);
                ((RotationFilter) rotFilter).setRotationMatrix(newRot);

                ((ModelSourceFilter)sourceModel).run(model);

                // After all faces have been pushed, flush the sorting filter
                ((DepthSortingFilter) depthSortingFilter).flush();
            }
        };
    }
}