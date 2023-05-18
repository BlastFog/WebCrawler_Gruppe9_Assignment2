package at.gr6.test;

import at.gr6.crawler.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FileOutputTest {

    String path = "writer_test.md";



    @Mock
    Page pageMock = mock(Page.class);

    Page page;

    @Mock
    Translation translationMock = mock(Translation.class);

    FileOutput fileOutput;



    FileReader reader;
    BufferedReader bufferedReader;

    @BeforeEach
    void setUp() throws IOException {
        fileOutput = new FileOutput(path);



        //pageMock = new Page("https://orf.at/",1);
    }

    private void generateSamplePage(){
        page = new Page("https://orf.at/",1);
        ArrayList<String> linkList = new ArrayList<>();
        linkList.add("https://orf.at/news");
        ArrayList<Header> headerList = new ArrayList<>();
        headerList.add(new Header("Sample Header",3));
        page.setSubPages(linkList);
        page.setHeaderStringList(headerList);
    }

    @Test
    void writeBeginning() throws IOException {

        when(pageMock.getUrl()).thenReturn("https://orf.at/");
        fileOutput.writeBeginning(pageMock);
        fileOutput.closeWriter();
        String actual = readTest();
        String expected = "-----START OF FILE-----\ninput: <a>https://orf.at/</a>\n";
        assertEquals(expected,actual);
    }

    @Test
    void writeLanguage() throws IOException {
        when(translationMock.getSourceLang()).thenReturn("German");
        when(translationMock.getTargetLang()).thenReturn("English(British)");
        fileOutput.writeLanguage(translationMock);
        fileOutput.closeWriter();
        String actual = readTest();
        String expected = "<br>source language: German\n<br>target language: English(British)\n<br>summary: \n";
        assertEquals(expected,actual);
    }

    @Test
    void writeBody() throws Exception {
        generateSamplePage();
        assertDoesNotThrow(() -> fileOutput.writeBody(page));
        fileOutput.closeWriter();
        String actual = readTest();
        String expected = "### ->Sample Header\n<br> -->link to <a>https://orf.at/news</a>\n";
        assertEquals(expected,actual);


    }

    @Test
    void closeFile() throws IOException {
        assertDoesNotThrow(()-> fileOutput.closeFile());
        String actual = readTest();
        String expected = "\n-----END OF FILE-----\n";
        assertEquals(expected,actual);


    }
    private String readTest() throws IOException {
        reader = new FileReader(path);
        bufferedReader = new BufferedReader(reader);
        int i;
        String result = "";
        while((i=bufferedReader.read())!=-1){
            result+=(char)i;
        }
        bufferedReader.close();
        return result;

    }

    @AfterEach
    void tearDown() throws IOException {
        File f = new File(path);
        assertTrue(f.delete());

    }
}