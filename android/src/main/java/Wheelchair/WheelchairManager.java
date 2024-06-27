package Wheelchair;

import android.content.Context;
import Wheelchair.events.DirectionEvent;
import Wheelchair.events.IDirectionEventListener;
import android.hardware.usb.UsbManager;
import com.hoho.android.usbserial.driver.UsbSerialDriver;
import java.io.IOException;

public class WheelchairManager implements IDirectionEventListener {

  private final WheelchairHardwareConnector wheelchairHardwareConnector;

  public WheelchairManager(UsbManager usbManager, UsbSerialDriver driver) throws IOException {
    this.wheelchairHardwareConnector = new WheelchairHardwareConnector(usbManager, driver);
  }

  public void onDirectionEvent(DirectionEvent event) {
    switch (event.getDirection()) {
      case FORWARD -> this.wheelchairHardwareConnector.send("forward".getBytes());
      case LEFT -> this.wheelchairHardwareConnector.send("left".getBytes());
      case RIGHT -> this.wheelchairHardwareConnector.send("right".getBytes());
      case STOP -> this.wheelchairHardwareConnector.send("stop".getBytes());
    }
  }

}
