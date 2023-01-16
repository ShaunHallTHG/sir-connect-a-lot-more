package com.thg.accelerator19.connectn.ai.shaunhall;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.GameConfig;
import com.thg.accelerator19.connectn.ai.shaunhall.connectn.SirConnectALotMore;
import org.junit.Assert;
import org.junit.Test;

import static com.thehutgroup.accelerator.connectn.player.Counter.O;
import static com.thehutgroup.accelerator.connectn.player.Counter.X;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class SirConnectALotMoreTest
{
    @Test
    public void testImmediateWinTest() {
        int width = 10;
        int height = 8;
        int nInARowForWin = 4;
        SirConnectALotMore sirConnectALotMore = new SirConnectALotMore(Counter.O, new GameConfig(width, height, nInARowForWin), 2, 2);
        Counter[][] counters = new Counter[height][width];
        counters[7] = new Counter[] {O,X,O,null,null,null,null,null,null,null};
        counters[6] = new Counter[] {X,O,X,null,null,null,null,null,null,null};
        counters[5] = new Counter[] {O,X,X,null,null,O   ,null,null,null,null};
        counters[4] = new Counter[] {X,X,X,null,O   ,O   ,null,null,null,null};
        counters[3] = new Counter[] {O,O,O,null,X   ,X   ,null,null,null,null};
        counters[2] = new Counter[] {O,O,O,null,O   ,X   ,null,null,null,null};
        counters[1] = new Counter[] {X,O,O,X   ,X   ,X   ,null,null,null,null};
        counters[0] = new Counter[] {O,X,X,X   ,O   ,O   ,null,null,X   ,null};
        counters = rotateBoard(counters);

        Board board = new Board(counters, new GameConfig(width, height, nInARowForWin));
        int c = sirConnectALotMore.makeMove(board);
        Assert.assertEquals(3, c);
    }

    @Test
    public void immediateWinLossTest() {
        int width = 10;
        int height = 8;
        int nInARowForWin = 4;
        SirConnectALotMore sirConnectALotMore = new SirConnectALotMore(Counter.O, new GameConfig(width, height, nInARowForWin), 2, 2);
        Counter[][] counters = new Counter[height][width];
        counters[7] = new Counter[] {null,null,null,null,null,null,null,null,null,null};
        counters[6] = new Counter[] {null,null,null,X,null,null,null,null,null,null};
        counters[5] = new Counter[] {null,null,null,O,null,null,null,null,null,null};
        counters[4] = new Counter[] {null,null,null,O,null,null,null,null,null,null};
        counters[3] = new Counter[] {null,null,null,O,null,null,null,null,null,null};
        counters[2] = new Counter[] {null,X   ,X   ,X,null,null,null,null,null,null};
        counters[1] = new Counter[] {null,O   ,X   ,O,null,null,null,null,null,null};
        counters[0] = new Counter[] {O   ,X   ,O   ,O,X,O,null,X,null,null};
        counters = rotateBoard(counters);

        Board board = new Board(counters, new GameConfig(width, height, nInARowForWin));
        int c = sirConnectALotMore.makeMove(board);
        Assert.assertNotEquals(0, c);
    }

    @Test
    public void sensibleTest() {
        int width = 10;
        int height = 8;
        int nInARowForWin = 4;
        SirConnectALotMore sirConnectALotMore = new SirConnectALotMore(Counter.O, new GameConfig(width, height, nInARowForWin), 4, 4);
        Counter[][] counters = new Counter[height][width];
        counters[7] = new Counter[] {null,null,null,null,null,null,null,null,null,null};
        counters[6] = new Counter[] {null,null,null,null,null,null,null,null,null,null};
        counters[5] = new Counter[] {null,null,null,null,null,null,null,null,null,null};
        counters[4] = new Counter[] {null,null,null,null,null,null,null,null,null,null};
        counters[3] = new Counter[] {null,null,null,O,null,null,null,null,null,null};
        counters[2] = new Counter[] {null,null,null,X,O,null,null,null,null,null};
        counters[1] = new Counter[] {O   ,null,null,O,X,null,null,null,null,null};
        counters[0] = new Counter[] {O   ,null,null,X,X,null,null,null,null,null};
        counters = rotateBoard(counters);

        Board board = new Board(counters, new GameConfig(width, height, nInARowForWin));
        int c = sirConnectALotMore.makeMove(board);
        assertTrue(c == 2 || c == 5);
    }

    @Test
    public void sensibleTest2() {
        int width = 10;
        int height = 8;
        int nInARowForWin = 4;
        SirConnectALotMore sirConnectALotMore = new SirConnectALotMore(Counter.X, new GameConfig(width, height, nInARowForWin), 4, 4);
        Counter[][] counters = new Counter[height][width];
        counters[7] = new Counter[] {null,null,null,null,null,null,null,null,null,null};
        counters[6] = new Counter[] {null,null,null,null,null,null,null,null,null,null};
        counters[5] = new Counter[] {null,null,null,null,null,null,null,null,null,null};
        counters[4] = new Counter[] {null,null,null,X   ,null,null,null,null,null,null};
        counters[3] = new Counter[] {null,null,null,X   ,null,null,null,null,null,null};
        counters[2] = new Counter[] {null,null,null,O   ,null,null,O,null,null,null};
        counters[1] = new Counter[] {null,null,null,X   ,null,null,O,null,null,null};
        counters[0] = new Counter[] {null,null,null,O   ,null,X   ,O,null,null,null};
        counters = rotateBoard(counters);

        Board board = new Board(counters, new GameConfig(width, height, nInARowForWin));
        int c = sirConnectALotMore.makeMove(board);
        assertEquals(6, c);
    }

    @Test
    public void sensibleTest4() {
        int width = 10;
        int height = 8;
        int nInARowForWin = 4;
        SirConnectALotMore sirConnectALotMore = new SirConnectALotMore(Counter.O, new GameConfig(width, height, nInARowForWin), 4, 4);
        Counter[][] counters = new Counter[height][width];
        counters[7] = new Counter[] {null,null,null,null,null,null,null,null,null,null};
        counters[6] = new Counter[] {null,null,null,null,null,null,null,null,null,null};
        counters[5] = new Counter[] {null,null,null,null,null,null,O,null,null,null};
        counters[4] = new Counter[] {null,null,null,null,null,null,O,null,null,null};
        counters[3] = new Counter[] {null,null,null,null,null,X   ,X,null,null,null};
        counters[2] = new Counter[] {null,null,null,null,null,X   ,O,null,null,null};
        counters[1] = new Counter[] {null,null,null,O   ,null,O   ,O,null,null,null};
        counters[0] = new Counter[] {null,null,X   ,X   ,null,O   ,X,null,null,null};
        counters = rotateBoard(counters);

        Board board = new Board(counters, new GameConfig(width, height, nInARowForWin));
        int c = sirConnectALotMore.makeMove(board);
        assertTrue(c == 1 || c == 6);
    }

//    @Test
//    public void sensibleTest3() {
//        int width = 10;
//        int height = 8;
//        int nInARowForWin = 4;
//        SirConnectALotMore sirConnectALotMore = new SirConnectALotMore(Counter.X, new GameConfig(width, height, nInARowForWin), 6, 80);
//        Counter[][] counters = new Counter[height][width];
//        counters[7] = new Counter[] {null,null,null,null,null,null,null,null,null,null};
//        counters[6] = new Counter[] {null,null,null,null,null,null,null,null,null,null};
//        counters[5] = new Counter[] {null,null,null,null,null,null,null,null,null,null};
//        counters[4] = new Counter[] {null,null,null,X   ,null,null,null,null,null,null};
//        counters[3] = new Counter[] {null,null,null,X   ,null,null,X   ,null,null,null};
//        counters[2] = new Counter[] {null,null,null,O   ,null,O,O   ,null,null,null};
//        counters[1] = new Counter[] {null,null,null,X   ,null,X   ,O   ,O,O,X};
//        counters[0] = new Counter[] {null,null,null,O   ,X   ,X   ,O   ,X,O,O};
//        counters = rotateBoard(counters);
//
//        Board board = new Board(counters, new GameConfig(width, height, nInARowForWin));
//        int c = sirConnectALotMore.makeMove(board);
//        assertEquals(9, c);
//    }

    private Counter[][] rotateBoard(Counter[][] board) {
        Counter[][] newBoard = new Counter[board[0].length][board.length];
        for(int i=0; i<board[0].length; i++){
            for(int j=board.length-1; j>=0; j--){
                newBoard[i][j] = board[j][i];
            }
        }
        return newBoard;
    }
}
