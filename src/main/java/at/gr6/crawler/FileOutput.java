package at.gr6.crawler;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileOutput {
    private final String path;
    private final FileWriter fileWriter;



    public static void clearFile(String path) throws IOException {
        PrintWriter writer = new PrintWriter(path);
        writer.print("");
        writer.close();
    }


    public FileOutput(String path) throws IOException {
        this.path = path;
        fileWriter = new FileWriter(this.path,true);
    }

    public void writeBeginning(Page p) throws IOException {
        fileWriter.write("-----START OF FILE-----\n");
        fileWriter.write("input: <a>" + p.getUrl() + "</a>");
        fileWriter.write("\n");
    }

    public void writeLanguage(Translation l) throws IOException {
        fileWriter.write("<br>source language: " + l.getSourceLang());
        fileWriter.write("\n");
        fileWriter.write("<br>target language: " + l.getTargetLang());
        fileWriter.write("\n");
        fileWriter.write("<br>summary: ");
        fileWriter.write("\n");
    }

    public void writeBody(Page p) throws IOException {
        fileWriter.write(p.getformattedPage());
    }

    public void closeFile() throws IOException {
        fileWriter.write("\n-----END OF FILE-----\n");
        fileWriter.close();
    }
    public void  closeWriter() throws IOException {
        fileWriter.close();
    }
}
