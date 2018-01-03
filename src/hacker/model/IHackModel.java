package hacker.model;

import hacker.model.strategies.CountStrategy;
import hacker.model.strategies.Trivia;

public interface IHackModel {

  String cheat(CountStrategy strat) throws IllegalArgumentException;

  Trivia getTrivia();

  void setTrivia(Trivia t) throws IllegalArgumentException;
}
