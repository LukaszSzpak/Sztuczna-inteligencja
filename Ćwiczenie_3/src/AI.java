import javafx.util.Pair;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.Math.min;
import static jdk.nashorn.internal.objects.NativeMath.max;

public class AI extends Player {
    private String algorithm; //[min-max, ...]
    private int evaluationFunction; //[1, 2, ...]
    private int maxDepth;

    public AI(String name, Analysis analyzer, String algorithm, int treeDepth, int evaluationFunction) {
        super(name, analyzer);
        this.algorithm = algorithm;
        this.maxDepth = treeDepth * 2;
        this.evaluationFunction = evaluationFunction;
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
            case "alpha-beta": return alphaBetaAlgorithm(mancala);
        }

        throw new NoSuchMethodException("Wrong algorithm!");
    }

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

    private int alphaBetaAlgorithm(Mancala mancala) throws NoSuchMethodException {
        double bestScore = Integer.MIN_VALUE;
        int bestFieldIndex = -1;

        for (Field field : this.getNoneEmptyFields()) {
            double score = this.getAlphaBetaBestMove(mancala, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
            if (score > bestScore) {
                bestScore = score;
                bestFieldIndex = this.fieldList.indexOf(field);
            }

        }

        return bestFieldIndex;
    }

    private double getMinMaxBestMove(Mancala mancala, int actDepth) throws NoSuchMethodException {
        if (actDepth >= this.maxDepth || this.getNoneEmptyFields().isEmpty()) {
            if (actDepth % 2 == 0)
                return this.getOpponentMinusMeScore(mancala, (AI) mancala.getPlayerByName(this));
            return 0;
        }
        analyzer.addMove();

        List<Double> scoreList = new LinkedList<>();
        for (Field field : this.getNoneEmptyFields()) {
            Mancala myMancala = new Mancala(mancala);
            myMancala.nextMove(Optional.of(this.fieldList.indexOf(field)));
            scoreList.add(this.getMinMaxBestMove(myMancala, actDepth + 1));
        }
        return Collections.max(scoreList);
    }

    private double getAlphaBetaBestMove(Mancala mancala, int actDepth, double alpha, double beta) throws NoSuchMethodException {
        if (actDepth >= this.maxDepth || this.getNoneEmptyFields().isEmpty()) {
            if (actDepth % 2 == 0)
                return this.getOpponentMinusMeScore(mancala, (AI) mancala.getPlayerByName(this));
            return 0;
        }
        analyzer.addMove();

        List<Double> scoreList = new LinkedList<>();
        for (Field field : this.getNoneEmptyFields()) {
            Mancala myMancala = new Mancala(mancala);
            myMancala.nextMove(Optional.of(this.fieldList.indexOf(field)));
            double score = this.getAlphaBetaBestMove(myMancala, actDepth + 1, alpha, beta);

            if (actDepth%2 == 0)
                alpha = max(score, alpha);
            else
                beta = min(score, beta);

            if (alpha >= beta)
                return this.getOpponentMinusMeScore(mancala, (AI) mancala.getPlayerByName(this));
            scoreList.add(score);
        }
        return Collections.max(scoreList);
    }

    private double getOpponentMinusMeScore(Mancala mancala, AI ai) throws NoSuchMethodException {
        switch (this.evaluationFunction) {
            case 0: return ai.boardScoreForMinMax() - mancala.getAnotherPlayer(ai).boardScoreForMinMax();
            case 1: return ai.getSummaryScore() - mancala.getAnotherPlayer(ai).getSummaryScore();
            case 2: return ai.getWellScore() - mancala.getAnotherPlayer(ai).getWellScore();
        }
        throw new NoSuchMethodException("Wrong heuristic!");
    }

    private List<Field> getNoneEmptyFields() {
        List<Field> resultList = this.fieldList.stream()
                .filter(f -> !f.isEmpty())
                .collect(Collectors.toList());
        Collections.shuffle(resultList);
        return resultList;
    }

}
