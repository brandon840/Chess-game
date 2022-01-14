package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Pawn extends Piece {

    private final int[] CANDIDATE_MOVE_VECTOR_COORDINATE = {8, 16, 7, 9}; //8: normal pawn move, 16: pawn jump

    public Pawn(final Alliance pieceAlliance,int piecePosition) {
        super(piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for (final int currentCandidateOffset : CANDIDATE_MOVE_VECTOR_COORDINATE) {

            //Black pawns move down (the positive direction) and white pawns move up (negative direction). Hence we need to get the alliance
            final int candidateDestinationCoordinate = this.piecePosition + (this.getPieceAlliance().getDirection() * currentCandidateOffset);

            //Check if destination is a valid tile, else just skip
            //Non-valid tile, skip
            if (!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                continue;
            }

            //If moving one tile forward and the destination square is not occupied by another piece...
            if (currentCandidateOffset == 8 && !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                //TODO: more work to do here!! (deal with promotions)
                legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));

            } else if (currentCandidateOffset == 16 && this.isFirstMove() &&
                    ((BoardUtils.SECOND_ROW[this.piecePosition] && this.getPieceAlliance().isBlack())
                            || (BoardUtils.SEVENTH_ROW[this.piecePosition] && this.getPieceAlliance().isWhite()))) {

                //Must check whether there is a piece one step in front for the pawn jump
                final int behindCandidateDestinationCoordinate = this.piecePosition + (this.pieceAlliance.getDirection() * 8);

                //Check if the two spaces are clear for pawn jump
                if (!board.getTile(behindCandidateDestinationCoordinate).isTileOccupied() &&
                        !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                    legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));

                }

                //Attacks on left and right diagonals
            } else if (currentCandidateOffset == 7 &&
                    !((BoardUtils.FIRST_COLUMN[this.piecePosition] && pieceAlliance.isBlack()) ||
                            (BoardUtils.EIGHTH_COLUMN[this.piecePosition] && pieceAlliance.isWhite()))) {
                if (board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                    final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();

                    //Check if the piece we are attacking is an opponent's piece
                    if (this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
                        //TODO more to do here
                        legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
                    }
                }

            } else if (currentCandidateOffset == 9 &&
                    !((BoardUtils.FIRST_COLUMN[this.piecePosition] && pieceAlliance.isWhite()) ||
                            (BoardUtils.EIGHTH_COLUMN[this.piecePosition] && pieceAlliance.isBlack()))) {
                if (board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                    final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();

                    //Check if the piece we are attacking is an opponent's piece
                    if (this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
                        //TODO more to do here
                        legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
                    }
                }

            }

        }
        return ImmutableList.copyOf(legalMoves);
    }

}