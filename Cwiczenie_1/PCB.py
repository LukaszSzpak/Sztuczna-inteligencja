import numpy as np

from Cwiczenie_1.path import Path


class PCB:
    def __init__(self, points_list, width, height):
        self.path_list = []
        self.points_list = points_list
        self.height = height
        self.width = width
        self.matrix = np.zeros([width, height], dtype=int)
        self.score = None

    def calculate_and_get_quality(self):
        pass  # TODO: implement fitness function

    def make_random_solution(self):
        self.add_points_to_matrix()
        self.add_points_to_paths()
        pass  # TODO: implement random solution method

    def add_points_to_matrix(self):
        for x, y in self.points_list:
            self.matrix[x, y] = 1

    def add_points_to_paths(self):
        for start, end in zip(self.points_list[0::2], self.points_list[1::2]):
            self.path_list.append(Path(start, end))
