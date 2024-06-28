package core;

import Wheelchair.WheelchairController;
import ai.ModelController;
import android.app.PendingIntent;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.util.Log;
import com.example.wrappercore.control.ControlManager;
import com.example.wrappercore.control.IControlManagerEventListener;
import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialProber;
import headset.HeadsetController;
import headset.events.IHeadsetListener;
import java.io.IOException;
import java.util.EventListener;
import java.util.List;
import java.util.Map;

public class WrapperCore {

  private static final String ACTION_USB_PERMISSION = "com.example.core.USB_PERMISSION";

  private static UsbSerialDriver driver = null;
  private final HeadsetController headsetController;
  private final ControlManager controlManager;
  private final ModelController modelController;
  //FIXME: put the correct PROD url
  private final String modelUrl = "https://learny-v1.onrender.com/api/v1/downloadModel";

  //FIXME: This field is not initialized with null this just to mimic the real implementation []
  //FIXME: This field should be made as final []
  private WheelchairController wheelchairController = null;

  public static boolean initPermission(UsbManager usbManager, Context context) {
    List<UsbSerialDriver> availableDrivers = UsbSerialProber.getDefaultProber()
        .findAllDrivers(usbManager);
    Log.i("HARDWARE", "Available Drivers: " + availableDrivers.size());
    if (availableDrivers.isEmpty()) {
      return false;
    }

    Map<String, UsbDevice> diverList = usbManager.getDeviceList();
    UsbDevice mDevice;
    for (UsbDevice device : diverList.values()) {
      mDevice = device;
      getBtAccess(mDevice, usbManager, context);
      Log.i("HARDWARE",
          "Device Name: " + device.getDeviceName() + " Device ID: " + device.getDeviceId()
              + " Vendor: "
              + device.getVendorId() + " Product: " + device.getProductId());
    }

    driver = availableDrivers.get(0);
    return true;
  }


  private static void getBtAccess(UsbDevice device, UsbManager manager, Context context) {
    // Create a PendingIntent for USB permission request
    PendingIntent permissionIntent = PendingIntent.getBroadcast(context, 0,
        new Intent(ACTION_USB_PERMISSION),
        PendingIntent.FLAG_IMMUTABLE);

    // Create a BroadcastReceiver to handle USB permission
    BroadcastReceiver usbReceiver = new BroadcastReceiver() {
      @Override
      public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (ACTION_USB_PERMISSION.equals(action)) {
          synchronized (this) {
            UsbDevice usbDevice = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
            if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
              if (usbDevice != null && usbDevice.equals(device)) {
                // Permission granted, proceed with opening the USB device
                UsbDeviceConnection connection = manager.openDevice(device);
                if (connection != null) {
                  // Device opened successfully, perform further operations
                  Log.i("HARDWARE", "Device opened successfully");
                  // Here you can perform USB communication or other tasks
//                  asyncSendBtPackets(device, manager);
//                  sendBtPackets(device, manager);
                }
              }
            } else {
              // Permission denied for the USB device
              Log.e("HARDWARE", "Permission denied for USB device: " + device.getDeviceName());
              // Handle the permission denial if needed
            }
          }
        }
      }
    };

    // Register the BroadcastReceiver to handle USB permission
    IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
    filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY); // Set priority to high
    context.registerReceiver(usbReceiver, filter); // Add permission flag
//    if (VERSION.SDK_INT >= VERSION_CODES.O) {
//    }
    // Request USB permission for the device
    manager.requestPermission(device, permissionIntent);
  }


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
  public WrapperCore(BluetoothManager bluetoothManager, String macAddress, UsbManager usbManager,
      Context context)
      throws IOException {
    this.controlManager = new ControlManager();
    try {
      this.wheelchairController = new WheelchairController(usbManager, driver);
    } catch (Exception e) {
      Log.e("ERROR", "Error in  WheelchairController initialization" + e.getMessage());
    }
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
