package at.fhv.sysarch.lab3.pipeline.pull;

import at.fhv.sysarch.lab3.obj.Face;

public class PullBackfaceCullingFilter implements PullFilter<Face> {

    private Face nextFace = null;
    private final PullFilter<Face> source;

    public PullBackfaceCullingFilter(PullFilter<Face> source) {
        this.source = source;
    }

    @Override
    public Face pull() {
        prepareNext();
        var temp = nextFace;
        nextFace = null;
        return temp;
    }

    @Override
    public Boolean hasNext() {
        prepareNext();
        return nextFace != null;
    }

    private void prepareNext() {
        while (source.hasNext() && nextFace == null) {
            Face f = source.pull();
            if (f.getV1().dot(f.getN1()) < 0) {
                nextFace = f;
            }
        }
    }
}
