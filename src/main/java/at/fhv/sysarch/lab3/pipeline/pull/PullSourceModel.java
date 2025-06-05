package at.fhv.sysarch.lab3.pipeline.pull;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;

import java.util.ArrayDeque;
import java.util.Queue;

public class PullSourceModel implements PullFilter<Face> {

    private final Queue<Face> faces = new ArrayDeque<>();

    @Override
    public Face pull() {
        return faces.poll();
    }

    @Override
    public Boolean hasNext() {
        return !faces.isEmpty();
    }

    public void updateModel(Model model) {
        faces.clear();
        faces.addAll(model.getFaces());
    }
}
