import java.util.*;
import java.util.stream.Collectors;

public class ConstraintSatisfactionProblem<V, D> {
    private final List<V> variables;
    private final Map<V, List<D>> domains;
    private final Map<V, List<Constraint<V, D>>> constraints;
    private final static String HEURISTIC = "first"; //[first, mostEdges]


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
        for (D value : domains.get(unassigned)) {
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

    private V getNextUnassignedVariable(Map<V, D> assignment) {
        switch (ConstraintSatisfactionProblem.HEURISTIC) {
            case "first": return getFirstVariable(assignment);
            case "mostEdges": return getMostEdgesVariable(assignment);
            default: throw new IllegalArgumentException("Wrong heuristic choice!");
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