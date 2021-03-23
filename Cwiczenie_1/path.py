import random
import copy

from Cwiczenie_1.direction import Direction
from Cwiczenie_1.segment import *


class Path:
    ADD_LENGTH_LEVEl = 30  # 1 - 100
    SPLIT_LEVEL = 30  # 1 - 100
    ADD_OPPOSITE = 30  # 1 - 100
    SUB_OPPOSITE = 80  # 1 - 100
    MAX_LENGTH_OF_NEW_SEGMENT = 5

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
        if random.randint(0, 100) < Path.SPLIT_LEVEL:
            pointer = random.randint(0, len(self.segments_list) - 1)
            segment = self.segments_list[pointer]
            if segment.length > 1:
                seg = Segment(segment.direction, random.randint(1, segment.length))
                segment.length -= seg.length
                self.segments_list.insert(random.randint(0, len(self.segments_list) - 1), seg)

        if random.randint(0, 100) < Path.ADD_OPPOSITE:
            length = random.randint(0, Path.MAX_LENGTH_OF_NEW_SEGMENT)
            chance = random.randint(0, 100)
            if chance < 25:
                self.add_two_opposite_segments(length, Direction.up, Direction.down)
            elif chance < 50:
                self.add_two_opposite_segments(length, Direction.down, Direction.up)
            elif chance < 75:
                self.add_two_opposite_segments(length, Direction.left, Direction.right)
            elif chance < 100:
                self.add_two_opposite_segments(length, Direction.right, Direction.left)

        if random.randint(0, 100) < Path.SUB_OPPOSITE:
            segment = self.segments_list[random.randint(0, len(self.segments_list) - 1)]

            if segment.direction == Direction.up:
                self.sub_length_opposite_directions(segment, Direction.down)
            elif segment.direction == Direction.down:
                self.sub_length_opposite_directions(segment, Direction.up)
            elif segment.direction == Direction.left:
                self.sub_length_opposite_directions(segment, Direction.right)
            elif segment.direction == Direction.right:
                self.sub_length_opposite_directions(segment, Direction.left)

            self.rebuild_path()

    def add_two_opposite_segments(self, length, direction1, direction2):
        self.segments_list.insert(random.randint(0, len(self.segments_list) - 1), Segment(direction1, length))
        self.segments_list.insert(random.randint(0, len(self.segments_list) - 1), Segment(direction2, length))

    def sub_length_opposite_directions(self, segment, direction):
        for seg in self.segments_list:
            if seg.direction == direction:
                segment.length -= 1
                seg.length -= 1
                return

    def rebuild_path(self):
        for index in reversed(range((len(self.segments_list) - 1))):
            if len(self.segments_list) > 1:
                if self.segments_list[index].length == 0:
                    del self.segments_list[index]
                    return
                elif self.segments_list[index].direction == self.segments_list[index + 1].direction:
                    self.segments_list[index].length += self.segments_list[index + 1].length
                    del self.segments_list[index + 1]
                    return
                elif self.segments_list[index].direction == Direction.up and \
                        self.segments_list[index + 1].direction == Direction.down:
                    self.check_lengths_and_delete(index)
                    return
                elif self.segments_list[index].direction == Direction.down and \
                        self.segments_list[index + 1].direction == Direction.up:
                    self.check_lengths_and_delete(index)
                    return
                elif self.segments_list[index].direction == Direction.left and \
                        self.segments_list[index + 1].direction == Direction.right:
                    self.check_lengths_and_delete(index)
                    return
                elif self.segments_list[index].direction == Direction.right and \
                        self.segments_list[index + 1].direction == Direction.left:
                    self.check_lengths_and_delete(index)
                    return

    def check_lengths_and_delete(self, index):
        if self.segments_list[index].length == self.segments_list[index + 1].length:
            del self.segments_list[index]
            del self.segments_list[index]

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
