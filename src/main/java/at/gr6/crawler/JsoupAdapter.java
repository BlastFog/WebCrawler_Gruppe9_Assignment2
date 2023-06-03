package at.gr6.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;


public class JsoupAdapter implements JsoupProvider {
    private Document doc;

    @Override
    public void connect(String url) throws IOException {
        this.doc = Jsoup.connect(url).get();
    }
   @Override
   public Elements select(String selector) {
       return doc.select(selector);
   }
}
