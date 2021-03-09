class DataLoading:
    def __init__(self, file_path):
        self.file_path = file_path

    def get_data(self):
        file = open(self.file_path, "r")

        dimension = file.readline().split(";")
        points_list = []

        for line in file:
            line_list = list(map(int, line.split(";")))
            points_list.append(line_list[:2])
            points_list.append(line_list[2:])

        return list(map(int, dimension)), points_list
