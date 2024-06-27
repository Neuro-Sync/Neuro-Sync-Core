package Wheelchair;

import Wheelchair.events.DirectionEvent;
import Wheelchair.events.IDirectionEventListener;
import android.hardware.usb.UsbManager;
import java.io.IOException;

public class WheelchairManager implements IDirectionEventListener {

  private final WheelchairHardwareConnector wheelchairHardwareConnector;

  public WheelchairManager(UsbManager usbManager) throws IOException {
    this.wheelchairHardwareConnector = new WheelchairHardwareConnector(usbManager);
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
