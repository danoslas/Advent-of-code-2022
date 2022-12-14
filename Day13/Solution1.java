import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Solution1 {

    public static void main(final String[] args) throws IOException {
        int indicesSum = 0;

        try (final BufferedReader br = Files.newBufferedReader(Path.of("input.txt"))) {
            int pair = 1;
            do {
                if (compareArrays(br.readLine(), br.readLine()) == Result.RIGHT_ORDER) {
                    indicesSum += pair;
                }
                pair++;
            } while (br.readLine() != null);
        }

        System.out.println(indicesSum);
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
