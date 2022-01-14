package com.chess.engine.board;

public class BoardUtils {

    //This section is for the legal moves of the knight
    public static final boolean[] FIRST_COLUMN = initColumn(0); //Stores all the tile numbers that are in the first column
    public static final boolean[] SECOND_COLUMN = initColumn(1);//Stores all the tile numbers that are in the second column
    public static final boolean[] SEVENTH_COLUMN = initColumn(6);//Stores all the tile numbers that are in the seventh column
    public static final boolean[] EIGHTH_COLUMN = initColumn(7);//Stores all the tile numbers that are in the eighth column

    public static final boolean[] SECOND_ROW = initRow(8); //Stores all the tile numbers that are in the second row
    public static final boolean[] SEVENTH_ROW = initRow(56); //Stores all the tile numbers that are in the seventh row

    public static final int NUM_TILES = 64;
    public static final int NUM_TILES_PER_ROW = 8;

    private BoardUtils(){
        throw new RuntimeException("You cannot instantiate me!");
    }

    private static boolean[] initColumn(int columnNumber) {

        final boolean[] column = new boolean[NUM_TILES];
        do{
            column[columnNumber] = true;
            columnNumber += NUM_TILES_PER_ROW;
        }while(columnNumber<NUM_TILES);

        return column;
    }

    private static boolean[] initRow(int rowNumber){
        final boolean[] row = new boolean[NUM_TILES];
        do{
            row[rowNumber] = true;
            rowNumber += 1;
        }while(rowNumber%8!=0);

        return row;
    }

    public static boolean isValidTileCoordinate(int coordinate) {
        return coordinate < 64 && coordinate >= 0; //Checks bounds
    }
}
