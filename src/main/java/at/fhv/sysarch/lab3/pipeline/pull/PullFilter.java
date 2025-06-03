package at.fhv.sysarch.lab3.pipeline.pull;

import at.fhv.sysarch.lab3.pipeline.push.PushFilter;

public interface PullFilter<R> {

    void setSource(PullFilter<R> source);

    R pull();

    Boolean hasNext();
}
