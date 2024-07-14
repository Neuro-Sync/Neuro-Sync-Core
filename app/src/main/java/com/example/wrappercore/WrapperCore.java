package com.example.wrappercore;

import Wheelchair.WheelchairController;
import ai.ModelController;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.hardware.usb.UsbManager;
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
  private final WheelchairController wheelchairController;
  private final String modelUrl = "https://learny-v1.onrender.com/api/v1/downloadModel";


  public WrapperCore(BluetoothManager bluetoothManager, String macAddress, UsbManager usbManager)
      throws IOException {
    this.controlManager = new ControlManager();
    this.wheelchairController = new WheelchairController(usbManager);
    this.modelController = new ModelController(this.modelUrl);
    this.modelController.addListener(this.controlManager.getActionManager());
    this.headsetController = new HeadsetController(bluetoothManager, macAddress);
    this.headsetController.connect();
    this.headsetController.addEventListener(this.controlManager.getBlinkManager());
    this.headsetController.addEventListener(this.modelController);
  }

  public void addListener(EventListener listener) {
    if (listener instanceof IControlManagerEventListener) {
      controlManager.addListener(listener);
    } else if (listener instanceof IHeadsetListener) {
      headsetController.addEventListener(listener);
    }
  }

  public void removeListener(EventListener listener) {
    if (listener instanceof IControlManagerEventListener) {
      controlManager.removeListener(listener);
    } else if (listener instanceof IHeadsetListener) {
      headsetController.removeEventListener(listener);
    }
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
}
