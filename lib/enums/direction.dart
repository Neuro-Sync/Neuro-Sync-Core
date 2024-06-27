enum Direction {
  stop(0, 'Stop'),
  left(1, 'Left'),
  forward(2, 'Forward'),
  right(3, 'Right');

  final int value;
  final String name;
  const Direction(this.value, this.name);
  factory Direction.fromValue(int value) =>
      values.firstWhere((element) => element.value == value);
}
