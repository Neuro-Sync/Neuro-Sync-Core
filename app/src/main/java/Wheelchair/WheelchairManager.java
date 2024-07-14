package Wheelchair;

import Wheelchair.events.DirectionEvent;
import Wheelchair.events.IDirectionEventListener;
import android.content.Context;
import android.hardware.usb.UsbManager;
import java.io.IOException;

public class WheelchairManager implements IDirectionEventListener {

  private WheelchairHardwareConnector wheelchairHardwareConnector = null;

  public WheelchairManager(UsbManager usbManager, Context context) throws IOException {
    this.wheelchairHardwareConnector = new WheelchairHardwareConnector(usbManager, context);
//    this. = new WheelchairHardwareConnector(usbManager, context);
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
