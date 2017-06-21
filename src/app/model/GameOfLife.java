package app.model;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GameOfLife {
    public int width = 100;
    public int height = 100;
    public List<Boolean> board = new ArrayList<>(Collections.nCopies(width * height, false));
    //TODO: change to proper values of either height or width
    private int[] offsets = new int[]{-1, 1, -height, -(height - 1), -(height + 1), height - 1, height + 1, height};


    public void updateBoard() {
        board = IntStream.range(0, board.size())
                .mapToObj(this::isAlive)
                .collect(Collectors.toList());
    }

    public void setField(int width, int height, boolean value) {
        board.set(convertCoord(width, height), value);
    }

    public void clearBoard() {
        board = new ArrayList<>(Collections.nCopies(width * height, false));
    }

    private boolean isAlive(int field) {
        return calculateNeighbours(field) == 3 || (calculateNeighbours(field) == 2 && board.get(field));
    }

    private long calculateNeighbours(int field) {
        return Arrays
                .stream(offsets)
                .mapToObj(x -> board.get(convertToPeriodic(field, x)))
                .filter(p -> p).count();
    }

    private int convertToPeriodic(int field, int offset) {
        // if(field%width==0 && (field-offset)%width==width-1) return (field-offset)+width;
        return (field - offset) < 0 ? (width * height) + (field - offset) : (field - offset) % (width * height);
    }

    private int convertCoord(int width, int height) {
        return height * this.width + width;
    }

}
