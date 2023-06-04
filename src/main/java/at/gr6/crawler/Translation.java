package at.gr6.crawler;

import com.deepl.api.DeepLException;

public interface Translation {
    void translatePage(Page page) throws DeepLException, InterruptedException;
    String getSourceLang();
    String getTargetLang();
    void setDetectedLanguage();
}
