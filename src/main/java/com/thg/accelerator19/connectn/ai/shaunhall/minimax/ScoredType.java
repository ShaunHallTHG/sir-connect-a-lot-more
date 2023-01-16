package com.thg.accelerator19.connectn.ai.shaunhall.minimax;

public class ScoredType<T> {
    private T t;
    private double score;

    public ScoredType(T t, double score) {
        this.t = t;
        this.score = score;
    }

    public T getT() {
        return t;
    }

    public double getScore() {
        return score;
    }
}
