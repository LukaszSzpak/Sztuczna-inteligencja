import java.util.Optional;

public class StartApp {
    public static void main(String[] args) throws NoSuchMethodException {
        Mancala mancala = new Mancala(new AI("AI1", "min-max"), new AI("AI2", "min-max"));
        mancala.printGame();

        while (!mancala.theEndOfGame()) {
            mancala.nextMove(Optional.empty());
            mancala.printGame();
        }

        mancala.printEndGameStats();

    }
}
