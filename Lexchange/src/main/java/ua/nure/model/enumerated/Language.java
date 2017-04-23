package ua.nure.model.enumerated;

import java.util.Arrays;

public enum Language {
    Azerbaijan("Azerbaijan", "az"),
    Albanian("Albanian", "sq"),
    Amharic("Amharic", "am"),
    English("English", "en"),
    Arabic("Arabic", "ar"),
    Armenian("Armenian", "hy"),
    Afrikaans("Afrikaans", "af"),
    Basque("Basque", "eu"),
    Bashkir("Bashkir", "ba"),
    Belarusian("Belarusian", "be"),
    Bengali("Bengali", "bn"),
    Bulgarian("Bulgarian", "bg"),
    Bosnian("Bosnian", "bs"),
    Welsh("Welsh", "cy"),
    Hungarian("Hungarian", "hu"),
    Vietnamese("Vietnamese", "vi"),
    HaitianCreole("Haitian (Creole)", "ht"),
    Galician("Galician", "gl"),
    Dutch("Dutch", "nl"),
    HillMari("Hill Mari", "mrj"),
    Greek("Greek", "el"),
    Georgian("Georgian", "ka"),
    Gujarati("Gujarati", "gu"),
    Danish("Danish", "da"),
    Hebrew("Hebrew", "he"),
    Yiddish("Yiddish", "yi"),
    Indonesian("Indonesian", "id"),
    Irish("Irish", "ga"),
    Italian("Italian", "it"),
    Icelandic("Icelandic", "is"),
    Spanish("Spanish", "es"),
    Kazakh("Kazakh", "kk"),
    Kannada("Kannada", "kn"),
    Catalan("Catalan", "ca"),
    Kyrgyz("Kyrgyz", "ky"),
    Chinese("Chinese", "zh"),
    Korean("Korean", "ko"),
    Xhosa("Xhosa", "xh"),
    Latin("Latin", "la"),
    Latvian("Latvian", "lv"),
    Lithuanian("Lithuanian", "lt"),
    Luxembourgish("Luxembourgish", "lb"),
    Malagasy("Malagasy", "mg"),
    Malay("Malay", "ms"),
    Malayalam("Malayalam", "ml"),
    Maltese("Maltese", "mt"),
    Macedonian("Macedonian", "mk"),
    Maori("Maori", "mi"),
    Marathi("Marathi", "mr"),
    Mari("Mari", "mhr"),
    Mongolian("Mongolian", "mn"),
    German("German", "de"),
    Nepali("Nepali", "ne"),
    Norwegian("Norwegian", "no"),
    Punjabi("Punjabi", "pa"),
    Papiamento("Papiamento", "pap"),
    Persian("Persian", "fa"),
    Polish("Polish", "pl"),
    Portuguese("Portuguese", "pt"),
    Romanian("Romanian", "ro"),
    Russian("Russian", "ru"),
    Cebuano("Cebuano", "ceb"),
    Serbian("Serbian", "sr"),
    Sinhala("Sinhala", "si"),
    Slovakian("Slovakian", "sk"),
    Slovenian("Slovenian", "sl"),
    Swahili("Swahili", "sw"),
    Sundanese("Sundanese", "su"),
    Tajik("Tajik", "tg"),
    Thai("Thai", "th"),
    Tagalog("Tagalog", "tl"),
    Tamil("Tamil", "ta"),
    Tatar("Tatar", "tt"),
    Telugu("Telugu", "te"),
    Turkish("Turkish", "tr"),
    Udmurt("Udmurt", "udm"),
    Uzbek("Uzbek", "uz"),
    Ukrainian("Ukrainian", "uk"),
    Urdu("Urdu", "ur"),
    Finnish("Finnish", "fi"),
    French("French", "fr"),
    Hindi("Hindi", "hi"),
    Croatian("Croatian", "hr"),
    Czech("Czech", "cs"),
    Swedish("Swedish", "sv"),
    Scottish("Scottish", "gd"),
    Estonian("Estonian", "et"),
    Esperanto("Esperanto", "eo"),
    Javanese("Javanese", "jv"),
    Japanese("Japanese", "ja");

    private String name;

    private String code;

    Language(String name, String code){
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Language getByName(String name) {
        return Arrays.asList(values()).stream()
                .filter(l -> l.getName().equals(name))
                .findAny().get();
    }

    public static Language getByCode(String code) {
        return Arrays.asList(values()).stream()
                .filter(l -> l.getCode().equals(code))
                .findAny().orElseGet(() -> null);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
