import random
import numpy as np

from Cwiczenie_1.PCB import PCB
from Cwiczenie_1.dataLoading import DataLoading

COUNTER = 100
PCB_POPULATION = 5
CROSSOVER_POPULATION = 20
FIRST_POPULATION = 10


def roulette(pcb_list):
    max_cost = 0
    for pcb, cost in pcb_list:
        max_cost = max(cost, max_cost)

    reversed_pcbs = []
    cost_length = 0
    for pcb, cost in pcb_list:
        reversed_pcbs.append((pcb, max_cost - cost + 1))
        cost_length += max_cost - cost + 1

    result_pcbs = []
    for index in range(PCB_POPULATION):
        random_value = random.randint(0, cost_length)
        act_pcb = 0
        while random_value > 0:
            random_value -= reversed_pcbs[act_pcb][1]
            act_pcb += 1
        else:
            result_pcbs.append(reversed_pcbs[act_pcb - 1][0])

    return result_pcbs


def my_split(a, n):
    return np.array_split(a, n)


def competition(pcb_list):
    comp_lists = list(my_split(pcb_list, PCB_POPULATION))
    result_pcbs = []

    for my_list in comp_lists:
        result_pcbs.append((sorted(my_list, key=lambda x: x[1])[0])[0])
    return result_pcbs


def print_matrix(pcb):
    print("\nMatrix:")
    for x in range(int(PCB.MATRIX_OVERLAP / 2), pcb.width + int(PCB.MATRIX_OVERLAP / 2), 1):
        for y in range(int(PCB.MATRIX_OVERLAP / 2), pcb.height + int(PCB.MATRIX_OVERLAP / 2), 1):
            print(pcb.matrix[x][y], '', end='')
        print('')


if __name__ == '__main__':
    data_load = DataLoading("lab1_problemy_testowe/zad1.txt")
    data = data_load.get_data()
    points_list = data[1]
    pcb = PCB(points_list, data[0][0], data[0][1])

    pcbs_list = []
    for i in range(FIRST_POPULATION):
        temp_pcb = PCB(points_list, data[0][0], data[0][1])
        temp_pcb.make_random_simple_solution()
        pcbs_list.append((temp_pcb, temp_pcb.calculate_and_get_quality()))
    pcbs_list = roulette(pcbs_list)
    print_matrix(pcbs_list[0])

    for i in range(COUNTER):
        result_list = []

        for pcb_counter in range(CROSSOVER_POPULATION):
            result_list.append(random.choice(pcbs_list).crossover(random.choice(pcbs_list)))

        for act_pcb in result_list:
            act_pcb.mutation()

        if random.randint(0, 100) < 50:
            pcbs_list = [(i, i.calculate_and_get_quality()**2) for i in result_list]
            pcbs_list = roulette(pcbs_list)
        else:
            pcbs_list = [(i, i.calculate_and_get_quality()**2) for i in result_list]
            pcbs_list = competition(pcbs_list)

    pcbs_list = [(i, i.calculate_and_get_quality()) for i in pcbs_list]
    best_solution = (sorted(pcbs_list, key=lambda x: x[1])[0])[0]
    print_matrix(best_solution)
    print("Done")
