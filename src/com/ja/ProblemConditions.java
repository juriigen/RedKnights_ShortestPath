package com.company;

public class ProblemConditions {

    private final int mSizeOfBoardNxN;
    private final int mNearestMove;
    private final int mFareMostMove;


    public ProblemConditions(int sizeOfBoardNxN, int nearestMove, int fareMostMove) {
        mSizeOfBoardNxN = sizeOfBoardNxN;
        mNearestMove = nearestMove;
        mFareMostMove = fareMostMove;
    }

    /**
     * @return true if the position within bounds of chessboard
     */
    public boolean isPositionOnBoard(int x,int y) {
        return (x <= mSizeOfBoardNxN-1 && y <= mSizeOfBoardNxN-1
                && x >= 0 && y >= 0);
    }

    public int getFareMostMove() {
        return mFareMostMove;
    }

    public int getNearestMove() {
        return mNearestMove;
    }

    public int getBoardSize() {
        return mSizeOfBoardNxN;
    }
}
