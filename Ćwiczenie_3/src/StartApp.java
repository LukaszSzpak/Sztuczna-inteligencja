import java.util.Optional;

public class StartApp {
    public static void main(String[] args) throws NoSuchMethodException {
        Analysis analyzer1 = new Analysis();
        Analysis analyzer2 = new Analysis();

        Mancala mancala = new Mancala(
                new AI("AI1", analyzer1, "alpha-beta", 4),
                new AI("AI2", analyzer2, "min-max", 4));
        mancala.printGame();

        while (!mancala.theEndOfGame()) {
            mancala.nextMove(Optional.empty());
            mancala.printGame();
        }

        mancala.printEndGameStats();
        analyzer1.printStats();
        analyzer2.printStats();

    }
}
