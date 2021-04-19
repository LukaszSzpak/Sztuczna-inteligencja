import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

abstract class Player {
    static final int NUMBER_OF_STONES = 4;
    static final int NUMBER_OF_FIELDS = 6;
    protected List<Field> fieldList;
    protected Well well;
    protected String name;

    public Player(String name) {
        this.name = name;
    }

    void initializePlayer() {
        this.well = new Well();
        fieldList = Stream.generate(Field::new)
                .limit(NUMBER_OF_FIELDS)
                .collect(Collectors.toList());
    }

    public int getWellScore() {
        return this.well.getScore();
    }

    public int getFieldsScore() {
        return this.fieldList.stream()
                .mapToInt(Field::getNumberOfStones)
                .sum();
    }

    public int getSummaryScore() {
        return this.getFieldsScore() + this.getWellScore();
    }

    public boolean checkAllFieldsEmpty() {
        for (Field field : this.fieldList)
            if (!field.isEmpty())
                return false;
        return true;
    }

    public int getNumberOfStonesFromFieldNumber(int fieldNumber) {
        return this.fieldList.get(fieldNumber).getNumberOfStones();
    }

    public int getNumberOfStonesFromFieldNumberAndErase(int fieldNumber) {
        return this.fieldList.get(fieldNumber).getAndEraseStones();
    }

    public void addStoneToField(int fieldNumber) {
        this.fieldList.get(fieldNumber).addStone();
    }

    public void addStoneToWell() {
        this.well.addStone();
    }

    public void addStonesToWall(int numberOfStones) {
        this.well.addStones(numberOfStones);
    }

    abstract int move();

}
