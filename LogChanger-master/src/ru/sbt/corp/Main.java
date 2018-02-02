package ru.sbt.corp;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
    private static final String FILE_NAME_RESULT = "result.txt";
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private static final SimpleDateFormat formatResult = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");

    public static void main(String[] args) {
        File file = new File(".");
        File[] files = file.listFiles((dir, name) -> name.startsWith("dul") && name.endsWith(".log"));
        if (files != null) {
            try {
                List<String> logResult = new ArrayList<>();
                for (int i = 0; i < files.length; i++) {
                    List<String> stringsFile = Files.readAllLines(files[i].toPath(), StandardCharsets.UTF_8);
                    List<String> stringsLogs = getLogs(stringsFile);
                    logResult.addAll(stringsLogs);
                    logResult.sort((o1, o2) -> {
                        Date parseDateOne = format.parse(o1, new ParsePosition(0));
                        Date parseDateTwo = format.parse(o2, new ParsePosition(0));
                        return parseDateOne.compareTo(parseDateTwo);
                    });
                }
                Date date = new Date();
                String formatDate = Main.formatResult.format(date);
                String fileNameResult = formatDate + "_" + FILE_NAME_RESULT;
                Files.write(Paths.get(fileNameResult), logResult, StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static List<String> getLogs(List<String> list) {
        int j = 0;
        List<String> logs = new ArrayList<>();
        for (String aList : list) {
            if (!aList.equals("") && aList.startsWith("2018")) {
                logs.add(j, aList);
                j++;
            } else {
                String newString = logs.get(j - 1);
                newString = newString.concat("\n");
                newString = newString.concat(aList);
                logs.remove(j - 1);
                logs.add(j - 1, newString);
            }
        }
        return modifyLogs(logs);
    }

    private static List<String> modifyLogs(List<String> list) {
        int j = 0;
        List<String> modifyList = new ArrayList<>();
        modifyList.add(list.get(0));
        for (int i = 1; i < list.size(); i++) {
            StringBuilder oldStringBuilder = new StringBuilder(list.get(i));
            String oldDate = oldStringBuilder.substring(0, 19);
            StringBuilder newStringBuilder = new StringBuilder(modifyList.get(j));
            String newDate = newStringBuilder.substring(0, 19);
            if (newDate.equals(oldDate)) {
                String newString = modifyList.get(j);
                newString = newString.concat("\n");
                newString = newString.concat(list.get(i));
                modifyList.remove(j);
                modifyList.add(j, newString);
            } else {
                modifyList.add(list.get(i));
                j++;
            }
        }
        return modifyList;
    }
}