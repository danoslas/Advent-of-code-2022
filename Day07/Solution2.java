import java.io.IOException;
import java.util.Comparator;
import java.util.Objects;

public class Solution2 {

    private static final int SPACE_TOTAL = 70_000_000;
    private static final int SPACE_NEEDED = 30_000_000;

    public static void main(final String[] args) throws IOException {

        final Directory root = Directories.buildDirectoryTree("input.txt");
        final int spaceAvailable = SPACE_TOTAL - root.getSize();
        final int spaceToBeFreed = SPACE_NEEDED - spaceAvailable;

        System.out.println(getDeletedDirectory(root, spaceToBeFreed).getSize());
    }

    private static Directory getDeletedDirectory(final Directory root, final int spaceToBeFreed) {
        if (spaceToBeFreed > root.getSize()) {
            return null;
        }

        return root.getSubdirectories()
                .stream()
                .map(dir -> getDeletedDirectory(dir, spaceToBeFreed))
                .filter(Objects::nonNull)
                .min(Comparator.comparing(Directory::getSize))
                .orElse(root);
    }
}
