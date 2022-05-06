package ru.cynteka;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Reader {
    private final File inputFile;

    public Reader(File inputFile) {
        this.inputFile = inputFile;
    }

    List<List<String>> readStringsFromFile() throws IOException {
        List<String> listN = new ArrayList<>();
        List<String> listM = new ArrayList<>();
        List<List<String>> result = new ArrayList<>();
        result.add(listN);
        result.add(listM);

        // Read all lines from a file
        List<String> lines = FileUtils.readLines(inputFile, Charset.defaultCharset());

        // Leave only non-empty lines
        List<String> noEmptyLines = lines.stream()
                .map(String::trim).filter(s -> s.length() > 0).collect(Collectors.toList());

        Utils.removeLeadNonPosInt(noEmptyLines);
        int n = 0;
        if (!noEmptyLines.isEmpty()) {
            n = Integer.parseInt(noEmptyLines.get(0));
            noEmptyLines.remove(0);
            for (int i = 0; i < n; i++) {
                if (i >= noEmptyLines.size()) {
                    break;
                }
                listN.add(noEmptyLines.get(i));
            }
        }

        if (listN.size() < noEmptyLines.size()) {
            noEmptyLines = noEmptyLines.subList(n, noEmptyLines.size());
        } else {
            noEmptyLines.clear();
        }

        Utils.removeLeadNonPosInt(noEmptyLines);
        if (!noEmptyLines.isEmpty()) {
            int m = Integer.parseInt(noEmptyLines.get(0));
            noEmptyLines.remove(0);
            for (int i = 0; i < m; i++) {
                if (i >= noEmptyLines.size()) {
                    break;
                }
                listM.add(noEmptyLines.get(i));
            }
        }
        return result;
    }
}