package com.crdb;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
public class UTL {
    static Date date = Calendar.getInstance().getTime();
    static DateFormat formated = new SimpleDateFormat("ddMMYY");
    static String time = formated.format(date);
    public static void moveFilesAfterRead(String source) throws IOException {
        InputStream input = Main.class.getClassLoader().getResourceAsStream("config.properties");
        Properties prop = new Properties();
        prop.load(input);
        File directoryName = new File(source);
        Path sourceFile = Paths.get(prop.getProperty("mainpath") + "/" + directoryName.getName());
        Path targetFile = Paths.get(prop.getProperty("tmppath") + "/" + directoryName.getName());
        try {
            Files.move(sourceFile, targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String truncate(String value, int length) {
        if (value.length() > length && value != null) {
            return value.substring(0, length);
        } else {
            return value;
        }
    }

    public static String spaceOut(String value, int space, String delimiter) {
        StringBuilder stringBuilder = new StringBuilder();
        int n = 0;
        while (n < value.length()) {
            stringBuilder.append(value, n, n + space);
            if ((n + space) < value.length())
                stringBuilder.append(delimiter);
            n = n + space;
        }
        return stringBuilder.toString().trim();
    }

    public static String spaceOut(String value, int space) {
        return spaceOut(value, space, " ");
    }

    public static void backupFile(String source) throws IOException {
        InputStream input = Main.class.getClassLoader().getResourceAsStream("config.properties");
        Properties prop = new Properties();
        prop.load(input);
        File directoryName = new File(source);
        File mtFile = new File(prop.getProperty("backuppath") + "\\" + prop.getProperty("tissbackup") + UTL.time);
        mtFile.mkdirs();
        Path sourceFile = Paths.get(prop.getProperty("tmppath") + "/" + directoryName.getName());
        Path targetFile = Paths.get(mtFile + "/"+directoryName.getName());
        try {
            if (Files.notExists(targetFile)){
                Files.move(sourceFile, targetFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


