// Interface to represent the desired operations for a HackModel
public interface HackModelOperations {
  /**
   * Using the google API, finds the most probable answer to the given trivia question.
   * @param t
   * @return
   */
  String cheat(Trivia t);

  /**
   * Get the search term to be used for the given trivia.
   */
  String searchTerm(Trivia t);

}
