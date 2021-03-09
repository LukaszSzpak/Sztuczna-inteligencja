class Segment:
    def __init__(self, direction, length):
        self.direction = direction
        self.length = length

    def is_the_same_direction(self, segment):
        return self.direction == segment.direction

    def add_length(self, segment):
        self.length += segment.length
