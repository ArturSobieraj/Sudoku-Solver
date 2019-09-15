package table;

import java.util.ArrayList;
import java.util.List;

public class SudokuElement {

    private static Integer Empty = -1;
    private Integer value;
    private List<Integer> possibleNumbers = new ArrayList<>();

    protected SudokuElement() {
        for (int i = 0; i < 9; i++) {
            possibleNumbers.add(i+1);
            this.value = Empty;
        }
    }

    public Integer getValue() {
        return value;
    }

    public List<Integer> getPossibleNumbers() {
        return possibleNumbers;
    }

    public void removeNumberFromList(int number) {
        possibleNumbers.remove(number);
    }

    public void setValue(int value) {
        this.value = value;
    }
    public String toString() {
        return "|" + (value == -1 ? " ":value);
    }
}
