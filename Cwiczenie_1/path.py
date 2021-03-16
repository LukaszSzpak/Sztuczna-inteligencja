import random
import copy

from Cwiczenie_1.direction import Direction
from Cwiczenie_1.segment import *


class Path:
    def __init__(self, start_point, end_point):
        self.start_point = start_point
        self.end_point = end_point
        self.actual_point = copy.deepcopy(start_point)
        self.segments_list = []

    def make_random_path(self):
        directions = []
        while not self.is_done():
            if self.end_point[0] - self.actual_point[0] > 0:
                directions.append(Direction.right)
            elif self.end_point[0] - self.actual_point[0] < 0:
                directions.append(Direction.left)

            if self.end_point[1] - self.actual_point[1] > 0:
                directions.append(Direction.up)
            elif self.end_point[1] - self.actual_point[1] < 0:
                directions.append(Direction.down)

            self.add_random_segment(directions)

    def add_random_segment(self, available_directions):
        if not self.check_next_to_end():
            new_segment = Segment(random.choice(available_directions), 1)
            self.add_segment(new_segment)

    def add_segment(self, new_segment):
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
            self.actual_point[1] += added_path.direction.value
        else:
            self.actual_point[0] += added_path.direction.value + 10

    def check_next_to_end(self):
        if self.actual_point[0] + 1 == self.end_point[0] and self.actual_point[1] == self.end_point[1]:
            self.add_segment(Segment(Direction.right, 1))
            return True
        if self.actual_point[0] - 1 == self.end_point[0] and self.actual_point[1] == self.end_point[1]:
            self.add_segment(Segment(Direction.left, 1))
            return True
        if self.actual_point[1] + 1 == self.end_point[1] and self.actual_point[0] == self.end_point[0]:
            self.add_segment((Segment(Direction.up, 1)))
            return True
        if self.actual_point[1] - 1 == self.end_point[1] and self.actual_point[0] == self.end_point[0]:
            self.add_segment((Segment(Direction.down, 1)))
            return True
        return False

    def get_sum_of_segments(self):
        return sum(seg.length for seg in self.segments_list) + 1
