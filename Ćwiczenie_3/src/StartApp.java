import java.util.Optional;

public class StartApp {
    public static void main(String[] args) throws NoSuchMethodException, InterruptedException {
        Mancala mancala = new Mancala(
                new AI("AI1", "min-max", 2),
                new AI("AI2", "min-max", 2));
        mancala.printGame();

        while (!mancala.theEndOfGame()) {
            mancala.nextMove(Optional.empty());
            mancala.printGame();

            //Thread.sleep(1000);
        }

        mancala.printEndGameStats();

    }
}
