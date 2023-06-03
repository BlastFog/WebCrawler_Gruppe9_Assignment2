package at.gr6.crawler;

import java.io.IOException;

public interface JsoupProvider {
    void connect(String url) throws IOException;
    <T> T select(String selector);
}