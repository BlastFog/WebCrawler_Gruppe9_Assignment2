package at.gr6.crawler;

import java.util.HashMap;

public class LanguageStatisticsManager {
    private static HashMap<String, Integer> languageStatistics = new HashMap<>();

    public static void updateLanguageStatistics(String detectedLanguage) {
        if (!languageStatistics.containsKey(detectedLanguage))
            languageStatistics.put(detectedLanguage, 1);
        else languageStatistics.put(detectedLanguage, languageStatistics.get(detectedLanguage) + 1);
    }

    public static String getMostCommonLanguage() {
        int max = 0;
        String lang = "";
        for (String language : languageStatistics.keySet()) {
            int count = languageStatistics.get(language);
            if (count > max) {
                max = count;
                lang = language;
            }
        }
        return lang;
    }

    public static HashMap<String, Integer> getLanguageStatistics() {
        return languageStatistics;
    }
}
