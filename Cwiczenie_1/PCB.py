from math import inf
from operator import add

import numpy as np

from Cwiczenie_1.direction import Direction
from Cwiczenie_1.path import Path


class PCB:
    LENGTH_MULTIPLIER = 1.0
    CROSS_MULTIPLIER = 5.0
    OUT_OF_PCB_MULTIPLIER = 10.0
    ERROR_COST = float(inf)
    MATRIX_OVERLAP = 10  # must be %2=0

    def __init__(self, points_list, width, height):
        self.path_list = None
        self.points_list = points_list
        self.height = height
        self.width = width
        self.matrix = None
        self.score = None
        self.act_number = 1

    def calculate_and_get_quality(self):
        if not self.path_list:
            return None

        length_cost = sum(path.get_sum_of_segments() for path in self.path_list) * PCB.LENGTH_MULTIPLIER
        crossing_cost, out_pcb_cost = self.check_crossing_and_out_pcb()
        crossing_cost *= PCB.CROSS_MULTIPLIER
        out_pcb_cost *= PCB.OUT_OF_PCB_MULTIPLIER
        self.score = length_cost + crossing_cost + out_pcb_cost
        return self.score

    def check_crossing_and_out_pcb(self):
        self.matrix = np.zeros([self.width + PCB.MATRIX_OVERLAP, self.height + PCB.MATRIX_OVERLAP], dtype=int)
        cross_counter = 0
        out_counter = 0
        pcb_overlap_half = int(PCB.MATRIX_OVERLAP / 2)
        act_counter = 1

        for path in self.path_list:
            act_point = list(map(add, path.start_point, [pcb_overlap_half, pcb_overlap_half]))

            for segment in path.segments_list:
                for move in range(segment.length):
                    if act_point[0] < pcb_overlap_half or act_point[0] > pcb_overlap_half + self.width:
                        out_counter += 1
                    if act_point[1] < pcb_overlap_half or act_point[1] > pcb_overlap_half + self.height:
                        out_counter += 1

                    if segment.direction == Direction.up or segment.direction == Direction.down:
                        act_point[1] += segment.direction.value
                    else:
                        act_point[0] += segment.direction.value + 10
                    try:
                        if self.matrix[act_point[0]][act_point[1]] != 0:
                            cross_counter += 1
                        self.matrix[act_point[0]][act_point[1]] = act_counter
                    except IndexError:
                        return PCB.ERROR_COST, PCB.ERROR_COST

            self.matrix[path.end_point[0] + pcb_overlap_half][path.end_point[1] + pcb_overlap_half] = act_counter
            self.matrix[path.start_point[0] + pcb_overlap_half][path.start_point[1] + pcb_overlap_half] = act_counter
            act_counter += 1

        return cross_counter, out_counter

    def make_random_simple_solution(self):
        self.erase_data()
        self.add_points_to_matrix()
        self.add_points_to_paths()

        for path in self.path_list:
            path.make_random_path()

    def make_random_solution(self):
        self.erase_data()
        self.add_points_to_matrix()
        self.add_points_to_paths()
        self.act_number = 1

        for path in self.path_list:
            while not path.is_done():
                available_directions = self.get_available_directions(path.actual_point[0], path.actual_point[1])

                if len(available_directions) == 0:
                    return False

                path.add_random_segment(available_directions)
                self.add_point_to_matrix(path.actual_point)
            self.act_number += 1
        return True

    def erase_data(self):
        self.matrix = np.zeros([self.width, self.height], dtype=int)
        self.path_list = []
        self.score = None

    def add_points_to_matrix(self):
        self.act_number = 1
        for p1, p2 in zip(self.points_list[0::2], self.points_list[1::2]):
            self.matrix[p1[0], p1[1]] = self.act_number
            self.matrix[p2[0], p2[1]] = self.act_number
            self.act_number += 1

    def add_point_to_matrix(self, point):
        self.matrix[point[0], point[1]] = self.act_number

    def add_points_to_paths(self):
        for start, end in zip(self.points_list[0::2], self.points_list[1::2]):
            self.path_list.append(Path(start, end))

    def get_available_directions(self, x_point, y_point):
        directions_list = []

        if x_point > 0 and self.matrix[x_point - 1, y_point] == 0:
            directions_list.append(Direction.left)

        if x_point < self.width - 1 and self.matrix[x_point + 1, y_point] == 0:
            directions_list.append(Direction.right)

        if y_point > 0 and self.matrix[x_point, y_point - 1] == 0:
            directions_list.append(Direction.down)

        if y_point < self.height - 1 and self.matrix[x_point, y_point + 1] == 0:
            directions_list.append(Direction.up)

        return directions_list
