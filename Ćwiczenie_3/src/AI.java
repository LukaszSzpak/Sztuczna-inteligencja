import javafx.util.Pair;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AI extends Player {
    private String algorithm; //[min-max, ...]
    private int maxDepth;

    public AI(String name, String algorithm, int treeDepth) {
        super(name);
        this.algorithm = algorithm;
        this.maxDepth = treeDepth * 2;
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

//    private int minMaxAlgorithm(Mancala mancala) throws NoSuchMethodException {
//        double bestScore = Integer.MIN_VALUE;
//        int bestFieldIndex = -1;
//
//        for (Field field : this.getNoneEmptyFields()) {
//            Mancala myMancala = new Mancala(mancala);
//            AI ai = (AI) myMancala.getPlayerByName(this);
//            myMancala.nextMove(Optional.of(this.fieldList.indexOf(field)));
//
//            double score = getOpponentMinusMeScore(myMancala, ai);
//            if (score > bestScore) {
//                bestScore = score;
//                bestFieldIndex = this.fieldList.indexOf(field);
//            }
//
//        }
//
//        return bestFieldIndex;
//    }

    private int minMaxAlgorithm(Mancala mancala) throws NoSuchMethodException {
        double bestScore = Integer.MIN_VALUE;
        int bestFieldIndex = -1;

        for (Field field : this.getNoneEmptyFields()) {
            double score = this.getMinMaxBestMove(mancala, 0);
            if (score > bestScore) {
                bestScore = score;
                bestFieldIndex = this.fieldList.indexOf(field);
            }

        }

        return bestFieldIndex;
    }

    private double getMinMaxBestMove(Mancala mancala, int actDepth) throws NoSuchMethodException {
        if (actDepth >= this.maxDepth || this.getNoneEmptyFields().isEmpty()) {
            return this.getOpponentMinusMeScore(mancala, (AI) mancala.getPlayerByName(this));
        }

        List<Double> scoreList = new LinkedList<>();
        for (Field field : this.getNoneEmptyFields()) {
            Mancala myMancala = new Mancala(mancala);
            myMancala.nextMove(Optional.of(this.fieldList.indexOf(field)));
            scoreList.add(this.getMinMaxBestMove(myMancala, actDepth + 1));
        }
        return Collections.max(scoreList);
    }

    private double getOpponentMinusMeScore(Mancala mancala, AI ai) {
        double score = ai.boardScoreForMinMax();
        double opponentScore = mancala.getAnotherPlayer(ai).boardScoreForMinMax();

        return score - opponentScore;
    }

    private List<Field> getNoneEmptyFields() {
        List<Field> resultList = this.fieldList.stream()
                .filter(f -> !f.isEmpty())
                .collect(Collectors.toList());
        Collections.shuffle(resultList);
        return resultList;
    }

}
