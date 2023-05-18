package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import com.deepl.DeepLClient;
import com.deepl.util.Language;

import javax.swing.text.Document;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        //DeepLClient client = new DeepLClient();
        String url ="orf.at";
        Document doc = Jsoup.connect(url).get();
    }
}