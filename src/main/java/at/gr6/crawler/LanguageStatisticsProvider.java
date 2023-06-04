package at.gr6.crawler;


public interface LanguageStatisticsProvider {
    void updateLanguageStatistics(String detectedLanguage);
    String getMostCommonLanguage();
}
