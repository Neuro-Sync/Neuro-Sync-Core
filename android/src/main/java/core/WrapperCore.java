package core;

import Wheelchair.WheelchairController;
import ai.ModelController;
import android.bluetooth.BluetoothManager;
import android.hardware.usb.UsbManager;
import android.util.Log;
import com.example.wrappercore.control.ControlManager;
import com.example.wrappercore.control.IControlManagerEventListener;
import headset.HeadsetController;
import headset.events.IHeadsetListener;
import java.io.IOException;
import java.util.EventListener;

public class WrapperCore {

  private final HeadsetController headsetController;
  private final ControlManager controlManager;
  private final ModelController modelController;
  //FIXME: put the correct PROD url
  private final String modelUrl = "https://learny-v1.onrender.com/api/v1/downloadModel";

  //FIXME: This field is not initialized with null this just to mimic the real implementation []
  //FIXME: This field should be made as final []
  private WheelchairController wheelchairController = null;

  //NOTE: This constructor is not the production one it is for testing purposes only
  public WrapperCore() {
    this.controlManager = new ControlManager();
    this.headsetController = new HeadsetController();
    this.modelController = new ModelController(modelUrl);
//    this.wheelchairController = new WheelchairController();
    this.headsetController.addEventListener(controlManager.getBlinkManager());
    this.headsetController.addEventListener(this.modelController);
  }

  //FIXME: This constructor is missing the serial usb connection initialization [DONE]
  public WrapperCore(BluetoothManager bluetoothManager, String macAddress, UsbManager usbManager)
      throws IOException {
    this.controlManager = new ControlManager();
    this.wheelchairController = new WheelchairController(usbManager);
    //FIXME: put the correct PROD url
    this.modelController = new ModelController(this.modelUrl);
    this.modelController.addListener(this.controlManager.getActionManager());
    this.headsetController = new HeadsetController(bluetoothManager, macAddress);
//    this.headsetController.connect();
//    this.headsetController.addEventListener(this.controlManager.getBlinkManager());
    this.headsetController.addEventListener(this.modelController);
  }

  public void addListener(EventListener listener) {
    if (listener instanceof IControlManagerEventListener) {
      controlManager.addListener((IControlManagerEventListener) listener);
    } else if (listener instanceof IHeadsetListener) {
      headsetController.addEventListener(listener);
    }
  }

  public void removeListener(EventListener listener) {
    if (listener instanceof IControlManagerEventListener) {
      controlManager.removeListener((IControlManagerEventListener) listener);
    } else if (listener instanceof IHeadsetListener) {
      headsetController.removeEventListener(listener);
    }
  }

  public void headsetConnect() {
    headsetController.connect();
  }

  public void headsetDisconnect() {
    headsetController.disconnect();
  }

  public void makeWheelchairGoForward() {
    wheelchairController.forward();
  }

  public void makeWheelchairGoLeft() {
    wheelchairController.left();
  }

  public void makeWheelchairGoRight() {
    wheelchairController.right();
  }

  public void makeWheelchairStop() {
    wheelchairController.stop();
  }

  //FIXME: This method is for testing purposes only
  public HeadsetController getMindWaveMobile2() {
    return headsetController;
  }

  //FIXME: This method is for testing purposes only
  public ControlManager getControlManager() {
    return controlManager;
  }

  //FIXME: This method is for testing purposes only
  public ModelController getModelController() {
    return modelController;
  }

}
