package at.fhv.sysarch.lab3.pipeline.filter;

import at.fhv.sysarch.lab3.obj.Face;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DepthSortingFilter implements PushFilter {

    private final List<Face> faceBuffer = new ArrayList<>();
    private PushFilter successor;

    @Override
    public void setSuccessor(PushFilter successor) {
        this.successor = successor;
    }

    @Override
    public void push(Face f) {
        faceBuffer.add(f); // Collect all incoming faces
    }

    public void flush() {
        faceBuffer.sort(Comparator.comparingDouble(this::averageZ).reversed()); // sort back-to-front (descending z)

        for (Face f : faceBuffer) {
            successor.push(f);
        }

        faceBuffer.clear();
    }

    private double averageZ(Face f) {
        return (f.getV1().getZ() + f.getV2().getZ() + f.getV3().getZ()) / 3.0;
    }
}
