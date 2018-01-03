package hacker.model.strategies;

/**
 * The type of question-searching method we assign to each question. QUOTE is used for a question
 * with a quote > 5 words long. MAXQO is used for a question-only search (MINQO is negated version),
 * MAX is used for a standard max-hit search (MIN is negated version).
 */
public enum QuestionType {
  QUOTE,
  MAXQO,
  MINQO,
  MAX,
  MIN
}
