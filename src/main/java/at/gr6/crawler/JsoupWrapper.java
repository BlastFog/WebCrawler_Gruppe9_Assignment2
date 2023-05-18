package at.gr6.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class JsoupWrapper {

    private Elements headers;
    private Elements links;
    private Document doc;

    public JsoupWrapper(){
    }

    public JsoupWrapper(Elements headers, Elements links){
        this.headers = headers;
        this.links = links;
    }

    public void readWebPage(String url) throws Exception {
        connectViaJSoup(url);
        this.links = doc.select("a[href]");
        this.headers = doc.select("h1,h2,h3,h4,h5,h6");
    }
    private void connectViaJSoup(String url) throws IOException {
        this.doc = Jsoup.connect(url).get();
    }
    public ArrayList<Header> getHeadersList(){
        ArrayList<Header> headerList = new ArrayList<>();
        for(Element header: this.headers){
            int headerGrade = Integer.parseInt(""+header.tagName().charAt(1));
            headerList.add(new Header(header.text(),headerGrade));
        }
        return headerList;
    }
    public ArrayList<String> getLinkList(){
        ArrayList<String> linkList = new ArrayList<>();
        for(Element link: this.links)
            linkList.add(link.attr("abs:href"));
        return linkList;
    }

}
