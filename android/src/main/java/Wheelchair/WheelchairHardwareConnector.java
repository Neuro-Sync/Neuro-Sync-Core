package Wheelchair;

import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialPort;
import com.hoho.android.usbserial.driver.UsbSerialProber;
import java.io.IOException;
import java.util.List;

public class WheelchairHardwareConnector {

  private UsbSerialPort port;

  public WheelchairHardwareConnector(UsbManager usbManager) throws IOException {
    List<UsbSerialDriver> availableDrivers = UsbSerialProber.getDefaultProber().findAllDrivers(usbManager);
    if (availableDrivers.isEmpty()) {
      return;
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

  public void send(byte direction) {
    try {
      this.port.write(new byte[]{direction}, 1000);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
