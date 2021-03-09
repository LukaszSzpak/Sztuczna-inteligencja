class PCB:
    def __init__(self, points_list, width, height):
        self.path_list = []
        self.points_list = points_list
        self.height = height
        self.width = width
        self.score = None

    def calculate_and_get_quality(self):
        pass  # TODO: implement fitness function

    def make_random_solution(self):
        pass  # TODO: implement random solution method
