import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class Solution1 {

    public static void main(final String[] args) throws IOException {
        final Tree[][] forest;

        try (final Stream<String> stream = Files.lines(Path.of("input.txt"))) {
            forest = stream.map(
                    line -> line.chars()
                            .mapToObj(Tree::new)
                            .toArray(Tree[]::new)
            ).toArray(Tree[][]::new);
        }

        final int forestHeight = forest.length;
        final int forestWidth = forest[0].length;

        int leftToRightVisible = 0;
        int rightToLeftVisible = 0;
        for (int i = 1; i < forestHeight - 1; i++) {
            final Tree[] row = forest[i];

            int leftToRightMaxHeight = row[0].getHeight();
            int rightToLeftMaxHeight = row[forestWidth - 1].getHeight();
            for (int j = 1; j < forestWidth - 1; j++) {
                final Tree leftTree = row[j];
                if (leftTree.getHeight() > leftToRightMaxHeight) {
                    if (!leftTree.isSpotted()) {
                        leftToRightVisible++;
                        leftTree.spot();
                    }
                    leftToRightMaxHeight = leftTree.getHeight();
                }

                final Tree rightTree = row[forestHeight - j - 1];
                if (rightTree.getHeight() > rightToLeftMaxHeight) {
                    if (!rightTree.isSpotted()) {
                        rightToLeftVisible++;
                        rightTree.spot();
                    }
                    rightToLeftMaxHeight = rightTree.getHeight();
                }
            }
        }

        int topToBottomVisible = 0;
        int bottomToTopVisible = 0;
        for (int i = 1; i < forestWidth - 1; i++) {
            int topToBottomMaxHeight = forest[0][i].getHeight();
            int bottomToTopMaxHeight = forest[forestWidth - 1][i].getHeight();
            for (int j = 1; j < forestHeight - 1; j++) {
                final Tree topTree = forest[j][i];
                if (topTree.getHeight() > topToBottomMaxHeight) {
                    if (!topTree.isSpotted()) {
                        topToBottomVisible++;
                        topTree.spot();
                    }
                    topToBottomMaxHeight = topTree.getHeight();
                }

                final Tree bottomTree = forest[forestHeight - j - 1][i];
                if (bottomTree.getHeight() > bottomToTopMaxHeight) {
                    if (!bottomTree.isSpotted()) {
                        bottomToTopVisible++;
                        bottomTree.spot();
                    }
                    bottomToTopMaxHeight = bottomTree.getHeight();
                }
            }
        }

        System.out.println(
                forestHeight * 2 + forestWidth * 2 - 4
                + leftToRightVisible
                + rightToLeftVisible
                + topToBottomVisible
                + bottomToTopVisible
        );
    }
}
