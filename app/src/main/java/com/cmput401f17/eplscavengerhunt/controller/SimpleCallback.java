package com.cmput401f17.eplscavengerhunt.controller;

// Used to get a return value from an inner class, check LocationController
// https://stackoverflow.com/questions/42128909/return-value-from-valueeventlistener-java 06/10/2017
public interface SimpleCallback<T> {
    void callback(T data);
}
