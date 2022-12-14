import java.util.ArrayList;
import java.util.List;

public class Utils {

    private Utils() {} // Non-instantiable

    public static List<String> parseArrayItems(final String arrayString) {
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
