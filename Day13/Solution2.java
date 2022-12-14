import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Solution2 {

    public static void main(final String[] args) throws IOException {
        final List<String> sortedLines;

        try (final Stream<String> stream = Files.lines(Path.of("input.txt"))) {
            sortedLines = Stream.concat(Stream.of("[[2]]", "[[6]]"), stream)
                    .filter(Predicate.not(String::isEmpty))
                    .sorted((fst, snd) -> {
                        final Result result = compareArrays(fst, snd);
                        if (result == Result.DUNNO) {
                            return 0;
                        }
                        return result == Result.RIGHT_ORDER ? -1 : 1;
                    })
                    .toList();
        }

        System.out.println(
                IntStream.range(0, sortedLines.size())
                        .filter(i -> "[[2]]".equals(sortedLines.get(i)) || "[[6]]".equals(sortedLines.get(i)))
                        .limit(2)
                        .map(i -> i + 1)
                        .reduce(1, (fst, snd) -> fst * snd)
        );
    }

    private static Result compareArrays(final String left, final String right) {
        final List<String> leftItems = Utils.parseArrayItems(left);
        final List<String> rightItems = Utils.parseArrayItems(right);

        Result isInOrder = Result.DUNNO;
        for (int i = 0; i < Math.min(leftItems.size(), rightItems.size()); i++) {
            isInOrder = isInOrder.merge(compareItems(leftItems.get(i), rightItems.get(i)));
        }

        if (isInOrder == Result.DUNNO) {
            if (leftItems.size() < rightItems.size()) {
                return Result.RIGHT_ORDER;
            } else if (leftItems.size() > rightItems.size()) {
                return Result.NOT_RIGHT_ORDER;
            }
        }

        return isInOrder;
    }

    private static Result compareItems(final String left, final String right) {
        final boolean isLeftItemArray = left.charAt(0) == '[';
        final boolean isRightItemArray = right.charAt(0) == '[';

        if (isLeftItemArray && isRightItemArray) {
            return compareArrays(left, right);
        }
        if (isLeftItemArray) {
            return compareArrays(left, '[' + right + ']');
        }
        if (isRightItemArray) {
            return compareArrays('[' + left + ']', right);
        }
        return compareIntegers(Integer.parseInt(left), Integer.parseInt(right));
    }

    private static Result compareIntegers(final int left, final int right) {
        if (left == right) {
            return Result.DUNNO;
        }
        return left < right ? Result.RIGHT_ORDER : Result.NOT_RIGHT_ORDER;
    }
}
