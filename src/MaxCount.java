import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.customsearch.Customsearch;
import com.google.api.services.customsearch.model.Result;
import com.google.api.services.customsearch.model.Search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MaxCount implements CountStrategy {

  private ArrayList<String> fillers = new ArrayList<>(Arrays.asList(
          //"the"
  ));

  @Override
  public String execute(Trivia t) {
    HashMap<String, Integer> counts = new HashMap<>();
    counts.put(t.o1, 0);
    counts.put(t.o2, 0);
    counts.put(t.o3, 0);
    String query = constructQuery(t);

    for (Map.Entry<String, Integer> e : counts.entrySet()) {
      List<Result> results = search((query) + " " + e.getKey());
      String content = "";
      for (Result r : results) {
        content = content.concat(r.getTitle() + " " + r.getSnippet() + " ");
      }

      //prints out content being checked
      System.out.println("Results for " + e.getKey() + " search ---------------------\n" + content + "\n");

      Pattern p = Pattern.compile(e.getKey().toLowerCase());
      Matcher m = p.matcher(content.toLowerCase());
      int count = 0;
      while (m.find()){
        count++;
      }
      e.setValue(count);
    }

    // Show title and URL of 1st result.
    //return "OCCURENCES:\n1: " + counts.get(t.o1) + "\n2: " + counts.get(t.o2) + "\n3: " + counts.get(t.o3);

    //FOR DEBUGGING:
    System.out.println("Option A: " + t.o1 + "\nOccurrences: " + counts.get(t.o1) + "\n");
    System.out.println("Option B: " + t.o2 + "\nOccurrences: " + counts.get(t.o2) + "\n");
    System.out.println("Option C: " + t.o3 + "\nOccurrences: " + counts.get(t.o3) + "\n");

    if (counts.get(t.o1) > counts.get(t.o2) && counts.get(t.o1) > counts.get(t.o3)) {
      return t.o1;
    }
    if (counts.get(t.o2) > counts.get(t.o1) && counts.get(t.o2) > counts.get(t.o3)) {
      return t.o2;
    } else {
      return t.o3;
    }

  }

  private String constructQuery(Trivia t) {
    ArrayList<String> questionArray = new ArrayList<String>(Arrays.asList(t.question.split(" ")));
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
  private List<Result> search(String s) {

    String ENGINE_ID = "008732300561582678887:5l5n1mvojly";
    String API_KEY = "AIzaSyCeIBZcBJiRpw3O2WGWPen_-_KI2ZkHLUU";

    Customsearch cs = null;
    List<Result> resultList = null;

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
      resultList = results.getItems();
    }
    catch (IOException e) {
      e.printStackTrace();
    }

    return resultList;
  }
}
