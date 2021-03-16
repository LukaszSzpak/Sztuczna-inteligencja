from Cwiczenie_1.direction import Direction


class Segment:
    def __init__(self, direction, length):
        self.direction = direction
        self.length = length

    def is_the_same_direction(self, segment):
        return self.direction == segment.direction

    def add_length(self, segment):
        self.length += segment.length

    def get_segment_with_opposite_direction(self):
        if self.direction == Direction.up:
            return Segment(Direction.down, 1)
        if self.direction == Direction.down:
            return Segment(Direction.up, 1)

        if self.direction == Direction.left:
            return Segment(Direction.right, 1)
        if self.direction == Direction.right:
            return Segment(Direction.left, 1)
