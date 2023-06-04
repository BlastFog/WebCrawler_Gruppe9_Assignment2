package at.gr6.test;

import at.gr6.crawler.Header;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeaderTest {
    Header header;

    String headerString = "Sample Header";
    int headerGrade = 3;

    @BeforeEach
    void setUp() {
        header = new Header(headerString,headerGrade);
    }

    @Test
    void getHeaderString() {
        assertEquals(headerString,header.getHeaderString());
    }

    @Test
    void getHeaderGrade() {
        assertEquals(headerGrade,header.getHeaderGrade());
    }
}