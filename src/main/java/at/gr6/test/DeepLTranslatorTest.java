package at.gr6.test;

import at.gr6.crawler.*;
import com.deepl.api.DeepLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


class DeepLTranslatorTest {
    static Translation deepLTranslator;
    @BeforeEach
    public void setup() {
        LanguageStatisticsProvider languageStatistics = new LanguageStatistics();
        deepLTranslator = new DeepLTranslator("en-GB",true,"56a1abfc-d443-0e69-8963-101833b4014e:fx",languageStatistics);
    }

    @Test
    void testTranslatePage() throws DeepLException, InterruptedException {
        String translated1 = "Welcome to this test page";
        String translated2 = "This is a test";

        Page testPage = new Page("https://example.com",1);
        ArrayList<Header> headerList = new ArrayList<>();
        headerList.add(new Header("Willkommen auf dieser Test Seite",1));
        headerList.add(new Header("Das ist ein Test",1));
        testPage.setHeaderStringList(headerList);

        deepLTranslator.translatePage(testPage);

        assertEquals(testPage.getHeaderList().get(0).getHeaderString(),translated1);
        assertEquals(testPage.getHeaderList().get(1).getHeaderString(),translated2);
    }

    @Test
    void testGetSourceLang() throws DeepLException, InterruptedException {
        testTranslatePage();
        deepLTranslator.setDetectedLanguage();
        assertEquals("German", deepLTranslator.getSourceLang());
    }

    @Test
    void testGetTargetLang() {
        assertEquals("English (British)", deepLTranslator.getTargetLang());
    }


}