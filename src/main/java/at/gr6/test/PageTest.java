package at.gr6.test;

import at.gr6.crawler.Header;
import at.gr6.crawler.Page;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PageTest {
    Page page;
    String url = "https://orf.at/";
    Header sampleHeader = new Header("Sample Header",1);
    int depth = 1;

    @BeforeEach
    void setUp() {
        page = new Page(url, depth);
    }

    @Test
    void testConstructor() {
        Page page1 = new Page(url, depth);
        assertTrue(page1.equals(page));
        Page page2 = new Page("wrong url", 2);
        assertFalse(page2.equals(page));
    }

    @Test
    void testHeaderStringList() {
        ArrayList<Header> headerList = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            sampleHeader = new Header("Sample Header"+i,1);
            headerList.add(sampleHeader);
        }
        page.setHeaderStringList(headerList);

        ArrayList<Header> actualHeaderStringList = page.getHeaderList();
        for (int i = 0; i < 10; i++)
            assertEquals(headerList.get(i), actualHeaderStringList.get(i));
    }

    @Test
    void testIsBroken() {
        assertFalse(page.isBroken());
    }

    @Test
    void testSetBroken() {
        page.setBroken(true);
        assertTrue(page.isBroken());
    }

    @Test
    void testSetSubPages() {
        ArrayList<String> linkList = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            linkList.add(url + i);
        }
        page.setSubPages(linkList);
        int index = 0;
        for (Page subPage : page.getSubPage()) {
            assertEquals(linkList.get(index), subPage.getUrl());
            assertEquals(depth + 1, subPage.getDepth());
            index++;
        }
    }

   }