package at.gr6.crawler;

import java.io.IOException;
import java.util.ArrayList;

public interface CrawlerWrapper {
    void readWebPage(String url) throws IOException;
    ArrayList<Header> getHeadersList();
    ArrayList<String> getLinkList();
}
