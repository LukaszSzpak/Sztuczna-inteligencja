import random

from direction import Direction
from segment import *


class Path:
    def __init__(self, start_point, end_point):
        self.start_point = start_point
        self.end_point = end_point
        self.actual_point = start_point
        self.segments_list = []

    def add_random_segment(self, available_directions):
        new_segment = Segment(random.choice(available_directions), 1)

        if len(self.segments_list) > 0:
            if self.segments_list[-1].is_the_same_direction(new_segment):
                self.segments_list[-1].add_length(new_segment)
            else:
                self.segments_list.append(new_segment)
        else:
            self.segments_list.append(new_segment)

        self.add_to_actual_point(new_segment)

    def is_done(self):
        return self.actual_point == self.end_point

    def add_to_actual_point(self, added_path):
        if added_path.direction == Direction.up or added_path.direction == Direction.down:
            self.actual_point[0] += added_path.direction.value
        else:
            self.actual_point[1] += added_path.direction.value
