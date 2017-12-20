//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package hacker.model;

import hacker.model.strategies.CountStrategy;
import hacker.model.strategies.Trivia;

/**
 * The model interface. Bam ba bam bam!!
 */
public interface IHackModel {
  String cheat(CountStrategy var1) throws IllegalArgumentException;

  Trivia getTrivia();

  void setTrivia(Trivia var1) throws IllegalArgumentException;
}
