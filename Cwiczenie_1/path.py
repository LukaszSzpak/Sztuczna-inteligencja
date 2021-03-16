import random
import copy

from Cwiczenie_1.direction import Direction
from Cwiczenie_1.segment import *


class Path:
    ADD_LENGTH_LEVEl = 70  # 1 - 100

    def __init__(self, start_point, end_point):
        self.start_point = start_point
        self.end_point = end_point
        self.actual_point = copy.deepcopy(start_point)
        self.segments_list = []

    def make_random_path(self):
        while not self.is_done():
            directions = []
            if self.end_point[0] - self.actual_point[0] > 0:
                directions.append(Direction.right)
            elif self.end_point[0] - self.actual_point[0] < 0:
                directions.append(Direction.left)

            if self.end_point[1] - self.actual_point[1] > 0:
                directions.append(Direction.up)
            elif self.end_point[1] - self.actual_point[1] < 0:
                directions.append(Direction.down)

            self.add_random_segment(directions)

    def mutate(self):
        pointer1 = random.randint(0, len(self.segments_list) - 1)
        pointer2 = random.randint(0, len(self.segments_list) - 1)
        segment = self.segments_list[pointer1]

        if random.randint(0, 100) < Path.ADD_LENGTH_LEVEl:
            segment.length += 1
            self.segments_list.insert(pointer2, segment.get_segment_with_opposite_direction())
            self.rebuild_path()
        else:
            if segment.direction == Direction.up:
                self.sub_length_opposite_directions(segment, Direction.down)
            elif segment.direction == Direction.down:
                self.sub_length_opposite_directions(segment, Direction.up)
            elif segment.direction == Direction.left:
                self.sub_length_opposite_directions(segment, Direction.right)
            elif segment.direction == Direction.right:
                self.sub_length_opposite_directions(segment, Direction.left)
            self.rebuild_path()

    def sub_length_opposite_directions(self, segment, direction):
        for seg in self.segments_list:
            if seg.direction == direction:
                segment.length -= 1
                seg.length -= 1

    def rebuild_path(self):
        for index in range(start=0, stop=len(self.segments_list) - 1, step=1):
            if self.segments_list[index].length == 0:
                del self.segments_list[index]
                self.rebuild_path()

            if self.segments_list[index].direction == self.segments_list[index + 1].direction:
                self.segments_list[index].length += self.segments_list[index + 1].length
                del self.segments_list[index + 1]
                self.rebuild_path()

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
