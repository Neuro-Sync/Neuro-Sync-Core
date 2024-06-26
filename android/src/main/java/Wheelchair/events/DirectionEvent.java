package Wheelchair.events;

public class DirectionEvent extends java.util.EventObject {

  private final Direction direction;

  public DirectionEvent(Object source, Direction direction) {
    super(source);
    this.direction = direction;
  }

  public Direction getDirection() {
    return direction;
  }

  @Override
  public String toString() {
    return "DirectionEvent [direction=" + direction + "]";
  }

}
