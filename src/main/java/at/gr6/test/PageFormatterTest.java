package at.gr6.test;

import at.gr6.crawler.Header;
import at.gr6.crawler.Page;
import at.gr6.crawler.PageFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PageFormatterTest {
    Page page;
    ArrayList<Header> headerList;
    ArrayList<String> linkList;
    String expecdtedOutput = "### ->Sample Header\n<br> -->link to <a>https://orf.at/news</a>\n";
    String expecdtedBrokenOutput = "### ->Sample Header\n<br> -->broken link <a>https://orf.at/news</a>\n";

    PageFormatter formatter;

    @BeforeEach
    void setUp() {
        page = new Page("url",1);
        setupHeaderList();
        setupSubPage();
        formatter = new PageFormatter(page);
    }
    private void setupHeaderList(){
        headerList = new ArrayList<>();
        headerList.add(new Header("Sample Header",3));
        page.setHeaderStringList(headerList);
    }
    private void setupSubPage(){
        linkList = new ArrayList<>();
        linkList.add("https://orf.at/news");
        page.setSubPages(linkList);


    }

    @Test
    void testGenerateOutputString() throws Exception {
        formatter.generateOutputString();
        assertEquals(expecdtedOutput,formatter.getOutputString());
    }
    @Test
    void testBrokenLink() throws Exception {
        page.getSubPage().get(0).setBroken(true);
        formatter.generateOutputString();
        assertEquals(expecdtedBrokenOutput,formatter.getOutputString());
    }
    @Test
    void testOutputException(){
       Exception exception = assertThrows(Exception.class,()->formatter.getOutputString());
       assertEquals("Output String must be generated first",exception.getMessage());
    }
}