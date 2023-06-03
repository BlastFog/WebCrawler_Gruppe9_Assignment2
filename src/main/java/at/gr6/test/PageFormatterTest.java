package at.gr6.test;

import at.gr6.crawler.Header;
import at.gr6.crawler.Page;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PageFormatterTest {

    @Mock
    Page page = new Page;
    ArrayList<Header> headerList;
    ArrayList<String> linkList;
    String expecdtedOutput = "\"### ->Sample Header\\n<br> -->link to <a>https://orf.at/news</a>\\n\""

    @BeforeEach
    void setUp() {
        headerList = new ArrayList<>();
        headerList.add(new Header("Sample Header",3));
        page.setHeaderStringList(headerList);
        page.setSubPages();

    }

    @Test
    void testGenerateOutputString(){


    }
}