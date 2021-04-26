import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AI extends Player {
    private String algorithm; //[min-max, ...]

    public AI(String name, String algorithm) {
        super(name);
        this.algorithm = algorithm;
    }

    public AI(AI ai) {
        super(ai);
        this.algorithm = ai.algorithm;
    }

    @Override
    int move(Mancala mancala) throws NoSuchMethodException {
        System.out.println("Turn: " + this.name);
        switch (this.algorithm) {
            case "min-max": return minMaxAlgorithm(mancala);
        }

        throw new NoSuchMethodException("Wrong algorithm!");
    }

    private int minMaxAlgorithm(Mancala mancala) throws NoSuchMethodException {
        double bestScore = Integer.MIN_VALUE;
        int bestFieldIndex = -1;

        for (Field field : this.getNoneEmptyFields()) {
            Mancala myMancala = new Mancala(mancala);
            AI ai = new AI(this);
            myMancala.nextMove(Optional.of(this.fieldList.indexOf(field)));

            double score = ai.boardScoreForMinMax();
            double opponentScore = myMancala.getAnotherPlayer(ai).boardScoreForMinMax();
            if (score - opponentScore > bestScore) {
                bestScore = score - opponentScore;
                bestFieldIndex = this.fieldList.indexOf(field);
            }

        }

        return bestFieldIndex;
    }

    private List<Field> getNoneEmptyFields() {
        List<Field> resultList = this.fieldList.stream()
                .filter(f -> !f.isEmpty())
                .collect(Collectors.toList());
        Collections.shuffle(resultList);
        return resultList;
    }

}
