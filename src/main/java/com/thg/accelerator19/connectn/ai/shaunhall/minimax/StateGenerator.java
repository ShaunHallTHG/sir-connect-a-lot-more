package com.thg.accelerator19.connectn.ai.shaunhall.minimax;

import java.util.List;

public interface StateGenerator<T, S, P> {
  List<ActionState<T, S>> getActionStates(S state, P player);
}
