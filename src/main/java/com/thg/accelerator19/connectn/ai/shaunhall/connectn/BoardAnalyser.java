package com.thg.accelerator19.connectn.ai.shaunhall.connectn;

import com.thehutgroup.accelerator.connectn.player.*;
import com.thg.accelerator19.connectn.ai.shaunhall.minimax.StateAnalyser;
import com.thg.accelerator19.connectn.ai.shaunhall.minimax.StateInfo;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class BoardAnalyser implements StateAnalyser<Board, Counter> {
    private Function<Position, Position> hMover = p -> new Position(p.getX() + 1, p.getY());
    private Function<Position, Position> vMover = p -> new Position(p.getX(), p.getY() + 1);
    private Function<Position, Position> diagUpRightMover = hMover.compose(vMover);
    private Function<Position, Position> diagUpLeftMover = p -> new Position(p.getX() - 1, p.getY() + 1);
    private Map<Function<Position, Position>, List<Position>> positionsByFunction;
    private int[][][] fourArray;

    public BoardAnalyser(GameConfig config) {
        positionsByFunction = new HashMap<>();
        List<Position> leftEdge = IntStream.range(0, config.getHeight())
                .mapToObj(Integer::new)
                .map(i -> new Position(0, i))
                .collect(Collectors.toList());
        List<Position> bottomEdge = IntStream.range(0, config.getWidth())
                .mapToObj(Integer::new)
                .map(i -> new Position(i, 0))
                .collect(Collectors.toList());
        List<Position> rightEdge = leftEdge.stream()
                .map(p -> new Position(config.getWidth() - 1, p.getY()))
                .collect(Collectors.toList());

        List<Position> leftBottom = Stream.concat(leftEdge.stream(),
                bottomEdge.stream()).distinct().collect(Collectors.toList());
        List<Position> rightBottom = Stream.concat(rightEdge.stream(),
                bottomEdge.stream()).distinct().collect(Collectors.toList());

        positionsByFunction.put(hMover, leftEdge);
        positionsByFunction.put(vMover, bottomEdge);
        positionsByFunction.put(diagUpRightMover, leftBottom);
        positionsByFunction.put(diagUpLeftMover, rightBottom);

        ArrayList<int[][]> fours = new ArrayList<>();
        for (Map.Entry<Function<Position, Position>, List<Position>> entry : positionsByFunction.entrySet()) {
            Function<Position, Position> function = entry.getKey();
            List<Position> startPositions = entry.getValue();
            for (var start : startPositions) {
                List<Position> positions = new LinkedList<>();
                Position currentPos = start;
                while (isWithinBoard(currentPos, config)) {
                    positions.add(currentPos);
                    currentPos = function.apply(currentPos);
                }

                if (positions.size() >= 4) {
                    for (int i = 0; i < (positions.size() - 3); i++) {
                        fours.add(new int[][]{
                                new int[]{
                                        positions.get(i).getX(), positions.get(i).getY()
                                },
                                new int[]{
                                        positions.get(i+1).getX(), positions.get(i+1).getY()
                                },
                                new int[]{
                                        positions.get(i+2).getX(), positions.get(i+2).getY()
                                },
                                new int[]{
                                        positions.get(i+3).getX(), positions.get(i+3).getY()
                                }
                        });
                    }
                }
            }
        }
        fourArray = fours.toArray(new int[fours.size()][][]);
    }

    private boolean isWithinBoard(Position position, GameConfig config) {
        return position.getX() >= 0 && position.getX() < config.getWidth() && position.getY() >= 0 && position.getY() < config.getHeight();
    }

    @Override
    public StateInfo analyseState(Board state, Counter counter) {
        return new StateInfo(getUtility(counter, state), state);
    }

    private double getUtility(Counter utilityCounter, Board board) {
        //note: draw not considered
        //todo: optimise
        double oscore = 0;
        for (var line : fourArray) {
            int os = 0;
            int xs = 0;
            Counter co = null;
            for (int j = 0; j < 4; j++) {
                co = BoardHacker.getCounterQuickly(board, line[j][0], line[j][1]);
                if (co == null) {
                    continue;
                } else {
                    if (co == Counter.O) {
                        os++;
                    } else {
                        xs++;
                    }
                }
            }
            if ((os > 0 && xs > 0) || (xs == 0 && os == 0)) {
                continue;
            } else {
                double rawScore = 0;
                switch (xs + os) {
                    case 1:
                        rawScore = 1;
                        break;
                    case 2:
                        rawScore = 5;
                        break;
                    case 3:
                        rawScore = 12;
                        break;
                    case 4:
                        return co == utilityCounter ? 10e9 : -10e9;
                }
                if (xs > 0) {
                    rawScore *= -1;
                }
                oscore += rawScore;
            }

        }
        if (utilityCounter == Counter.O) {
            return oscore;
        } else {
            return -oscore;
        }
        //return 0;
//      Map<Counter, int[]> promisingCountsInARow = getPromisingCountsInARow(board);
//      return utilityOfMaxRunLengths(promisingCountsInARow, counter, board.getConfig().getnInARowForWin());

    }
}
