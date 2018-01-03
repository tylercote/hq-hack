import org.junit.ComparisonFailure;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import hacker.controller.HackController;
import hacker.controller.IHackController;
import hacker.model.IHackModel;
import hacker.model.strategies.ComprehensiveCount;
import hacker.model.strategies.Count;
import hacker.model.strategies.CountQuestionOnly;
import hacker.model.strategies.CountStrategy;
import hacker.model.strategies.EvenMoreComprehensiveCount;
import hacker.model.strategies.QuoteSearch;
import hacker.model.strategies.Trivia;
import hacker.model.HackModel;
import hacker.view.HackView;
import hacker.view.IHackView;

import static org.junit.Assert.assertEquals;

/**
 * TEST TEST TEST!!!
 */
public class TestCases {

  /*
  To search with Min/MaxCount (standard)
   */
  QASet q1 = new QASet(new Trivia("Aside from the iconic \"The Birth of Venus,\" in which other of these Botticelli paintings does Venus appear?",
          "The Annunciation", "Bardi Altarpiece", "Primavera"),
          "Primavera");
  QASet q2 = new QASet(new Trivia("Courgette and aubergine are names for what kind of food?",
          "Cheese", "Wines", "Vegetables"),
          "Vegetables");
  QASet q3 = new QASet(new Trivia("Who staged the original 1976 adaptation of \"The Phantom of the Opera\"?",
          "Ken Hill", "Andrew Lloyd Webber", "Gaston Leroux"),
          "Ken Hill");
  QASet q4 = new QASet(new Trivia("Who is the author of \"The picture of Dorian Gray\"?",
          "Edgar Allen Poe", "Oscar Wilde", "Charles Dickens"),
          "Oscar Wilde");
  QASet q5 = new QASet(new Trivia("From which service did people in the 90's hear \"You've got mail!\"?",
          "AOL", "Netflix", "Kiki's Delivery Service"),
          "AOL");
  QASet q8 = new QASet(new Trivia("What device stops an elevator from running beyond its rated speed?",
          "Overspeed Governor", "Landing doors", "Roller Guide"),
          "Overspeed Governor");
  QASet q9 = new QASet(new Trivia("Which Broadway play is considered to be the world's first real musical?",
          "Listen Lester", "A Trip to Chinatown", "The Black Crook"),
          "The Black Crook");
  QASet q12 = new QASet(new Trivia("Veronica, Betty & Jughead are characters from which comic book franchise?",
          "East of West", "Watchmen", "Archie"),
          "Archie");
  QASet q13 = new QASet(new Trivia("Crystals growing inside a hollow rock are known as what?",
          "Geode", "Moraine", "Fizzgig"),
          "Geode");
  QASet q14 = new QASet(new Trivia("Which sauce has pine nuts as an ingredient?",
          "Ponzu", "Pesto", "Tahini"),
          "Pesto");
  QASet q15 = new QASet(new Trivia("Which animal's head appears on the label of Gordon's Gin?",
          "Eagle", "Boar", "Stag"),
          "Boar");
  QASet q18 = new QASet(new Trivia("Who has played a secret agent in a movie, TV movie and cartoon?",
          "Roger Moore", "Vin Diesel", "Don Adams"),
          "Don Adams");
  QASet q19 = new QASet(new Trivia("What U.S. town has a music venue which allows Americans to watch live, in-person concerts from Canada?",
          "Derby Line, VT", "Portal, ND", "Niagara Falls, NY"),
          "Derby Line, VT");
  QASet q20 = new QASet(new Trivia("Which of these sci-fi franchises came first?",
          "Buck Rogers", "Flash Gordon", "Godzilla"),
          "Buck Rogers");
  QASet q21 = new QASet(new Trivia("Alfred Hitchcock's \"Psycho\" was the first Hollywood film to ever to do what?", // this line was misspelled in the original question
          "Show a toilet flush", "Use human blood", "Kill its lead character"),
          "Show a toilet flush");
  QASet q23 = new QASet(new Trivia("Which character speaks the opening monoloque of \"The Godfather\"?",
          "Tom Hagen", "Vito Corleone", "Bonasera"),
          "Bonasera");
  QASet q24 = new QASet(new Trivia("What are the first three words in the King James Version of the Bible?",
          "In the beginning", "The heavens declare", "The almighty God"),
          "In the beginning");
  QASet q26 = new QASet(new Trivia("Which of these atmospheric layers of the Earth is the highest?",
          "Mesosphere", "Stratosphere", "Thermosphere"),
          "Thermosphere");
  QASet q27 = new QASet(new Trivia("What was the last Canadian team to win back-to-back NHL championships?",
          "Toronto Maple Leafs", "Edmonton Oilers", "Montreal Canadiens"),
          "Edmonton Oilers");
  /*
  Throws mysterious null pointer
  QASet q29 = new QASet(new Trivia("\"Which of these schools is NOT a member of the Ivy League\"?",
          "Johns Hopkins", "Dartmouth", "Yale"),
          "Johns Hopkins");
          */
  QASet q30 = new QASet(new Trivia("Which of these apps is designed to help with travel plans?",
          "TripAdvisor", "LastPass", "iTunes"),
          "TripAdvisor");
  QASet q31 = new QASet(new Trivia("Spike TV's \"MXC\" from mid-2000s was a spoof of which Japanese show?",
          "Castle Invadors Go!", "Hell Castle", "Takeshi's Castle"),
          "Takeshi's Castle");
  QASet q33 = new QASet(new Trivia("\"Xenogenesis\" is the first directing credit by which famous filmmaker?",
          "George Lucas", "Steven Spielberg", "James Cameron"),
          "James Cameron");
  QASet q34 = new QASet(new Trivia("Which word in English means the same in French, Russian, German, Afrikaans, & Lithuanian?",
          "Automobile", "Super", "Computer"),
          "Super");
  QASet q35 = new QASet(new Trivia("Punchnep is a root vegetable dish indigenous to which country?",
          "Wales", "Northern Ireland", "Scotland"),
          "Wales");
  QASet q36 = new QASet(new Trivia("Which Hollywood film's title was translated for French release as \"Sexy Dance\"?",
          "Dirty Dancing", "Step Up", "Black Swan"),
          "Step Up");
  QASet q37 = new QASet(new Trivia("Which of the following dinosaurs lived during the Jurassic period?",
          "Triceratops", "Plateosaurus", "Stegosaurus"),
          "Stegosaurus");
  QASet q38 = new QASet(new Trivia("Who has the most hits in the history of professional baseball",
          "Ty Cobb", "Ichiro Suzuki", "Pete Rose"),
          "Ichiro Suzuki");
  QASet q39 = new QASet(new Trivia("Who is the only journalist to win the Pulitzer Prize more than three times?",
          "Carol Guzy", "David Barstow", "Amy Archer"),
          "Carol Guzy");
  QASet q40 = new QASet(new Trivia("The First Transcontinental Railroad was previously known as what?",
          "Western Route", "Overland Route", "Great Pacific Railroad"),
          "Overland Route");
  QASet q41 = new QASet(new Trivia("Which British author had two of his novels made into films by two different pairs of brothers?",
          "Ian McEwan", "Nick Hornby", "Irvine Welsh"),
          "Nick Hornby");
  QASet q42 = new QASet(new Trivia("Which of these tends to be bigger?",
          "Alpine glacier", "Iceberg", "Ice sheet"),
          "Ice sheet");
  QASet q44 = new QASet(new Trivia("Sen. Mitch McConnell is married to the current Secretary of what department?",
          "Homeland Security", "Education", "Transportation"),
          "Transportation");
  QASet q45 = new QASet(new Trivia("What do the stars Alnilam, Alnitak, and Mintaka make?",
          "Draco's Tail", "Little Dipper's Handle", "Orion's Belt"),
          "Orion's Belt");
  QASet q46 = new QASet(new Trivia("Pigments found in what part of a flamingo's diet determine its color?",
          "Melanin", "Chromatophore", "Carotenoid"),
          "Carotenoid");
  QASet q47 = new QASet(new Trivia("Which of these pairs of states forms a border within the United States?",
          "Missouri / Mississippi", "Illinois / Tennessee", "Iowa / South Dakota"),
          "Iowa / South Dakota");
  QASet q48 = new QASet(new Trivia("What did the Joint Photographic Experts Group invent in 1992?",
          "JPEG", "ZIP", "GIF"),
          "JPEG");
  QASet q49 = new QASet(new Trivia("Three of the top seven best-performing stocks of the last year were in which industry?",
          "Steel", "Financial services", "Tech"),
          "Steel");
  /*
  Negated questions
   */
  QASet q1negated = new QASet(new Trivia("Which of these foods does not come in truffle form?",
          "Chocolate", "Celery", "Mushroom"),
          "Celery");
  QASet q2negated = new QASet(new Trivia("For which franchise has Telltale Games studio NOT made a video game?",
          "Batman", "Transformers", "Game of Thrones"),
          "Transformers");
  QASet q3negated = new QASet(new Trivia("Which feature is NOT included on a gilet jacket?",
          "Sleeves", "Collar", "Pockets"),
          "Sleeves");
  QASet q4negated = new QASet(new Trivia("Which of these countries was not involved in the Battle of the Bulge",
          "Canada", "Luxembourg", "Italy"),
          "Italy");
  QASet q5negated = new QASet(new Trivia("Who is NOT part of the ownership group of the future MLS team Los Angeles FC?",
          "Landon Donovan", "Tony Robbins", "Will Ferrell"),
          "Landon Donovan");
  QASet q6negated = new QASet(new Trivia("Which of these is NOT a competing group in the Westminster Dog Show?",
          "Working Group", "Toy Group", "Mutt Group"),
          "Mutt Group");

  /*
  To search with Question ONLY - characterized by an instance of "What is" within the question
  somewhere
   */
  QASet q1QO = new QASet(new Trivia("What is the award given out for game shows and soap operas on TV?",
          "Daytime emmy awards", "Academy awards", "Grammy awards"),
          "Daytime emmy awards");
  QASet q3QO = new QASet(new Trivia("What is the fastest sanctioned car racing sport in the world?",
          "Moto GP", "Top Fuel Drag", "Formula 1"),
          "Top Fuel Drag");
  QASet q5QO = new QASet(new Trivia("From base-to-peak, what is the tallest mountain in the world?",
          "Mt. Everest", "Mauna Kea", "K2"),
          "Mauna Kea");
  QASet q6QO = new QASet(new Trivia("What is the origin of Radiohead's name?",
          "Talking Heads song", "War of the Worlds", "Andy Warhol installation"),
          "Talking Heads song");
  QASet q7QO = new QASet(new Trivia("What is the capital of Kentucky?",
          "Louisville", "Frankfort", "Frankfurt"),
          "Frankfort");

  /*
  To Search with quote search - only if quote > 5 words
   */
  QASet q1quote = new QASet(new Trivia("\"If this is your idea of Christmas, I gotta be here for New Year's\" is the last line of what movie?",
          "Die Hard", "Love, Actually", "Home Alone"),
          "Die Hard");
  QASet q2quote = new QASet(new Trivia("\"Oh! What a tangled web we weave when first we practice to deceive\" is from which poet?",
          "Sir Walter Scott", "Robert Browning", "William Shakespeare"),
          "Sir Walter Scott");

  /*
  Lists of QASets divided up by the type of question they are, and similarly the type of search they
  require to solve
   */

  ArrayList<QASet> wed12_20_17_3 =
          new ArrayList<>(Arrays.asList(q35, q36, q37, q38, q39, q40, q41));

  ArrayList<QASet> wed12_20_17_9 =
          new ArrayList<>(Arrays.asList(q42, q44, q45, q46, q47));

  ArrayList<QASet> testSuite =
          new ArrayList<>(Arrays.asList(
                  q1, q2, q3, q4, q5, q8, q9, q12, q13, q14, q15, q18, q19, q20,
                  q21, q23, q24, q26, q27, q30, q31, q33, q34, q35, q36, q38, q39, q40, q48, q49));

  ArrayList<QASet> negatedTestSuite =
          new ArrayList<>(Arrays.asList(
                  q1negated, q2negated, q3negated, q4negated, q5negated, q6negated));

  ArrayList<QASet> quoteTestSuite =
          new ArrayList<>(Arrays.asList(
                  q1quote, q2quote));

  ArrayList<QASet> questionOnlyTestSuite =
          new ArrayList<>(Arrays.asList(
                  q1QO, q3QO, q5QO, q6QO, q7QO));

  ArrayList<QASet> allTests = new ArrayList<>();

  ArrayList<QASet> failingTestSuite = new ArrayList<>();

  //============================== End of data init, now begin tests ===============================
  //============================== Tests for searching algorithms ==================================

  @Test
  public void runEverything() {
    this.getAllTests();
    this.runTheseTestsSpecial(allTests);
    System.out.println("Tests completed.");
    System.out.println("tests failed: " + failingTestSuite.size() + " out of " + allTests.size()
            + " total.");
    System.out.println("Accuracy: " + failingTestSuite.size() / allTests.size());
    for (QASet q : failingTestSuite) {
      System.out.println(q.trivia.getQuestion());
    }
  }

  @Test
  public void runTestSuiteComprehensive() {
    this.runTheseTests(new EvenMoreComprehensiveCount(true), testSuite);
    System.out.println("Tests completed.");
    System.out.println("tests failed: " + failingTestSuite.size());
    for (QASet q : failingTestSuite) {
      System.out.println(q.trivia.getQuestion());
    }
  }

  @Test
  public void runToday() {
    this.runTheseTestsSpecial(wed12_20_17_3);
    this.runTheseTestsSpecial(wed12_20_17_9);
    System.out.println("Tests completed.");
    System.out.println("tests failed: " + failingTestSuite.size());
    for (QASet q : failingTestSuite) {
      System.out.println(q.trivia.getQuestion());
    }
  }

  @Test
  public void runTestSuitesMinCount() {
    this.runTheseTests(new EvenMoreComprehensiveCount(false), negatedTestSuite);
    System.out.println("Tests completed.");
    System.out.println("tests failed: " + failingTestSuite.size());
    for (QASet q : failingTestSuite) {
      System.out.println(q.trivia.getQuestion());
    }
  }

  @Test
  public void runTestSuitesMaxCountQO() {
    this.runTheseTests(new ComprehensiveCount(true), questionOnlyTestSuite);
    System.out.println("Tests completed.");
    System.out.println("tests failed: " + failingTestSuite.size());
    for (QASet q : failingTestSuite) {
      System.out.println(q.trivia.getQuestion());
    }
  }

  @Test
  public void runTestSuitesQuoteCount() {
    this.runTheseTests(new QuoteSearch(), quoteTestSuite);
    System.out.println("Tests completed.");
    System.out.println("tests failed: " + failingTestSuite.size());
    for (QASet q : failingTestSuite) {
      System.out.println(q.trivia.getQuestion());
    }
  }

  //======================================= Tests for OCR ==========================================
  @Test
  public void testTextReco() {
    assertEquals("What U.S. town has a music venue which allows Americans to watch live, in-person concerts from Canada?",
            IHackController.pictureToText("C:\\Users\\tyler\\IntelliJWorkspace\\hq-hack\\hq-hack\\images\\question.jpg"));
  }

  @Test
  public void testScreenshots() {
    IHackModel m = new HackModel(new Trivia("", "", "", ""));
    IHackView v = new HackView();
    IHackController c = new HackController(m, v);
    c.run("Doesn't matter");
  }

  /**
   * Initializes allTests with all of the tests.
   */
  private void getAllTests() {
    allTests.addAll(wed12_20_17_3);
    allTests.addAll(wed12_20_17_9);
    allTests.addAll(testSuite);
    allTests.addAll(negatedTestSuite);
    allTests.addAll(quoteTestSuite);
    allTests.addAll(questionOnlyTestSuite);
  }

  /**
   * Helper method that runs tests on the given list of QASets, with the strategy determined by the
   * trivia, rather that inputted.
   *
   * @param list  the list of question-answer pairs to test
   */
  private void runTheseTestsSpecial(ArrayList<QASet> list) {
    for (QASet qa : list) {
      System.out.println("=======================================================================");
      System.out.println("\n\n\nQuestion: " + qa.trivia.getQuestion());
      IHackModel hack = new HackModel(qa.trivia);
      try {
        assertEquals(hack.cheat(qa.trivia.getStrat()), qa.answer);
      } catch (ComparisonFailure f) {
        failingTestSuite.add(qa);
      }
    }
  }

  /**
   * Helper method that runs tests on the given list of QASets, with the given strategy.
   *
   * @param strat the strategy to test the question-answer pairs by
   * @param list  the list of question-answer pairs to test
   */
  private void runTheseTests(CountStrategy strat, ArrayList<QASet> list) {
    for (QASet qa : list) {
      System.out.println("=======================================================================");
      System.out.println("\n\n\nQuestion: " + qa.trivia.getQuestion());
      IHackModel hack = new HackModel(qa.trivia);
      try {
        assertEquals(hack.cheat(strat), qa.answer);
      } catch (ComparisonFailure f) {
        failingTestSuite.add(qa);
      }
    }
  }
}
