package com.thg.accelerator19.connectn.ai.shaunhall.minimax;

public interface StateAnalyser<S, P> {
  StateInfo analyseState(S state, P player);
}
