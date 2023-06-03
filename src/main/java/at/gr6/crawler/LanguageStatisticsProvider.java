package at.gr6.crawler;

import java.util.HashMap;

public interface LanguageStatisticsProvider {
    void updateLanguageStatistics(String detectedLanguage);
    String getMostCommonLanguage();
    HashMap<String, Integer> getLanguageStatistics();
}
