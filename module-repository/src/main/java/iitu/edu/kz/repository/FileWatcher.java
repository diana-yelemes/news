// module-repository/src/main/java/iitu/edu/kz/repository/FileWatcher.java
package iitu.edu.kz.repository;

import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FileWatcher {

    private final Path pathToWatch;
    private final Runnable onFileChangeAction;

    public FileWatcher(String folderPath, Runnable onFileChangeAction) {
        this.pathToWatch = Paths.get(folderPath);
        this.onFileChangeAction = onFileChangeAction;
    }

    // Initialize and start the file watching service
    public void startWatching() {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleWithFixedDelay(() -> {
            try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
                pathToWatch.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

                WatchKey key;
                while ((key = watchService.take()) != null) {
                    for (WatchEvent<?> event : key.pollEvents()) {
                        if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
                            System.out.println("File changed: " + event.context());
                            onFileChangeAction.run();  // Reload data when a file changes
                        }
                    }
                    key.reset();
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }, 0, 5, TimeUnit.SECONDS);  // Check for changes every 5 seconds
    }
}
