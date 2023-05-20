package at.gr6.crawler;

import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ExceptionLogger {

    private static ExceptionLogger OBJ;

    /*static {
        try {
            OBJ = new ExceptionLogger();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }*/

    private static Logger logger;
    private static FileHandler fh;

    private ExceptionLogger() throws IOException {
        initializeLogger();
        System.out.println("Constructor");
    }
    public static ExceptionLogger getInstance() throws IOException {
        synchronized (Logger.class) {
            if(OBJ == null){
                OBJ = new ExceptionLogger();
            }
        }
        return OBJ;
    }

    public static void initializeLogger() throws IOException {
        logger = java.util.logging.Logger.getLogger("Error Log");
        fh = new FileHandler("./report.md",true);
        logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);
        logger.setUseParentHandlers(false);
    }

    public Logger getLogger() {
        return logger;
    }
}
