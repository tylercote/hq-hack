package hacker.model;

import hacker.model.strategies.CountStrategy;
import hacker.model.strategies.Trivia;

/**
 * The model interface. Bam ba bam bam!!
 */
public interface IHackModel {

  /**
   * Runs the search based on the inputted strategy and returns the result based on that search.
   *
   * @param strat the type of strategy that we will use based on the type of question that is asked
   * @return the correct answer as concluded by the google god
   * @throws IllegalArgumentException if the input is null
   */
  String cheat(CountStrategy strat) throws IllegalArgumentException;

  /**
   * Getter for the piece of trivia in the model.
   *
   * @return the model's trivia
   */
  Trivia getTrivia();

  /**
   * Setter for the model's trivia.
   *
   * @param t the piece of trivia we are setting as the model's
   * @throws IllegalArgumentException if the given piece of trivia is null
   */
  void setTrivia(Trivia t) throws IllegalArgumentException;
}
