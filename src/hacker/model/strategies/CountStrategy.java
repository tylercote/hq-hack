//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

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
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public interface CountStrategy {
  String execute(Trivia var1);

  static List<Result> search(String s) {
    String ENGINE_ID = "012636751765660145571:km7onz6wa0k";
    String API_KEY = "AIzaSyA8LxFISBMsvjnXLfshdtQfQdin9LT4y7s\n";
    Customsearch cs = null;
    Object resultList = new ArrayList();

    try {
      cs = new Customsearch(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
        public void initialize(HttpRequest httpRequest) throws IOException {
          try {
            httpRequest.setConnectTimeout(7000);
            httpRequest.setReadTimeout(7000);
          } catch (Exception var3) {
            var3.printStackTrace();
          }

        }
      });
    } catch (Exception var8) {
      var8.printStackTrace();
    }

    try {
      com.google.api.services.customsearch.Customsearch.Cse.List list = cs.cse().list(s);
      list.setKey(API_KEY);
      list.setCx(ENGINE_ID);
      Search results = (Search)list.execute();
      resultList = results.getItems();
    } catch (IOException var7) {
      var7.printStackTrace();
    }

    return (List)resultList;
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

  static void countsPerHitQuestionOnly(HashMap<String, Integer> counts, String content) {
    Iterator var2 = counts.entrySet().iterator();

    while(var2.hasNext()) {
      Entry<String, Integer> e = (Entry)var2.next();
      Pattern p = Pattern.compile(((String)e.getKey()).toLowerCase());
      Matcher m = p.matcher(content.toLowerCase());
      int currCount = ((Integer)e.getValue()).intValue();

      int count;
      for(count = 0; m.find(); ++count) {
        ;
      }

      e.setValue(count + currCount);
    }

  }

  static List<Result> countsPerHitQuestionAnswer(HashMap<String, Integer> counts, String query) {
    List<Result> allResults = new ArrayList();
    Iterator var3 = counts.entrySet().iterator();

    while(var3.hasNext()) {
      Entry<String, Integer> e = (Entry)var3.next();
      List<Result> results = search(query + " " + (String)e.getKey());
      allResults.addAll(results);
      String content = "";

      Result r;
      for(Iterator var7 = results.iterator(); var7.hasNext(); content = content.concat(r.getTitle() + " " + r.getSnippet() + " ")) {
        r = (Result)var7.next();
      }

      Pattern p = Pattern.compile(((String)e.getKey()).toLowerCase());
      Matcher m = p.matcher(content.toLowerCase());

      int count;
      for(count = 0; m.find(); ++count) {
        ;
      }

      e.setValue(count);
    }

    return allResults;
  }

  static String concatenator(ArrayList<String> words) {
    String result = "";

    for(int i = 0; i < words.size(); ++i) {
      if (i == words.size() - 1) {
        result = result.concat((String)words.get(i));
      } else {
        result = result.concat((String)words.get(i) + " ");
      }
    }

    return result;
  }

  static String loadWebpages(boolean isMax, Trivia t, HashMap<String, Integer> counts, List<String> urls) {
    System.out.println("Now loading individual webpages...");
    int count = 0;

    for(Iterator var5 = urls.iterator(); var5.hasNext(); ++count) {
      String url = (String)var5.next();
      if (count > 4) {
        break;
      }

      System.out.println(url);

      try {
        Document document = Jsoup.connect(url).userAgent("Mozilla").get();
        countsPerHitQuestionOnly(counts, document.body().wholeText());
        System.out.println("Option A: " + t.getO1() + " occurred " + counts.get(t.getO1()) + " times.");
        System.out.println("Option B: " + t.getO2() + " occurred " + counts.get(t.getO2()) + " times.");
        System.out.println("Option C: " + t.getO3() + " occurred " + counts.get(t.getO3()) + " times.");
        String ans;
        if (isMax) {
          ans = maxCountResult(counts, t);
        } else {
          ans = minCountResult(counts, t);
        }

        System.out.println("Most likely answer: " + ans);
      } catch (IOException var9) {
        System.out.println("Something happened. the webpage: " + url + " couldn't be opened.");
      }
    }

    String ans;
    if (isMax) {
      ans = maxCountResult(counts, t);
    } else {
      ans = minCountResult(counts, t);
    }

    System.out.println("Most likely answer: " + ans);
    return ans;
  }
}
