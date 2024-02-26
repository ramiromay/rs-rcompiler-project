package com.realssoft.rcompiler.ui.values.observers;

public interface IEventObservable<T>
{

    void addObserver(IEventObserver<T> iEventObserver);
    void notifyEvent();

}
