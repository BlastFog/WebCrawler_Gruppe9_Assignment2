package at.gr6.test;

import at.gr6.crawler.LanguageTagConverter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class LanguageTagConverterTest {


    @ParameterizedTest
    @MethodSource("languagesAndCodes")
    void getFullLanguage(String languageCode,String expectedfullLanguage) {
        String actualFullLanguage = LanguageTagConverter.getFullLanguage(languageCode);
        assertEquals(expectedfullLanguage,actualFullLanguage);
    }

    private static Stream<Arguments> languagesAndCodes(){
        return Stream.of(
                Arguments.of("bg","Bulgarian"),
                Arguments.of("cs","Czech"),
                Arguments.of("da","Danish"),
                Arguments.of("de","German"),
                Arguments.of("el","Greek"),
                Arguments.of("en","English"),
                Arguments.of("en-gb","English (British)"),
                Arguments.of("EN-US","English (American)"),
                Arguments.of("ES","Spanish"),
                Arguments.of("ET","Estonian"),
                Arguments.of("FI","Finnish"),
                Arguments.of("FR","French"),
                Arguments.of("HU","Hungarian"),
                Arguments.of("ID","Indonesian"),
                Arguments.of("IT","Italian"),
                Arguments.of("JA","Japanese"),
                Arguments.of("KO","Korean"),
                Arguments.of("LT","Lithuanian"),
                Arguments.of("LV","Latvian"),
                Arguments.of("NB","Norwegian (Bokmål)"),
                Arguments.of("NL","Dutch"),
                Arguments.of("PL","Polish"),
                Arguments.of("PT-BR","Portuguese (Brazilian)"),
                Arguments.of("PT-PT","Portuguese (all Portuguese varieties excluding Brazilian Portuguese)"),
                Arguments.of("RO","Romanian"),
                Arguments.of("RU","Russian"),
                Arguments.of("SK","Slovak"),
                Arguments.of("SL","Slovenian"),
                Arguments.of("SV","Swedish"),
                Arguments.of("TR","Turkish"),
                Arguments.of("UK","Ukrainian"),
                Arguments.of("ZH","Chinese (simplified)"),
                Arguments.of("","Not enough information")
        );
    }
}