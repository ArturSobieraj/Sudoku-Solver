package table;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SudokuRow {

    private List<SudokuElement> sudokuRow = new ArrayList<>();

    protected SudokuRow() {
        for (int i = 0; i < 9; i++) {
            sudokuRow.add(new SudokuElement());
        }
    }

    public List<SudokuElement> getSudokuRow() {
        return sudokuRow;
    }

    @Override
    public String toString() {
        return sudokuRow.stream()
                .map(se -> se.toString())
                .collect(Collectors.joining("", "", "|\n"));
    }
}
