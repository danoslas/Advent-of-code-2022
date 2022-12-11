package monkeys;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.LongPredicate;
import java.util.function.LongUnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MonkeyFactory {

    private MonkeyFactory() {} // Non-instantiable

    public static Monkey createSolution1Monkey(final String itemsString,
                                               final String operationString,
                                               final String testString,
                                               final String ifTrueString,
                                               final String ifFalseString) {

        return createMonkey(itemsString, operationString, worryLevel -> worryLevel / 3,
                testString, ifTrueString, ifFalseString);
    }

    private static Monkey createMonkey(final String itemsString,
                                       final String operationString,
                                       final LongUnaryOperator operationDivisor,
                                       final String testString,
                                       final String ifTrueString,
                                       final String ifFalseString) {

        final Deque<Long> itemsList = Stream.of(itemsString.substring(18).split(", "))
                .map(Long::valueOf)
                .collect(Collectors.toCollection(ArrayDeque::new));

        final String operationValueString = operationString.substring(25);
        final LongUnaryOperator operationValue;
        if ("old".equals(operationValueString)) {
            operationValue = LongUnaryOperator.identity();
        } else {
            final long value = Long.parseLong(operationValueString);
            operationValue = ignored -> value;
        }
        final char operationOperator = operationString.charAt(23);
        final LongUnaryOperator operation = old -> switch (operationOperator) {
            case '*' -> operationDivisor.applyAsLong(old * operationValue.applyAsLong(old));
            case '+' -> operationDivisor.applyAsLong(old + operationValue.applyAsLong(old));
            default -> throw new IllegalStateException("Unexpected operation '" + operationOperator + "'.");
        };

        final int divisionValue = Integer.parseInt(testString.substring(21));
        final LongPredicate test = worryLevel -> worryLevel % divisionValue == 0;

        final int testTrueId = Integer.parseInt(ifTrueString.substring(29));
        final int testFalseId = Integer.parseInt(ifFalseString.substring(30));

        return new Monkey(itemsList, operation, test, testTrueId, testFalseId);
    }
}
