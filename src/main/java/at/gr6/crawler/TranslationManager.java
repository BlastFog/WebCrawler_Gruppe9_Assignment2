package at.gr6.crawler;

import com.deepl.api.DeepLException;
import com.deepl.api.TextResult;
import com.deepl.api.Translator;

import java.util.ArrayList;
import java.util.HashMap;

public class TranslationManager {
    private Translator translator;
    private String sourceLangTag;
    private String targetLangTag;
    private String sourceLang;
    private String targetLang;
    private boolean translate;

    public TranslationManager(String targetLangTag, boolean translate, String authKey) throws DeepLException, InterruptedException {
        this.translator = new Translator(authKey);
        this.targetLangTag = targetLangTag;
        this.targetLang = LanguageTagConverter.getFullLanguage(targetLangTag);
        this.translate = translate;
    }

    public void translatePage(Page page) throws DeepLException, InterruptedException {
        if (translate) {
            TextResult result;
            ArrayList<Header> headerList = page.getHeaderList();
            for (Header header : headerList) {
                result = translator.translateText(header.getHeaderString(), sourceLangTag, targetLangTag);
                String detectedLanguage = result.getDetectedSourceLanguage();
                header.setHeaderString(result.getText());
                LanguageStatisticsManager.updateLanguageStatistics(detectedLanguage);           // SRP?
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
        this.sourceLangTag = LanguageStatisticsManager.getMostCommonLanguage();
        this.sourceLang = LanguageTagConverter.getFullLanguage(this.sourceLangTag);
    }

}
