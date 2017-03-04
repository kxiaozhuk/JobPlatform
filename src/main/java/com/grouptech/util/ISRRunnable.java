package com.grouptech.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Print the log of process
 */
public class ISRRunnable implements Runnable {
    private final BufferedReader reader;

    private ISRRunnable(BufferedReader reader) {
        this.reader = reader;
    }

    public ISRRunnable(InputStream inputStream) {
        this(new BufferedReader(new InputStreamReader(inputStream)));
    }

    public void run() {
        String line = null;
        try {
            line = reader.readLine();
            while (line != null) {
                Constant.logger.info(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            Constant.logger.error("There is a exception when getting log: ",e);
            throw new RuntimeException("There is a exception when getting log.");
        }
    }
}
