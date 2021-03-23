import copy
import random
import numpy as np
import matplotlib.pyplot as plt

from Cwiczenie_1.PCB import PCB
from Cwiczenie_1.dataLoading import DataLoading

COUNTER = 30
PCB_POPULATION = 10
CROSSOVER_POPULATION = 20
FIRST_POPULATION = 20


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


def tournament(pcb_list):
    comp_lists = list(my_split(pcb_list, PCB_POPULATION))
    result_pcbs = []

    for my_list in comp_lists:
        result_pcbs.append((sorted(my_list, key=lambda x: x[1])[0])[0])
    return result_pcbs


def population_data(pcb_list_with_cost):
    min_cost = (sorted(pcb_list_with_cost, key=lambda x: x[1])[0])[1]
    max_cost = (sorted(pcb_list_with_cost, key=lambda x: x[1])[-1])[1]
    avg_cost = sum([pair[1] for pair in pcb_list_with_cost]) / len(pcb_list_with_cost)
    return min_cost, max_cost, avg_cost


def print_matrix(pcb):
    print("\nMatrix:")
    for x in range(int(PCB.MATRIX_OVERLAP / 2), pcb.width + int(PCB.MATRIX_OVERLAP / 2), 1):
        for y in range(int(PCB.MATRIX_OVERLAP / 2), pcb.height + int(PCB.MATRIX_OVERLAP / 2), 1):
            print(pcb.matrix[x][y], '', end='')
        print('')


def show_plot(cost_list):
    counter = 1
    min_list = []
    max_list = []
    avg_list = []
    count_list = []
    for my_min, my_max, avg in cost_list:
        min_list.append(my_min)
        avg_list.append(avg)
        if my_max > (sum(avg_list) / counter) * 5:
            my_max = avg * 2
        max_list.append(my_max)

        count_list.append(counter)
        counter += 1

    plt.plot(count_list, min_list, label="min")
    plt.plot(count_list, max_list, label="max")
    plt.plot(count_list, avg_list, label="avg")
    plt.legend()
    plt.show()


if __name__ == '__main__':
    data_load = DataLoading("lab1_problemy_testowe/zad3.txt")
    data = data_load.get_data()
    points_list = data[1]
    pcb = PCB(points_list, data[0][0], data[0][1])
    costs_list = []

    pcbs_list = []
    for i in range(FIRST_POPULATION):
        temp_pcb = PCB(points_list, data[0][0], data[0][1])
        temp_pcb.make_random_simple_solution()
        pcbs_list.append((temp_pcb, temp_pcb.calculate_and_get_quality()))
    pcbs_list = roulette(pcbs_list)
    # print_matrix(pcbs_list[0])

    for i in range(COUNTER):
        result_list = []

        for pcb_counter in range(CROSSOVER_POPULATION):
            result_list.append(random.choice(pcbs_list).crossover(random.choice(pcbs_list)))

        for act_pcb in result_list:
            act_pcb.mutation()

        pcbs_list = [(i, i.calculate_and_get_quality() ** 2) for i in result_list]
        # cost = (sorted(pcbs_list, key=lambda x: x[1])[0])[1]
        # print("Iter: ", i, " Cost: ", cost)
        costs_list.append(population_data(pcbs_list))

        if random.randint(0, 100) < 50:
            pcbs_list = roulette(pcbs_list)
        else:
            pcbs_list = tournament(pcbs_list)

    # pcbs_list = [(i, i.calculate_and_get_quality()) for i in pcbs_list]
    # best_solution = (sorted(pcbs_list, key=lambda x: x[1])[0])[0]
    # print_matrix(best_solution)
    # print("Done")
    show_plot(costs_list)
