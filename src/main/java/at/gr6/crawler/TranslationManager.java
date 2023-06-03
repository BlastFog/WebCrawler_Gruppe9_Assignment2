package at.gr6.crawler;

import com.deepl.api.DeepLException;
import com.deepl.api.TextResult;
import com.deepl.api.Translator;

import java.util.ArrayList;

public class TranslationManager {
    private Translator translator;
    private String sourceLangTag;
    private String targetLangTag;
    private String sourceLang;
    private String targetLang;
    private boolean translate;
    private LanguageStatisticsProvider languageStatisticsProvider;

    public TranslationManager(String targetLangTag, boolean translate, String authKey, LanguageStatisticsProvider languageStatisticsProvider) throws DeepLException, InterruptedException {
        this.translator = new Translator(authKey);
        this.targetLangTag = targetLangTag;
        this.targetLang = LanguageTagConverter.getFullLanguage(targetLangTag);
        this.translate = translate;
        this.languageStatisticsProvider = languageStatisticsProvider;
    }

    public void translatePage(Page page) throws DeepLException, InterruptedException {
        if (translate) {
            TextResult result;
            ArrayList<Header> headerList = page.getHeaderList();
            for (Header header : headerList) {
                result = translator.translateText(header.getHeaderString(), sourceLangTag, targetLangTag);
                String detectedLanguage = result.getDetectedSourceLanguage();
                header.setHeaderString(result.getText());
                languageStatisticsProvider.updateLanguageStatistics(detectedLanguage);           // SRP?
            }
        }
    }


    public String getSourceLang() {
        return this.sourceLang;
    }

    public String getTargetLang() {
        return this.targetLang;
    }

    public void setDetectedLanguage() {
        this.sourceLangTag = languageStatisticsProvider.getMostCommonLanguage();
        this.sourceLang = LanguageTagConverter.getFullLanguage(this.sourceLangTag);
    }

}
