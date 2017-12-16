package hacker.model.strategies;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.customsearch.Customsearch;
import com.google.api.services.customsearch.model.Search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Compares the total webpages returned from each type of query.
 */
public class TotalCount implements CountStrategy {

  private ArrayList<String> fillers = new ArrayList<>(Arrays.asList(
          //"the"
  ));

  @Override
  public String execute(Trivia t) {
    HashMap<String, Long> counts = new HashMap<>();
    counts.put(t.getO1(), 0L);
    counts.put(t.getO2(), 0L);
    counts.put(t.getO3(), 0L);

    for (Map.Entry<String, Long> e : counts.entrySet()) {

      String query = constructQuery(t);
      System.out.println("Query: " + query  + "\n");
      long results = search(query + " " + e.getKey());
      e.setValue(results);
    }

    // Show title and URL of 1st result.
    //return "OCCURENCES:\n1: " + counts.get(t.o1) + "\n2: " + counts.get(t.o2) + "\n3: " + counts.get(t.o3);

    //FOR DEBUGGING:
    System.out.println("Option A: " + t.getO1() + "\nOccurrences: " + counts.get(t.getO1()) + "\n");
    System.out.println("Option B: " + t.getO2() + "\nOccurrences: " + counts.get(t.getO2()) + "\n");
    System.out.println("Option C: " + t.getO3() + "\nOccurrences: " + counts.get(t.getO3()) + "\n");

    if (counts.get(t.getO1()) > counts.get(t.getO2())
            && counts.get(t.getO1()) > counts.get(t.getO3())) {
      return t.getO1();
    }
    if (counts.get(t.getO2()) > counts.get(t.getO1())
            && counts.get(t.getO2()) > counts.get(t.getO3())) {
      return t.getO2();
    } else {
      return t.getO3();
    }

  }

  private String constructQuery(Trivia t) {

     ArrayList<String> questionArray =
     new ArrayList<String>(Arrays.asList(t.getQuestion().split(" ")));
     // because items can't be removed while iterating through
     ArrayList<String> toRemove = new ArrayList<>();
     for (String s : questionArray) {
     for (String st : this.fillers) {
     if (st.equals(s)) {
     toRemove.add(s);
     }
     }
     }
     questionArray.removeAll(toRemove);

     return this.concatenator(questionArray);

  }

  /**
   * Helper method to concatenate an array list of strings into one string (seperated by spaces)
   * @param words array list of words
   * @return string concatenation
   */
  private String concatenator(ArrayList<String> words) {
    String result = "";
    for (int i = 0; i < words.size(); i++) {
      if (i == words.size() - 1) {
        result = result.concat(words.get(i));
      }
      else {
        result = result.concat(words.get(i) + " ");
      }
    }
    return result;
  }

  /**
   * Gets the results of the given search.
   */
  private long search(String s) {

    String ENGINE_ID = "008732300561582678887:5l5n1mvojly";
    String API_KEY = "AIzaSyCeIBZcBJiRpw3O2WGWPen_-_KI2ZkHLUU";

    Customsearch cs = null;
    long numResults = 0;

    try {
      cs = new Customsearch(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
        @Override
        public void initialize(HttpRequest httpRequest) throws IOException {
          try {
            httpRequest.setConnectTimeout(7000);
            httpRequest.setReadTimeout(7000);
          }
          catch (Exception e) {
            e.printStackTrace();
          }
        }
      });
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    try {
      Customsearch.Cse.List list = cs.cse().list(s);
      list.setKey(API_KEY);
      list.setCx(ENGINE_ID);
      Search results = list.execute();
      numResults = results.getSearchInformation().getTotalResults();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    return numResults;
  }
}
