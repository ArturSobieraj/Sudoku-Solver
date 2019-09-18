package gameLogic;

import table.SudokuBoard;
import table.SudokuElement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SudokuSolver {
    private List<Backtrack> backtracks = new ArrayList<>();
    private int loopCounter = 0;
    private int interLoopCounter = 0;
    private Guesser guesser = new Guesser();
    private int backtrackIndex;

    public boolean solveSudoku(SudokuBoard board) {
        SudokuElement element;
        boolean actionPerformed = true;
        boolean solutionFound = false;
        while (!solutionFound) {
            while (actionPerformed) {
                actionPerformed = false;
                for (int row = 0; row < 9; row++) {
                    for (int col = 0; col < 9; col++) {
                        element = board.getRow(row).getElement(col);
                        String candidate = "0";
                        if (element.getValue().equals(SudokuElement.EMPTY)) {
                            for (int cand = 0; cand < element.getPossibleNumbers().size(); cand++) {
                                candidate = element.getPossibleNumbers().get(cand);
                                element.getPossibleNumbers().remove(candidate);
                                boolean isSafe = isSafe(board, row, col, candidate);
                                boolean isPossible = isPossible(board, row, col, candidate);
                                element.getPossibleNumbers().add(cand, candidate);
                                if (isSafe) {
                                    if (isPossible) {
                                        element.setValue(candidate);
                                        element.getPossibleNumbers().clear();
                                        actionPerformed = true;
                                    }

                                    if (element.getPossibleNumbers().size() == 1) {
                                        element.setValue(element.getPossibleNumbers().get(element.getPossibleNumbers().size() - 1));
                                        element.getPossibleNumbers().clear();
                                        actionPerformed = true;
                                    }

                                } else {
                                    element.getPossibleNumbers().remove(candidate);
                                    actionPerformed = true;
                                }
                            }
                        }
                    }
                }
                interLoopCounter++;
                System.out.println("Inter loop counter: " + interLoopCounter);
            }

            if (isFullfiled(board)) {
                System.out.println(board);
                System.out.println("Number of loops:" + loopCounter);
                solutionFound = true;
            } else {
                System.out.println(board);
                System.out.println("Number of loops: " + loopCounter);

                if (backtracks.size() != 0) {
                    board = backtracks.get(backtrackIndex).getBoard();
                    int rowCoordinates = backtracks.get(backtrackIndex).getRowCoordinates();
                    int colCoordinates = backtracks.get(backtrackIndex).getColCoordinates();
                    String guessedValue = backtracks.get(backtrackIndex).getGuessedValue();
                    board.getRow(rowCoordinates).getElement(colCoordinates).removeNumberFromList(guessedValue);
                    backtracks.remove(backtrackIndex);
                    actionPerformed = true;

                    if (backtracks.size() != 0) {
                        SudokuBoard clonedBoard = null;
                        try {
                            clonedBoard = board.deepCopy();
                        } catch (CloneNotSupportedException e) {
                            e.getMessage();
                        }

                        for (Backtrack backtrack : backtracks) {
                            backtrack.setBoard(clonedBoard);
                        }

                        for (int i = 0; i < backtracks.size(); i++) {
                            rowCoordinates = backtracks.get(i).getRowCoordinates();
                            colCoordinates = backtracks.get(i).getColCoordinates();
                            guessedValue = backtracks.get(i).getGuessedValue();
                            Backtrack backtrack = backtracks.get(i);

                            if (isSafe(board, rowCoordinates, colCoordinates, guessedValue)) {
                                board.getRow(rowCoordinates).getElement(colCoordinates).setValue(guessedValue);
                                board.getRow(rowCoordinates).getElement(colCoordinates).getPossibleNumbers().clear();
                                backtrackIndex = backtracks.indexOf(backtrack);
                            }
                            break;
                        }
                        actionPerformed = true;
                    }
                }

                if (backtracks.size() == 0) {
                    SudokuBoard clonedBoard = null;
                    try {
                        clonedBoard = board.deepCopy();
                    } catch (CloneNotSupportedException e) {
                        e.getMessage();
                    }
                    backtracks = guesser.findCandidate(clonedBoard);

                    for (int i = 0; i < backtracks.size(); i++) {
                        int rowCoordinates = backtracks.get(i).getRowCoordinates();
                        int colCoordinates = backtracks.get(i).getColCoordinates();
                        String guessedValue = backtracks.get(i).getGuessedValue();
                        Backtrack backtrack = backtracks.get(i);
                        if (isSafe(board, rowCoordinates, colCoordinates, guessedValue)) {
                            board.getRow(rowCoordinates).getElement(colCoordinates).setValue(guessedValue);
                            board.getRow(rowCoordinates).getElement(colCoordinates).getPossibleNumbers().clear();
                            backtrackIndex = backtracks.indexOf(backtrack);
                        }
                        break;
                    }
                    actionPerformed = true;
                }
            }
            loopCounter++;
        }
        return true;
    }

    private boolean isSafe(SudokuBoard board, int row, int col, String candidate) {

        boolean isSafe = true;
        //row clash
        for (int i = 0; i < 9; i++) {
            if (board.getRow(row).getElement(i).getValue().equals(candidate)) {
                isSafe = false;
            }
        }
        //column clash
        for (int i = 0; i < 9; i++) {
            if (board.getRow(i).getElement(col).getValue().equals(candidate)) {
                isSafe = false;
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
            }
        }
        return isSafe;
    }

    private boolean isPossible(SudokuBoard board, int row, int col, String candidate) {

        boolean isPossible = true;
        for (int i = 0; i < 9; i++) {
            if (board.getRow(row).getElement(i).getPossibleNumbers().contains(candidate)) {
                isPossible = false;
            }

            if (board.getRow(i).getElement(col).getPossibleNumbers().contains(candidate)) {
                isPossible = false;
            }

            int sqrt = (int) Math.sqrt(9);
            int boxRowStart = row - row % sqrt;
            int boxColStart = col - col % sqrt;

            for (int r = boxRowStart; r < boxRowStart + sqrt; r++) {
                for (int c = boxColStart; c < boxColStart + sqrt; c++) {
                    if (board.getRow(r).getElement(c).getPossibleNumbers().contains(candidate)) {
                        isPossible = false;
                    }
                }
            }
        }
        return isPossible;
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
