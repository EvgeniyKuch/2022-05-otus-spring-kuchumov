package ru.otus.studenttesting.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class LoaderCSV implements Loader {

    private final MessageService messageService;

    public LoaderCSV(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public String loadData() {
        String loadedData = "";
        try (var inputStream = getClass().getClassLoader()
                .getResourceAsStream(messageService.getMessage("file.name"))) {
            if (inputStream != null) {
                try (var bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
                    List<String> lines = extractLines(bufferedReader);
                    if (lines.size() > 0) {
                        loadedData = String.join("|", lines);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loadedData;
    }

    private List<String> extractLines(BufferedReader br) throws IOException {
        List<String> lines = new ArrayList<>();
        boolean endOfFile = false;
        while (!endOfFile) {
            String line = br.readLine();
            if (line != null) {
                lines.add(line);
            } else {
                endOfFile = true;
            }
        }
        return lines;
    }
}
