package com.crdb;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
public class DirectoryScanner {
    public static void watchForFile(Path path) throws InterruptedException, IOException {
        WatchService watchService = null;
        try {
            watchService = FileSystems.getDefault().newWatchService();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            path.register(
                    watchService,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        WatchKey key;
        File directoryName = new File(path.toString());
        while ((key = watchService.take()) != null) {
            File[] fileList = directoryName.listFiles();
            if (fileList != null)
                for (File file : fileList) {
                    if (file.isFile()) {
                        ParseFile.readFile(file, file1 -> {
                            try {
                                UTL.moveFilesAfterRead(file1.toString());
                                UTL.backupFile(file1.toString());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                        Thread.sleep(500);
                    }
                }
            key.reset();
        }
    }

    public interface OnReadComplete {

        void onReadComplete(File file);
    }
}
