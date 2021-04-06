import java.util.*;
import java.util.stream.Collectors;

public class ConstraintSatisfactionProblem<V, D> {
    private final List<V> variables;
    private final Map<V, List<D>> domains;
    private final Map<V, List<Constraint<V, D>>> constraints;
    private final static String VALUE_HEURISTIC = "first"; //[first, mostEdges]
    private final static String DOMAIN_HEURISTIC = "leastUsed"; //[random, leastUsed]


    public ConstraintSatisfactionProblem(List<V> variables, Map<V, List<D>> domains) {
        this.variables = variables;
        this.domains = domains;
        constraints = new HashMap<>();
        for (V variable : variables) {
            constraints.put(variable, new ArrayList<>());
            if (!domains.containsKey(variable)) {
                throw new IllegalArgumentException("Every variable should have a domain assigned to it.");
            }
        }
    }

    public void addConstraint(Constraint<V, D> constraint) {
        for (V variable : constraint.variables) {
            if (!variables.contains(variable)) {
                throw new IllegalArgumentException("Variable in constraint not in CSP");
            } else {
                constraints.get(variable).add(constraint);
            }
        }
    }

    public boolean consistent(V variable, Map<V, D> assignment) {
        for (Constraint<V, D> constraint : constraints.get(variable)) {
            if (!constraint.satisfied(assignment)) {
                return false;
            }
        }
        return true;
    }

    private Map<V, D> backTrackingSearch(Map<V, D> assignment) {
        if (assignment.size() == variables.size()) {
            return assignment;
        }

        V unassigned = getNextUnassignedVariable(assignment);
        for (D value : getValuesList(domains.get(unassigned), assignment)) {
            Map<V, D> localAssignment = new HashMap<>(assignment);
            localAssignment.put(unassigned, value);

            if (consistent(unassigned, localAssignment)) {
                Map<V, D> result = backTrackingSearch(localAssignment);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    private List<D> getValuesList(List<D> domains, Map<V, D> assignment) {
        switch (DOMAIN_HEURISTIC) {
            case "random": return getRandomValuesList(domains);
            case "leastUsed": return getLeastUsedValuesList(domains, assignment);
            default: throw new IllegalArgumentException("Wrong domain heuristic choice!");
        }
    }

    private List<D> getRandomValuesList(List<D> domains) {
        Collections.shuffle(domains);
        return domains;
    }

    private List<D> getLeastUsedValuesList(List<D> domains, Map<V, D> assignment) {
        Map<D, Integer> valuesWithCount = new HashMap<>();
        for (D domain : domains)
            valuesWithCount.put(domain, 0);

        for (D domain : assignment.values())
            valuesWithCount.merge(domain, 1, Integer::sum);

        List<D> valuesList = new LinkedList<>();
        while (!valuesWithCount.isEmpty()) {
            D domain = Collections.min(valuesWithCount.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
            valuesList.add(domain);
            valuesWithCount.remove(domain);
        }

        return valuesList;
    }

    private V getNextUnassignedVariable(Map<V, D> assignment) {
        switch (VALUE_HEURISTIC) {
            case "first": return getFirstVariable(assignment);
            case "mostEdges": return getMostEdgesVariable(assignment);
            default: throw new IllegalArgumentException("Wrong value heuristic choice!");
        }
    }

    private V getFirstVariable(Map<V, D> assignment) {
        return variables.stream().filter(v -> (!assignment.containsKey(v))).findFirst().get();
    }

    private V getMostEdgesVariable(Map<V, D> assignment) {
        List<V> variablesWithoutAssigment = variables.stream().filter(v ->
                (!assignment.containsKey(v))).collect(Collectors.toList());
        int most = 0;
        V bestVariable = variablesWithoutAssigment.get(0);
        for (V variable : variablesWithoutAssigment) {
            if (this.constraints.get(variable).size() > most) {
                most = this.constraints.get(variable).size();
                bestVariable = variable;
            }
        }

        return bestVariable;
    }

    public Map<V, D> backTrackingSearch() {
        return backTrackingSearch(new HashMap<>());
    }

}