import numpy as np

from Cwiczenie_1.direction import Direction
from Cwiczenie_1.path import Path


class PCB:
    def __init__(self, points_list, width, height):
        self.path_list = None
        self.points_list = points_list
        self.height = height
        self.width = width
        self.matrix = None
        self.score = None
        self.act_number = 1

    def calculate_and_get_quality(self):
        pass  # TODO: implement fitness function

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

        print(self.matrix)
        return True

    def erase_data(self):
        self.matrix = np.zeros([self.width, self.height], dtype=int)
        self.path_list = []

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
