import random
POPULATION_SIZE = 100
GENERATIONS = 200
MUTATION_RATE = 0.2

def init():
    return [[random.uniform(-10, 10) for _ in range(3)] for _ in range(POPULATION_SIZE)]

def fitness(individual):
    a, b, c = individual
    return 1/sum(abs(a*x**2+b*x+c - (x**2+3*x+2)) for x in range(-10, 11))

def crossover(parent1, parent2):
    cross_point = random.randint(0,2)
    child = parent1[:cross_point] + parent2[cross_point:]
    return child

def mutation(individual):
    for i in range(2):
        individual[i] += random.uniform(-1, 1)*MUTATION_RATE
    individual[2] = random.uniform(-5,5)
    return individual

result = [0, 0, 0]
population = init()
for i in range(GENERATIONS):
    offsprings = []
    while len(offsprings) < POPULATION_SIZE:
        parent1, parent2 = random.choices(population, weights=[fitness(idx) for idx in population], k=2)
        # crossover
        child = crossover(parent1, parent2)
        # mutation
        if random.uniform(0, 1)<MUTATION_RATE:
            child = mutation(child)
        offsprings.append(child)
    population = offsprings
    best_individual = max(population, key=fitness)
    if(fitness(best_individual) > fitness(result)):
        result = best_individual
print(f'result: {result}')