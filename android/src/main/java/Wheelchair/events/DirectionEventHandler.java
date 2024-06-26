package Wheelchair.events;

import headset.events.IEventHandler;
import java.util.ArrayList;

public class DirectionEventHandler implements IEventHandler<IDirectionEventListener, DirectionEvent> {

  private final ArrayList<IDirectionEventListener> listeners = new ArrayList<>();

  public void addListener(IDirectionEventListener listener) {
    listeners.add(listener);
  }

  public void removeListener(IDirectionEventListener listener) {
    listeners.remove(listener);
  }

  //TODO: This method is not used in the app for testing purposes only
  public boolean containsListener(IDirectionEventListener listener) {
    return listeners.contains(listener);
  }

  public void fireEvent(DirectionEvent event) {
    for (IDirectionEventListener listener : listeners) {
      listener.onDirectionEvent(event);
    }
  }
}