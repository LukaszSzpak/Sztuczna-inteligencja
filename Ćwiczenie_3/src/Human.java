import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Human implements Player{
    private List<Field> fieldList;
    private Well well;

    public Human() {
    }

    @Override
    public void initializePlayer() {
        this.well = new Well();
        fieldList = Stream.generate(Field::new)
                .limit(NUMBER_OF_FIELDS)
                .collect(Collectors.toList());
    }

    @Override
    public int move() {
        System.out.print("Enter next field number (0-5)");
        return new Scanner(System.in).nextInt();
    }

    @Override
    public int getWellScore() {
        return this.well.getScore();
    }

    @Override
    public int getFieldsScore() {
        return this.fieldList.stream()
                .mapToInt(Field::getNumberOfStones)
                .sum();
    }

    @Override
    public int getSummaryScore() {
        return this.getFieldsScore() + this.getWellScore();
    }
}
