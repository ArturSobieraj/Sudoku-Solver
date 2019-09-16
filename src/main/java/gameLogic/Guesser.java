package gameLogic;

import table.SudokuBoard;
import table.SudokuElement;

public class Guesser {

    public Backtrack findCandidate(SudokuBoard clonedBoard) {
        Backtrack backtrack = null;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (clonedBoard.getRow(i).getElement(j).getValue().equals(SudokuElement.EMPTY) && clonedBoard.getRow(i).getElement(j).getPossibleNumbers().size() != 0) {
                    int arrayIndex = clonedBoard.getRow(i).getElement(j).getPossibleNumbers().size()-1;
                    backtrack = new Backtrack(i, j, clonedBoard.getRow(i).getElement(j).getPossibleNumbers().get(arrayIndex), clonedBoard);
                }
                break;
            }
            break;
        }
        return backtrack;
    }
}
