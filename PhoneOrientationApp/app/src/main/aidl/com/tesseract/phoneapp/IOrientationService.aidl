// IOrientationService.aidl
package com.tesseract.phoneapp;
import com.tesseract.phoneapp.IAidlCbListener;
// Declare any non-default types here with import statements

interface IOrientationService {
  float [] getRotationVectorInfo();
  void registerCallback(IAidlCbListener cb);
  void unregisterCallback(IAidlCbListener cb);

}
