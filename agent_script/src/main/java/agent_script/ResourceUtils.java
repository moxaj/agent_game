package agent_script;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.function.Function;

public final class ResourceUtils {
    public static <T> T withResourcePath(Class<?> clazz, String resourcePathStr, Function<Path, T> pathFunction) throws URISyntaxException {
        URI uri = clazz.getResource(resourcePathStr).toURI();
        FileSystem fileSystem;
        boolean newFileSystem;
        Path resourcePath;
        if ("file".equals(uri.getScheme())) {
            fileSystem = null;
            newFileSystem = false;
            resourcePath = Paths.get(uri);
        } else {
            try {
                fileSystem = FileSystems.newFileSystem(uri, new HashMap<>());
                newFileSystem = true;
            } catch (FileSystemAlreadyExistsException e) {
                fileSystem = FileSystems.getFileSystem(uri);
                newFileSystem = false;
            } catch (IOException e) {
                // Should not happen
                throw new RuntimeException(e);
            }

            resourcePath = fileSystem.getPath(resourcePathStr);
        }

        T value = pathFunction.apply(resourcePath);
        if (newFileSystem) {
            try {
                fileSystem.close();
            } catch (IOException e) {
                // Should not happen
                throw new RuntimeException(e);
            }
        }

        return value;
    }
}
