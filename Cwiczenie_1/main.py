import random

from Cwiczenie_1.PCB import PCB
from Cwiczenie_1.dataLoading import DataLoading

COUNTER = 1000
PCB_POPULATION = 20


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
            result_pcbs.append(reversed_pcbs[act_pcb][0])

    return result_pcbs


if __name__ == '__main__':
    data_load = DataLoading("lab1_problemy_testowe/zad1.txt")
    data = data_load.get_data()
    points_list = data[1]
    pcb = PCB(points_list, data[0][0], data[0][1])

    pcbs_list = []
    for i in range(100):
        temp_pcb = PCB(points_list, data[0][0], data[0][1])
        temp_pcb.make_random_simple_solution()
        pcbs_list.append((temp_pcb, temp_pcb.calculate_and_get_quality()))
    pcbs_list = roulette(pcbs_list)

    for i in range(COUNTER):
        pass
    print("Done")
