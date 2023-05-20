package at.gr6.crawler;

import com.deepl.api.DeepLException;
import com.deepl.api.TextResult;
import com.deepl.api.Translator;

import java.util.ArrayList;
import java.util.HashMap;

public class Translation {
    private Translator translator;
    private String sourceLangTag;
    private String targetLangTag;
    private String sourceLang;
    private String targetLang;
    static HashMap<String, Integer> languageStatistics;
    private boolean translate;

    public Translation(String targetLangTag, boolean translate, String authKey) throws DeepLException, InterruptedException {
        this.translator = new Translator(authKey);
        this.targetLangTag = targetLangTag;
        this.targetLang = getFullLanguage(targetLangTag);
        this.translate = translate;
        this.languageStatistics = new HashMap<>();
    }

    public void translatePage(Page page) throws DeepLException, InterruptedException {
        if (translate) {
            TextResult result;
            ArrayList<Header> headerList = page.getHeaderList();
            for (Header header : headerList) {
                result = translator.translateText(header.getHeaderString(), sourceLangTag, targetLangTag);
                String detectedLanguage = result.getDetectedSourceLanguage();
                header.setHeaderString(result.getText());
                updateLanguageStatistics(detectedLanguage);
            }
        }
    }


    public String getSourceLang() {
        return this.sourceLang;
    }

    public String getTargetLang() {
        return this.targetLang;
    }

    private void updateLanguageStatistics(String detectedLanguage) {
        if (!languageStatistics.containsKey(detectedLanguage))
            languageStatistics.put(detectedLanguage, 1);
        else languageStatistics.put(detectedLanguage, languageStatistics.get(detectedLanguage) + 1);
    }

    public void setDetectedLanguage() {
        int max = 0;
        String lang = "";
        for (String i : languageStatistics.keySet()) {
            int val = languageStatistics.get(i);
            if (val >= max) {
                max = val;
                lang = i;
            }
        }
        this.sourceLangTag = lang;
        this.sourceLang = getFullLanguage(lang);
    }

    /*private String getFullLanguage(String langTag) throws DeepLException, InterruptedException {
        if (langTag.equals("en"))        // replace
            langTag = "en-GB";
        for (com.deepl.api.Language l : translator.getTargetLanguages()) {
            if (l.getCode().toLowerCase().equals(langTag.toLowerCase())) {
                return l.getName();
            }
        }
        return null;
    }*/

    private String getFullLanguage(String langTag) {
        String sourceLanguage;
        switch (langTag.toUpperCase()) {
            case "BG":
                sourceLanguage = "Bulgarian";
                break;
            case "CS":
                sourceLanguage = "Czech";
                break;
            case "DA":
                sourceLanguage = "Danish";
                break;
            case "DE":
                sourceLanguage = "German";
                break;
            case "EL":
                sourceLanguage = "Greek";
                break;
            case "EN-GB":
                sourceLanguage = "English (British)";
                break;
            case "EN":
                sourceLanguage = "English";
                break;
            case "EN-US":
                sourceLanguage = "English (American)";
                break;
            case "ES":
                sourceLanguage = "Spanish";
                break;
            case "ET":
                sourceLanguage = "Estonian";
                break;
            case "FI":
                sourceLanguage = "Finnish";
                break;
            case "FR":
                sourceLanguage = "French";
                break;
            case "HU":
                sourceLanguage = "Hungarian";
                break;
            case "ID":
                sourceLanguage = "Indonesian";
                break;
            case "IT":
                sourceLanguage = "Italian";
                break;
            case "JA":
                sourceLanguage = "Japanese";
                break;
            case "KO":
                sourceLanguage = "Korean";
                break;
            case "LT":
                sourceLanguage = "Lithuanian";
                break;
            case "LV":
                sourceLanguage = "Latvian";
                break;
            case "NB":
                sourceLanguage = "Norwegian (Bokmål)";
                break;
            case "NL":
                sourceLanguage = "Dutch";
                break;
            case "PL":
                sourceLanguage = "Polish";
                break;
            case "PT-BR":
                sourceLanguage = "Portuguese (Brazilian)";
                break;
            case "PT-PT":
                sourceLanguage = "Portuguese (all Portuguese varieties excluding Brazilian Portuguese)";
                break;
            case "RO":
                sourceLanguage = "Romanian";
                break;
            case "RU":
                sourceLanguage = "Russian";
                break;
            case "SK":
                sourceLanguage = "Slovak";
                break;
            case "SL":
                sourceLanguage = "Slovenian";
                break;
            case "SV":
                sourceLanguage = "Swedish";
                break;
            case "TR":
                sourceLanguage = "Turkish";
                break;
            case "UK":
                sourceLanguage = "Ukrainian";
                break;
            case "ZH":
                sourceLanguage = "Chinese (simplified)";
                break;
            default:
                sourceLanguage = "Not enough information";
        }
        return sourceLanguage;
    }
}
