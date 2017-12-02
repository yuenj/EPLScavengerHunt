package com.cmput401f17.eplscavengerhunt.custom;

import com.cmput401f17.eplscavengerhunt.controller.LocationController;

/**
 * Used to get a return value from an inner class
 * https://stackoverflow.com/questions/42128909/return-value-from-valueeventlistener-java 06/10/2017
 * @see LocationController
 */
public interface SimpleCallback<T> {
    void callback(T data);
}
