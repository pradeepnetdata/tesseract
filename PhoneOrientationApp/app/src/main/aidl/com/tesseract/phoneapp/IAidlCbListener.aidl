// IAidlCbListener.aidl
package com.tesseract.phoneapp;

// Declare any non-default types here with import statements

interface IAidlCbListener {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void valueChanged(out float [] value);
}
