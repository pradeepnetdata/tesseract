// IOrientationService.aidl
package com.tesseract.phoneapp;
import com.sample.aidl.ICallBack;
// Declare any non-default types here with import statements

interface IOrientationService {
  float [] getRotationVectorInfo();
  void registerCallback(ICallback cb);
  void unregisterCallback(ICallback cb);
}
