package com.thg.accelerator19.connectn.ai.shaunhall.minimax;

public interface ActionState<T, S> {
  T getAction();
  S getState();
}
