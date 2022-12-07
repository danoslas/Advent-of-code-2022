import java.io.IOException;

public class Solution1 {

    public static void main(final String[] args) throws IOException {

        final Directory root = Directories.buildDirectoryTree("input.txt");

        System.out.println(getDirectorySizeSum(root));
    }

    private static int getDirectorySizeSum(final Directory root) {
        int rootSize = root.getSize() > 100_000 ? 0 : root.getSize();

        return rootSize + root.getSubdirectories()
                .stream()
                .mapToInt(Solution1::getDirectorySizeSum)
                .sum();
    }
}
