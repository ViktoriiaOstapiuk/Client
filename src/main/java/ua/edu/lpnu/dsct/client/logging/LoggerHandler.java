package ua.edu.lpnu.dsct.client.logging;

import ua.edu.lpnu.dsct.client.ui.MainFrame;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class LoggerHandler extends Handler {
    private final MainFrame frame;

    public LoggerHandler(MainFrame frame) {
       this.frame = frame;
   }

    @Override
    public void publish(LogRecord record) {
        if(!isLoggable(record)) {
            return;
        }
        frame.logTextArea.append(getFormatter().format(record));
    }

    @Override
    public void flush() {

    }

    @Override
    public void close() throws SecurityException {

    }
}
