package at.fhv.sysarch.lab3.pipeline.push;

public interface PushFilter<I, O> {

    void setSuccessor(PushFilter<O, ?> successor); //Output O == I on next filter

    void push(I f); //Input
}
