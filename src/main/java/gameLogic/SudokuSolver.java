package gameLogic;

import table.SudokuBoard;
import table.SudokuElement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SudokuSolver {
    private boolean isPossible;
    private boolean actionPerformed;
    private List<Backtrack> backtracks = new ArrayList<>();
    private Guesser guesser = new Guesser();

    public boolean solveSudoku(SudokuBoard board) {
        SudokuElement element;
        boolean solutionFound = false;
        while (!solutionFound) {
            do {
                actionPerformed = false;
                for (int row = 0; row < 9; row++) {
                    for (int col = 0; col < 9; col++) {
                        element = board.getRow(row).getElement(col);
                        String candidate = "0";
                        if (element.getValue().equals(SudokuElement.EMPTY)) {
                            for (int cand = 0; cand < element.getPossibleNumbers().size(); cand++) {
                                candidate = element.getPossibleNumbers().get(cand);
                                boolean isSafe = isSafe(board, row, col, candidate, element);
                                if (isSafe) {
                                    if (element.getPossibleNumbers().size() == 1 || isPossible) {
                                        element.setValue(candidate);
                                        actionPerformed = true;
                                    }
                                }
                            }
                        }
                    }
                }
            } while (actionPerformed);
            if (isFullfiled(board)) {
                System.out.println(board);
                solutionFound = true;
            } else {
                System.out.println(board);
                if (backtracks.size() != 0) {
                    board = backtracks.get(backtracks.size() - 1).getBoard();
                    board.getSudokuBoard().get(backtracks.get(backtracks.size() - 1).getRowCoordinates()).getElement(backtracks.get(backtracks.size() - 1).getColCoordinates()).removeNumberFromList(backtracks.get(backtracks.size() - 1).getGuessedValue());
                    backtracks.clear();
                }
                SudokuBoard clonedBoard = null;
                try {
                    clonedBoard = board.deepCopy();
                } catch (CloneNotSupportedException e) {
                    e.getMessage();
                }
                backtracks.add(guesser.findCandidate(clonedBoard));
                board.getSudokuBoard().get(backtracks.get(backtracks.size() - 1).getRowCoordinates()).getElement(backtracks.get(backtracks.size() - 1).getColCoordinates()).setValue(backtracks.get(backtracks.size() - 1).getGuessedValue());
                actionPerformed = true;
            }
        }
        return true;
    }

    public boolean isSafe(SudokuBoard board, int row, int col, String candidate, SudokuElement element) {
        actionPerformed = false;
        isPossible = true;
        boolean isSafe = true;
        //row clash
        for (int i = 0; i < 9; i++) {
            if (board.getRow(row).getElement(i).getValue().equals(candidate)) {
                isSafe = false;
            }

            if (!(board.getRow(row).getElement(i).getValue().equals(SudokuElement.EMPTY)) && element.getPossibleNumbers().contains(board.getRow(row).getElement(i).getValue())) {
                element.removeNumberFromList(board.getRow(row).getElement(i).getValue());
                actionPerformed = true;
            }

            if (board.getRow(row).getElement(i).getPossibleNumbers().contains(candidate)) {
                isPossible = false;
            }
        }
        //column clash
        for (int i = 0; i < 9; i++) {
            if (board.getRow(i).getElement(col).getValue().equals(candidate)) {
                isSafe = false;
            }
            if (!(board.getRow(i).getElement(col).getValue().equals(SudokuElement.EMPTY)) && element.getPossibleNumbers().contains(board.getRow(i).getElement(col).getValue())) {
                element.removeNumberFromList(board.getRow(i).getElement(col).getValue());
                actionPerformed = true;
            }

            if (board.getRow(i).getElement(col).getPossibleNumbers().contains(candidate)) {
                isPossible = false;
            }
        }
        //square clash
        int sqrt = (int) Math.sqrt(9);
        int boxRowStart = row - row % sqrt;
        int boxColStart = col - col % sqrt;

        for (int r = boxRowStart; r < boxRowStart + sqrt; r++) {
            for (int c = boxColStart; c < boxColStart + sqrt; c++) {
                if (board.getRow(r).getElement(c).getValue().equals(candidate)) {
                    isSafe = false;
                }
                if (!(board.getRow(r).getElement(c).getValue().equals(SudokuElement.EMPTY)) && element.getPossibleNumbers().contains(board.getRow(r).getElement(c).getValue())) {
                    element.removeNumberFromList(board.getRow(r).getElement(c).getValue());
                    actionPerformed = true;
                }

                if (board.getRow(r).getElement(c).getPossibleNumbers().contains(candidate)) {
                    isPossible = false;
                }
            }
        }
        return isSafe;

    }

    private boolean isFullfiled(SudokuBoard board) {
        boolean isFullfiled = false;
        List<SudokuElement> resultedList = board.getSudokuBoard().stream()
                .flatMap(rows -> rows.getSudokuRow().stream())
                .filter(el -> el.getValue().equals(SudokuElement.EMPTY))
                .collect(Collectors.toList());
        if (resultedList.size() == 0) {
            isFullfiled = true;
        }
        return isFullfiled;
    }
}
