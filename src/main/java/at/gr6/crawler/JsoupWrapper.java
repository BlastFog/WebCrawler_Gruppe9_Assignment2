package at.gr6.crawler;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class JsoupWrapper implements CrawlerWrapper{
    private Elements headers;
    private Elements links;
    private JsoupProvider jsoupProvider;
    private final static int TAG_NAME_INDEX = 1;

    public JsoupWrapper() {
        this.jsoupProvider = new JsoupAdapter();
    }

    /**
     * This is a constructor for testing purposes
     *
     * @param headers A list of headers
     * @param links A list of links
     */
    public JsoupWrapper(Elements headers, Elements links) {
        this.jsoupProvider = new JsoupAdapter();
        this.headers = headers;
        this.links = links;
    }

    public void readWebPage(String url) throws IOException {
        jsoupProvider.connect(url);
        this.links = jsoupProvider.select("a[href]");
        this.headers = jsoupProvider.select("h1,h2,h3,h4,h5,h6");
    }

    public ArrayList<Header> getHeadersList() {
        ArrayList<Header> headerList = new ArrayList<>();
        for (Element header : this.headers) {
            int headerGrade = getHeaderGrade(header);
            if (!header.text().equals(""))
                headerList.add(new Header(header.text(), headerGrade));
        }
        return headerList;
    }

    private int getHeaderGrade(Element header) {
        return Integer.parseInt("" + header.tagName().charAt(TAG_NAME_INDEX));
    }

    public ArrayList<String> getLinkList() {
        ArrayList<String> linkList = new ArrayList<>();
        for (Element link : this.links)
            linkList.add(link.attr("abs:href"));
        return linkList;
    }
}
