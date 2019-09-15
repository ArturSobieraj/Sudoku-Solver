package table;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SudokuBoard {

    private List<SudokuRow> sudokuBoard = new ArrayList<>();

    public SudokuBoard() {
        for (int i = 0; i < 9; i++) {
            sudokuBoard.add(new SudokuRow());
        }
    }

    @Override
    public String toString() {
        return sudokuBoard.stream()
                .map(sr -> sr.toString())
                .collect(Collectors.joining());
    }
}
