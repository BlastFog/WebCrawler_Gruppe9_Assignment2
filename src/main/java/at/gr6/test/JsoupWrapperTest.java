package at.gr6.test;

import at.gr6.crawler.CrawlerWrapper;
import at.gr6.crawler.Header;
import at.gr6.crawler.JsoupWrapper;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JsoupWrapperTest {
    static CrawlerWrapper jsoupWrapper;
    static Element mockElement;
    static Elements mockHeaderElements;
    static Elements mockLinkElements;

    @BeforeEach
    public void setup(){
        jsoupWrapper = new JsoupWrapper();
        mockElement = mock(Element.class);
        mockHeaderElements = new Elements(mockElement);
        mockLinkElements = new Elements(mockElement);
    }

    @Test
    void testReadWebPage() throws Exception {
        mockStatic(Jsoup.class);
        String url = "https://example.com";
        Connection mockConnection = mock(Connection.class);
        Document mockDocument = mock(Document.class);
        Elements mockHeaderElements = mock(Elements.class);
        Elements mockLinkElements = mock(Elements.class);

        when(Jsoup.connect(url)).thenReturn(mockConnection);
        when(mockConnection.get()).thenReturn(mockDocument);
        when(mockDocument.select("a[href]")).thenReturn(mockLinkElements);
        when(mockDocument.select("h1,h2,h3,h4,h5,h6")).thenReturn(mockHeaderElements);

        jsoupWrapper.readWebPage(url);

        verify(mockDocument).select("a[href]");
        verify(mockDocument).select("h1,h2,h3,h4,h5,h6");
    }

    @Test
    void testGetHeadersList() {
        when(mockElement.tagName()).thenReturn("h1");
        when(mockElement.text()).thenReturn("Header");

        jsoupWrapper = new JsoupWrapper(mockHeaderElements,mockLinkElements);

        ArrayList<Header> headerList = jsoupWrapper.getHeadersList();

        assertEquals(1,headerList.size());
        assertEquals("Header",headerList.get(0).getHeaderString());
    }


    @Test
    void testGetLinkList() {
        when(mockElement.attr("abs:href")).thenReturn("https://example.com");

        jsoupWrapper = new JsoupWrapper(mockHeaderElements,mockLinkElements);

        ArrayList<String> linkList = jsoupWrapper.getLinkList();

        assertEquals(1,linkList.size());
        assertEquals("https://example.com",linkList.get(0));
    }
}