package com.thg.accelerator19.connectn.ai.shaunhall.connectn;

import com.thehutgroup.accelerator.connectn.player.*;
import com.thg.accelerator19.connectn.ai.shaunhall.minimax.Minimax;

import java.util.HashMap;
import java.util.stream.IntStream;

public class SirConnectALotMore extends Player {
  private Minimax<Integer, Board, Counter> minimax;
  private int minDepth;
  private int maxDepth;

  public SirConnectALotMore(Counter counter, GameConfig gameConfig, int minDepth, int maxDepth) {
    super(counter, SirConnectALotMore.class.getName());
    minimax = new Minimax<>(new BoardStateGenerator(gameConfig), new BoardAnalyser(gameConfig));
    this.minDepth = minDepth;
    this.maxDepth = maxDepth;
  }

  public SirConnectALotMore(Counter counter, GameConfig gameConfig) {
    this(counter, gameConfig, 7, 40);
  }

  @Override
  public int makeMove(Board board) {
    int bestMove = 0;
    long startTime = System.currentTimeMillis();
    HashMap<Counter[][], TranspositionEntry> table = new HashMap<>();
    for (int i = minDepth; i <= maxDepth; i++) {
      System.out.println("Starting with depth " + i);
      try {
        Integer latestMove = minimax
            .minimaxDecision(board, getCounter(), getCounter().getOther(), i, table, startTime);
        if (latestMove != null) {
          bestMove = latestMove;
          System.out.println("best move " + bestMove);
        } else {
          System.out.println("Didn't complete iteration");
          return bestMove;
        }
      } catch (RuntimeException e) {
        System.out.println("stopped part-way through");
        return bestMove;
      }
    }
    return bestMove;
  }

  private int numNonFullColumns(Board board) {
    return (int) IntStream.range(0, board.getConfig().getWidth())
        .filter(
            c -> !board.hasCounterAtPosition(new Position(c, board.getConfig().getHeight() - 1)))
        .count();
  }
}
