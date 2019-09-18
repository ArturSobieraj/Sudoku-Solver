package gameLogic;

import table.SudokuBoard;
import table.SudokuElement;

import java.util.ArrayList;
import java.util.List;

public class Guesser {

    public List<Backtrack> findCandidate(SudokuBoard clonedBoard) {
        List<Backtrack> backtracks = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (clonedBoard.getRow(i).getElement(j).getValue().equals(SudokuElement.EMPTY)) {
                    int arrayIndex = clonedBoard.getRow(i).getElement(j).getPossibleNumbers().size()-1;
                    if (arrayIndex == -1) {
                        throw new IllegalStateException("Entered sudoku is invalid");
                    }
                     backtracks.add(new Backtrack(i, j, clonedBoard.getRow(i).getElement(j).getPossibleNumbers().get(arrayIndex), clonedBoard));
                }
            }
        }
        return backtracks;
    }
}
