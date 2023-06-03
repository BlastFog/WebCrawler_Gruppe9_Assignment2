package at.gr6.crawler;

public class LanguageTagConverter {
    public static String getFullLanguage(String langTag) {
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
