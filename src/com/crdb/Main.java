package com.crdb;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        InputStream input = Main.class.getClassLoader().getResourceAsStream("config.properties");
        Properties prop = new Properties();
        prop.load(input);
        Path path = Paths.get(prop.getProperty("mainpath"));
        System.out.println("[+] Waiting For Files [+]");
        DirectoryScanner.watchForFile(path);
//       String file = "C:\\SWIFT_TISS\\TISS_IN\\00233957.out";
//        ParseFile.readFile(new File(file));

    }
}
