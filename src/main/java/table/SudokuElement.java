package table;

import java.util.ArrayList;
import java.util.List;

public class SudokuElement {

    public static String EMPTY = "-1";
    private String value;
    private List<String> possibleNumbers = new ArrayList<>();

    public SudokuElement() {
            this.value = EMPTY;

    }

    public String getValue() {
        return value;
    }

    public List<String> getPossibleNumbers() {
        return possibleNumbers;
    }

    public void removeNumberFromList(String number) {
        possibleNumbers.remove(number);
    }

    public void setValue(String value) {
        this.value = value;
    }
    public String toString() {
        return "|" + (value.equals(EMPTY) ? " ":value);
    }
}
