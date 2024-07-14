package Wheelchair;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.util.Log;
import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialPort;
import com.hoho.android.usbserial.driver.UsbSerialProber;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class WheelchairHardwareConnector {

  private static final String ACTION_USB_PERMISSION = "com.example.wrapper.USB_PERMISSION";


  private UsbSerialPort port;

  public WheelchairHardwareConnector() {

  }

  public WheelchairHardwareConnector(UsbManager usbManager, Context context) throws IOException {
    List<UsbSerialDriver> availableDrivers = UsbSerialProber.getDefaultProber().findAllDrivers(usbManager);
    if (availableDrivers.isEmpty()) {
      return;
    }

    Map<String, UsbDevice> diverList = usbManager.getDeviceList();
    UsbDevice mDevice;
    for (UsbDevice device : diverList.values()) {
      mDevice = device;
      getBtAccess(mDevice, usbManager, context);
      Log.i("HARDWARE",
          "Device Name: " + device.getDeviceName() + " Device ID: " + device.getDeviceId() + " Vendor: "
              + device.getVendorId() + " Product: " + device.getProductId());
    }

    // Open a connection to the first available driver.
    UsbSerialDriver driver = availableDrivers.get(0);
    UsbDeviceConnection connection = usbManager.openDevice(driver.getDevice());
    if (connection == null) {
      //TODO: handle this case
      // add UsbManager.requestPermission(driver.getDevice(), ..) handling here
      return;
    }

    this.port = driver.getPorts().get(0);
    this.port.open(connection);
    this.port.setParameters(9600, 8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE);

  }

  public void send(byte[] direction) {
    try {
      byte[] responseLen = new byte[]{};
      this.port.write(direction, 1000);
      this.port.read(responseLen, 1000);
      Log.i("HARDWARE", "response:" + responseLen);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }


  private void getBtAccess(UsbDevice device, UsbManager manager, Context context) {
    // Create a PendingIntent for USB permission request
    PendingIntent permissionIntent = PendingIntent.getBroadcast(context, 0, new Intent(ACTION_USB_PERMISSION),
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


}
