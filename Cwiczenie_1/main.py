import copy

from Cwiczenie_1.PCB import PCB
from Cwiczenie_1.dataLoading import DataLoading

COUNTER = 1000

if __name__ == '__main__':
    data_load = DataLoading("lab1_problemy_testowe/zad1.txt")
    data = data_load.get_data()
    pcb = PCB(data[1], data[0][0], data[0][1])

    # best_score = 9999
    # best_solution = None
    # for i in range(COUNTER):
    #     if pcb.make_random_solution():
    #         temp_score = pcb.calculate_and_get_quality()
    #         if temp_score < best_score:
    #             best_score = temp_score
    #             best_solution = copy.deepcopy(pcb)
    # print(best_solution.matrix)
    # print(best_score)

    # while not pcb.make_random_solution():
    #     pcb.calculate_and_get_quality()
    # print(pcb.matrix)
    # print(pcb.calculate_and_get_quality())

    pcb.make_random_simple_solution()
    print(pcb.calculate_and_get_quality())
    print("Done")
