import javafx.util.Pair;

import java.util.*;

public class ColoringMap extends Constraint<Pair<Integer, Integer>, String>{
    public Pair<Integer, Integer> place1;
    public Pair<Integer, Integer> place2;

    public ColoringMap(Pair<Integer, Integer> place1, Pair<Integer, Integer> place2) {
        super(Arrays.asList(place1, place2));
        this.place1 = place1;
        this.place2 = place2;
    }

    @Override
    public boolean satisfied(Map<Pair<Integer, Integer>, String> assignment) {
        if (!assignment.containsKey(place1) || !assignment.containsKey(place2)) {
            return true;
        }
        return !assignment.get(place1).equals(assignment.get(place2));
    }


    public boolean checkCrossing(ColoringMap coloringMap) {
        return Section.checkCrossing(new int[]{this.place1.getKey(),
                                            this.place2.getKey(),
                                            coloringMap.place1.getKey(),
                                            coloringMap.place2.getKey()} ,
                                    new int[]{this.place1.getValue(),
                                            this.place2.getValue(),
                                            coloringMap.place1.getValue(),
                                            coloringMap.place2.getValue()});
    }

    public boolean checkCrossingForAllFromList(List<ColoringMap> constrainsList) {
        for (ColoringMap coloringMap : constrainsList)
            if (coloringMap.checkCrossing(this))
                return true;
        return false;
    }

    public static List<Pair<Integer, Integer>> getRandomVariables(int mapX, int mapY, int howManyPlaces) {
        List<Pair<Integer, Integer>> resultsList = new LinkedList<>();
        Random random = new Random();

        while (resultsList.size() < howManyPlaces) {
            int x = random.nextInt(mapX);
            int y = random.nextInt(mapY);
            Pair<Integer, Integer> tempPair = new Pair<>(x, y);

            if (!resultsList.contains(tempPair))
                resultsList.add(tempPair);
        }
        return resultsList;
    }

    public static List<ColoringMap> getConstraintsList(List<Pair<Integer, Integer>> variablesList) {
        List<ColoringMap> resultList = new LinkedList<>();

        for (Pair<Integer, Integer> firstPair : variablesList) {
            for (Pair<Integer, Integer> secondPair : variablesList) {
                if (!firstPair.equals(secondPair)) {
                    ColoringMap tempColoringMap = new ColoringMap(firstPair, secondPair);

                    if (!tempColoringMap.checkCrossingForAllFromList(resultList))
                        resultList.add(tempColoringMap);
                }
            }
        }

        return resultList;
    }

    public static void main(String[] args) {
        List<String> colorList = Arrays.asList("red", "blue", "green", "yellow");
        List<Pair<Integer, Integer>> variables = getRandomVariables(10, 15, 10);
        Map<Pair<Integer, Integer>, List<String>> domains = new HashMap<>();

        for (Pair<Integer, Integer> variable : variables) {
            domains.put(variable, colorList);
        }

        ConstraintSatisfactionProblem<Pair<Integer, Integer>, String> csp = new ConstraintSatisfactionProblem<>(variables, domains);
        List<ColoringMap> constrainsList = getConstraintsList(variables);

        for (ColoringMap coloringMap : constrainsList)
            csp.addConstraint(coloringMap);

        Map<Pair<Integer, Integer>, String> solution = csp.backTrackingSearch();
        if (solution == null) {
            System.out.println("No solution found!");
        } else {
            System.out.println(solution);
        }
    }
}
