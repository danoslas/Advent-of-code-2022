import java.util.ArrayList;
import java.util.List;

public class Arrays {

    private Arrays() {} // Non-instantiable

    public static Result compareArrays(final String left, final String right) {
        final List<String> leftItems = parseArrayItems(left);
        final List<String> rightItems = parseArrayItems(right);

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

    private static List<String> parseArrayItems(final String arrayString) {
        final List<String> items = new ArrayList<>();

        final char[] arrayChars = arrayString.toCharArray();

        int bracketsOpened = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < arrayChars.length - 1; i++) {
            final char ch = arrayChars[i];

            if (ch == '[') {
                bracketsOpened++;
            } else if (ch == ']') {
                bracketsOpened--;
            } else if (ch == ',' && bracketsOpened == 0) {
                items.add(sb.toString());
                sb = new StringBuilder();
                continue;
            }

            sb.append(ch);
        }

        if (!sb.isEmpty()) {
            items.add(sb.toString());
        }

        return items;
    }
}
