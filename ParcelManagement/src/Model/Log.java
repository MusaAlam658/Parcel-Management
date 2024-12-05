package Model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log {
    // The static instance (singleton)
    private static Log instance;

    private static final String LOG_FILE_PATH = "src/View/log.txt";

    // Add datastamp along with the action
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Get the single instance of Log
    public static Log getInstance() {
        if (instance == null) {
            instance = new Log();
        }
        return instance;
    }

    // Log a message into the log file
    public void addLog(String message) {
        String timestamp = LocalDateTime.now().format(DATE_TIME_FORMATTER);
        String logMessage = String.format("[%s] %s", timestamp, message);

        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE_PATH, true))) {
            writer.println(logMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to clear the log file when the Manager GUI is accessed
    public void clearLogFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE_PATH, false))) {
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
