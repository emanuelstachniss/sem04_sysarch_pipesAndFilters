package at.fhv.sysarch.lab3.pipeline.filter;

import at.fhv.sysarch.lab3.obj.Face;

public interface PushFilter<I, O> {

    void setSuccessor(PushFilter<O, ?> successor); //Output O == I on next filter

    void push(I f); //Input
}
