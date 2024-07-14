package Wheelchair;

import Wheelchair.events.Direction;
import Wheelchair.events.DirectionEvent;
import Wheelchair.events.DirectionEventHandler;
import Wheelchair.events.IDirectionEventListener;
import android.content.Context;
import android.hardware.usb.UsbManager;
import java.io.IOException;

public class WheelchairController {

  private final DirectionEventHandler directionEventHandler = new DirectionEventHandler();

  public WheelchairController(UsbManager usbManager, Context context) throws IOException {
    WheelchairManager wheelchairManager = new WheelchairManager(usbManager, context);
    this.directionEventHandler.addListener(wheelchairManager);
  }

  public void forward() {
    this.directionEventHandler.fireEvent(new DirectionEvent(this, Direction.FORWARD));
  }

  public void left() {
    this.directionEventHandler.fireEvent(new DirectionEvent(this, Direction.LEFT));
  }

  public void right() {
    this.directionEventHandler.fireEvent(new DirectionEvent(this, Direction.RIGHT));
  }

  public void stop() {
    this.directionEventHandler.fireEvent(new DirectionEvent(this, Direction.STOP));
  }


  //TODO: This method is not used in the app for testing purposes only
  public void addDirectionListener(IDirectionEventListener listener) {
    directionEventHandler.addListener(listener);
  }

  //TODO: This method is not used in the app for testing purposes only
  public void removeDirectionListener(IDirectionEventListener listener) {
    directionEventHandler.removeListener(listener);
  }

}
