package at.fhv.sysarch.lab3.pipeline.pull;

import at.fhv.sysarch.lab3.obj.Face;
import java.util.Comparator;
import java.util.LinkedList;

public class PullDepthSortingFilter implements PullFilter<Face> {

    private final LinkedList<Face> faceBuffer = new LinkedList<>();
    private final PullFilter<Face> source;

    public PullDepthSortingFilter(PullFilter<Face> source) {
        this.source = source;
    }

    @Override
    public Face pull() {
        return faceBuffer.removeFirst();
    }

    @Override
    public Boolean hasNext() {
        if (source.hasNext() && faceBuffer.isEmpty()) {
            sortFaceBuffer();
        }
        return !faceBuffer.isEmpty();
    }

    public void sortFaceBuffer() {
        while (source.hasNext()) {
            Face f = source.pull();
            if (f != null) {
                faceBuffer.add(f);
            }
        }
        faceBuffer.sort(Comparator.comparing(f -> f.getV1().getZ() + f.getV2().getZ() + f.getV3().getZ()));
    }
}
