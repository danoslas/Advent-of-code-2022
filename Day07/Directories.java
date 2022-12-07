import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class Directories {

    private static final Pattern CD_PATTERN = Pattern.compile("^\\$ cd (.+)$");
    private static final Pattern DIR_PATTERN = Pattern.compile("^dir (.+)$");
    private static final Pattern FILE_PATTERN = Pattern.compile("^(\\d+)");

    static Directory buildDirectoryTree(final String inputFile) throws IOException {
        final Directory root = new Directory("/");

        try (final BufferedReader br = Files.newBufferedReader(Path.of(inputFile))) {
            Directory pwd = root;
            String line;
            while ((line = br.readLine()) != null) {
                Matcher lineMatcher;
                if ((lineMatcher = CD_PATTERN.matcher(line)).find()) {
                    pwd = switch (lineMatcher.group(1)) {
                        case "/" -> root;
                        case ".." -> pwd.getParent().orElseThrow();
                        default -> pwd.getSubdirectoryByName(lineMatcher.group(1))
                                .orElseThrow();
                    };

                } else if ((lineMatcher = DIR_PATTERN.matcher(line)).find()) {
                    pwd.addSubdirectory(new Directory(lineMatcher.group(1)));

                } else if ((lineMatcher = FILE_PATTERN.matcher(line)).find()) {
                    pwd.addLocalFileSize(Integer.parseInt(lineMatcher.group(1)));
                }
            }
        }

        return root;
    }
}
