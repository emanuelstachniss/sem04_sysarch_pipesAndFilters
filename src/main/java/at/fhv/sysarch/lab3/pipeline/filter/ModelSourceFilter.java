package at.fhv.sysarch.lab3.pipeline.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;

public class ModelSourceFilter implements PushFilter<Model, Face> {
    private PushFilter<Face, ?> successor;

    public void run(Model model) {
        model.getFaces().forEach(f -> successor.push(f));
        successor.push(null);
    }

    @Override
    public void setSuccessor(PushFilter<Face, ?> successor) {
        this.successor = successor;
    }

    @Override
    public void push(Model f) {

    }
}
