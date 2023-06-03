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

    public TranslationManager(String targetLangTag, boolean translate, String authKey) throws DeepLException, InterruptedException {
        this.translator = new Translator(authKey);
        this.targetLangTag = targetLangTag;
        this.targetLang = LanguageTagConverter.getFullLanguage(targetLangTag);
        this.translate = translate;
        this.languageStatisticsProvider = new LanguageStatisticsManager();
    }

    public void translatePage(Page page) throws DeepLException, InterruptedException {
        if (translate) {
            TextResult result;
            ArrayList<Header> headerList = page.getHeaderList();
            for (Header header : headerList) {
                result = translator.translateText(header.getHeaderString(), sourceLangTag, targetLangTag);
                String detectedLanguage = result.getDetectedSourceLanguage();
                header.setHeaderString(result.getText());
                this.languageStatisticsProvider.updateLanguageStatistics(detectedLanguage);
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
        this.sourceLangTag = this.languageStatisticsProvider.getMostCommonLanguage();
        this.sourceLang = LanguageTagConverter.getFullLanguage(this.sourceLangTag);
    }
}
