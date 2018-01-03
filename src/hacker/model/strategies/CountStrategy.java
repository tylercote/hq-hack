package hacker.model.strategies;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.customsearch.Customsearch;
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

public interface CountStrategy {
  String execute(Trivia t);

  static List<Result> search(String s) {
    String ENGINE_ID = "012636751765660145571:km7onz6wa0k";
    String API_KEY = "AIzaSyA8LxFISBMsvjnXLfshdtQfQdin9LT4y7s\n";
    Customsearch cs = null;
    List<Result> resultList = new ArrayList();

    try {
      cs = new Customsearch(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
        public void initialize(HttpRequest httpRequest) throws IOException {
          try {
            httpRequest.setConnectTimeout(7000);
            httpRequest.setReadTimeout(7000);
          } catch (Exception e) {
            e.printStackTrace();
          }

        }
      });
    } catch (Exception e) {
      e.printStackTrace();
    }

    try {
      com.google.api.services.customsearch.Customsearch.Cse.List list = cs.cse().list(s);
      list.setKey(API_KEY);
      list.setCx(ENGINE_ID);
      Search results = list.execute();
      resultList = results.getItems();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return resultList;
  }

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

  static String loadWebpages(boolean isMax, Trivia t, HashMap<String, Integer> counts, List<String> urls) {
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
