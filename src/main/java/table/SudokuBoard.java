package table;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SudokuBoard extends Cloner {

    private List<SudokuRow> sudokuBoard = new ArrayList<>();

    @Override
    public String toString() {
        return sudokuBoard.stream()
                .map(sr -> sr.toString())
                .collect(Collectors.joining());
    }

    public SudokuRow getRow(int x) {
        return sudokuBoard.get(x);
    }

    public SudokuBoard deepCopy() throws CloneNotSupportedException {
        SudokuBoard clonedBoard = (SudokuBoard)super.clone();
        clonedBoard.sudokuBoard = new ArrayList<>();
        for (SudokuRow row : sudokuBoard) {
            SudokuRow clonedRow = new SudokuRow();
            for (SudokuElement element : row.getSudokuRow()) {
                SudokuElement clonedElement = new SudokuElement();
                clonedElement.setValue(element.getValue());
                for (String number : element.getPossibleNumbers()) {
                    clonedElement.getPossibleNumbers().add(number);
                }
                clonedRow.getSudokuRow().add(clonedElement);
            }
            clonedBoard.getSudokuBoard().add(clonedRow);
        }
        return clonedBoard;
    }

    public List<SudokuRow> getSudokuBoard() {
        return sudokuBoard;
    }
}

