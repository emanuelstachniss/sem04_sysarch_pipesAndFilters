package at.fhv.sysarch.lab3.pipeline.pull;

import at.fhv.sysarch.lab3.pipeline.push.PushFilter;

public interface PullFilter<R> {

    R pull();

    Boolean hasNext();
}
