package headset.events.headsetStateChange;


import headset.events.IHeadsetListener;

public interface IHeadsetStateChangeEventListener extends IHeadsetListener {

  void onHeadsetStateChange(HeadsetStateChangeEvent event);
}
