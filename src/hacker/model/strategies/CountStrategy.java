package hacker.model.strategies;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.customsearch.Customsearch;
import com.google.api.services.customsearch.Customsearch.*;
import com.google.api.services.customsearch.model.Result;
import com.google.api.services.customsearch.model.Search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Interface to hold all the strategies we determine which queries we are searching for. I.e., for a
 * negated question "Which of the following is NOT" we would generally default to checking for the
 * minimum number of hits of a certain option. Conversely, "Which of the follow IS" would default to
 * the option generating the maximum number of hits.
 */
public interface CountStrategy {

  /**
   * Executes the appropriate counting strategy for determining the correct answer to this piece of
   * trivia.
   *
   * @param t the question and answer that we are searching about
   * @return the correct answer!
   */
  String execute(Trivia t);

  /**
   * Searched the given string on Google database with our search engine.
   *
   * @param query the string being searched
   * @return a list of results of the search
   */
  static List<Result> search(String query) {
    String ENGINE_ID = "012636751765660145571:km7onz6wa0k";
    String API_KEY = "AIzaSyA8LxFISBMsvjnXLfshdtQfQdin9LT4y7s\n";
    Customsearch searchEngine = null;
    List<Result> resultList = new ArrayList();

    try {
      searchEngine = new Builder(new NetHttpTransport(),
              new JacksonFactory(),
              new HttpRequestInitializer() {
                public void initialize(HttpRequest httpRequest) throws IOException {
                  try {
                    httpRequest.setConnectTimeout(7000);
                    httpRequest.setReadTimeout(7000);
                  } catch (Exception e) {
                    e.printStackTrace();
                  }

                }
              }).setApplicationName("Dan's search engine.").build();
      /*
      searchEngine =
              new Customsearch(
              new NetHttpTransport(),
              new JacksonFactory(),
              new HttpRequestInitializer() {
                public void initialize(HttpRequest httpRequest) throws IOException {
                  try {
                    httpRequest.setConnectTimeout(7000);
                    httpRequest.setReadTimeout(7000);
                  } catch (Exception e) {
                    e.printStackTrace();
                  }

                }
              });
      */
    } catch (Exception e) {
      e.printStackTrace();
    }

    try {
      Customsearch.Cse.List search = searchEngine.cse().list(query);
      search.setKey(API_KEY);
      search.setCx(ENGINE_ID);
      Search results = search.execute();
      resultList = results.getItems();
    } catch (IOException e) {
      System.out.println("Search failed. No results have been returned.");
    }
    return resultList;
  }

  /**
   * Returns the answer if we are looking for the result with the HIGHEST number of hits.
   *
   * @param counts a map detailing how many hits each string answer returned with a google search
   * @param t      the piece of trivia we are dealing with, which contains the String keys as
   *               answers
   * @return the most likely answer based on the Google search
   */
  static String maxCountResult(HashMap<String, Integer> counts, Trivia t) {
    String answer;
    if ((counts.get(t.getO1())) >= (counts.get(t.getO2()))
            && (counts.get(t.getO1())) >= (counts.get(t.getO3()))) {
      answer = t.getO1();
    } else if ((counts.get(t.getO2())) >= (counts.get(t.getO1()))
            && (counts.get(t.getO2())) >= (counts.get(t.getO3()))) {
      answer = t.getO2();
    } else {
      answer = t.getO3();
    }
    return answer;
  }

  /**
   * Returns the answer if we are looking for the result with the LOWEST number of hits.
   *
   * @param counts a map detailing how many hits each string answer returned with a google search
   * @param t      the piece of trivia we are dealing with, which contains the String keys as
   *               answers
   * @return the most likely answer based on the Google search
   */
  static String minCountResult(HashMap<String, Integer> counts, Trivia t) {
    String answer;
    if ((counts.get(t.getO1())) <= (counts.get(t.getO2()))
            && (counts.get(t.getO1())) <= (counts.get(t.getO3()))) {
      answer = t.getO1();
    } else if ((counts.get(t.getO2())) <= (counts.get(t.getO1()))
            && (counts.get(t.getO2())) <= (counts.get(t.getO3()))) {
      answer = t.getO2();
    } else {
      answer = t.getO3();
    }
    return answer;
  }

  /**
   * Modifies the inputted map to show the actual number of hits per answer option; used when
   * searching by question-answer pairs rather than question-only. This method conducts the actual
   * search within the body and checks the number of hits in real time.
   *
   * @param counts the map of answer options to the number of times that answer appeared in the
   *               search
   * @param query  the query we will perform a Google search with
   * @return a list of all the results
   */
  static List<Result> countsPerHitQuestionAnswer(HashMap<String, Integer> counts, String query) {
    List<Result> allResults = new ArrayList<>();
    for (Map.Entry<String, Integer> e : counts.entrySet()) {
      List<Result> results = CountStrategy.search(query + " " + e.getKey());
      allResults.addAll(results);
      String content = "";
      for (Result r : results) {
        content = content.concat(r.getTitle() + " " + r.getSnippet() + " ");
      }
      Pattern p = Pattern.compile(e.getKey().toLowerCase());
      Matcher m = p.matcher(content.toLowerCase());
      int count = 0;
      while (m.find()) {
        count++;
      }
      e.setValue(count);
    }
    return allResults;
  }

  /**
   * Modifies the inputted map to show the actual number of hits per answer option; used when
   * searching by question-only.
   *
   * @param counts  the map of answer options to the number of times that answer appeared in the
   *                search
   * @param content the body of the search in a string format - this is parsed for occurrences of an
   *                answer
   */
  static void countsPerHitQuestionOnly(HashMap<String, Integer> counts, String content) {
    for (Map.Entry<String, Integer> e : counts.entrySet()) {
      Pattern p = Pattern.compile(e.getKey().toLowerCase());
      Matcher m = p.matcher(content.toLowerCase());
      int currCount = e.getValue();
      int count = 0;
      while (m.find()) {
        count++;
      }
      e.setValue(count + currCount);
    }
  }

  /**
   * Loads web pages turned up by the Google search if the results are deemed not satisfactory to
   * determine a confident answer.
   *
   * @param isMax  whether we are searching for a negated question (presence of NOT or NEVER) or
   *               not
   * @param t      the piece of trivia we are processing
   * @param counts the map of question options to the number of hits each option has had,
   *               cumulative
   * @param urls   the list of urls we are loading
   * @return the most likely answer, now based on loading urls in addition to the search
   */
  static String loadWebpages(boolean isMax, Trivia t, HashMap<String, Integer> counts,
                             List<String> urls) {
    System.out.println("Now loading individual webpages...");
    int count = 0;
    for (String url : urls) {
      if (count > 5) {
        break;
      }
      System.out.println(url);
      Document document;
      try {
        document = Jsoup.connect(url).userAgent("Mozilla").get();
        CountStrategy.countsPerHitQuestionOnly(counts, document.body().wholeText());
        System.out.println("Option A: " + t.getO1() + " occurred " + counts.get(t.getO1()) + " times.");
        System.out.println("Option B: " + t.getO2() + " occurred " + counts.get(t.getO2()) + " times.");
        System.out.println("Option C: " + t.getO3() + " occurred " + counts.get(t.getO3()) + " times.");
        String ans;
        if (isMax) {
          ans = CountStrategy.maxCountResult(counts, t);
        } else {
          ans = CountStrategy.minCountResult(counts, t);
        }
        System.out.println("Most likely answer: " + ans);
      } catch (IOException e) {
        System.out.println("Something happened. the webpage: " + url + " couldn't be opened.");
      }
      count++;
    }

    String ans;
    if (isMax) {
      ans = CountStrategy.maxCountResult(counts, t);
    } else {
      ans = CountStrategy.minCountResult(counts, t);
    }
    System.out.println("Most likely answer: " + ans);
    return ans;
  }

  /**
   * Helper method to concatenate an array list of strings into one string (separated by spaces)
   *
   * @param words array list of words
   * @return string concatenation
   */
  static String concatenator(ArrayList<String> words) {
    String result = "";
    for (int i = 0; i < words.size(); i++) {
      if (i == words.size() - 1) {
        result = result.concat(words.get(i));
      } else {
        result = result.concat(words.get(i) + " ");
      }
    }
    return result;
  }
}
