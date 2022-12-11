package monkeys;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.function.LongUnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MonkeyFactory {

    private MonkeyFactory() {} // Non-instantiable

    public static List<Monkey> createSolution1Monkeys(final String fileName) throws IOException {
        return createMonkeys(fileName, worryLevel -> worryLevel / 3);
    }

    public static List<Monkey> createSolution2Monkeys(final String fileName) throws IOException {
        final List<Monkey> monkeys = createMonkeys(fileName, LongUnaryOperator.identity());
        setMonkeysLcmDivisors(monkeys);

        return monkeys;
    }

    private static void setMonkeysLcmDivisors(final List<Monkey> monkeys) {
        final long lcm = Utils.leastCommonMultiple(
                monkeys.get(0).getTestDivisor(),
                IntStream.range(1, monkeys.size())
                        .mapToObj(monkeys::get)
                        .mapToLong(Monkey::getTestDivisor)
                        .toArray()
        );

        for (final Monkey monkey : monkeys) {
            monkey.setLcmDivisor(lcm);
        }
    }

    private static List<Monkey> createMonkeys(final String fileName,
                                              final LongUnaryOperator reliefEvaluator) throws IOException {

        final List<Monkey> monkeys = new ArrayList<>();

        try (final BufferedReader br = Files.newBufferedReader(Path.of(fileName))) {
            do {
                br.readLine();
                monkeys.add(createMonkey(br.readLine(), br.readLine(),
                        br.readLine(), br.readLine(), br.readLine(), reliefEvaluator));
            } while (br.readLine() != null);
        }

        return monkeys;
    }

    private static Monkey createMonkey(final String itemsString,
                                       final String operationString,
                                       final String testString,
                                       final String ifTrueString,
                                       final String ifFalseString,
                                       final LongUnaryOperator reliefEvaluator) {

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
            case '*' -> reliefEvaluator.applyAsLong(old * operationValue.applyAsLong(old));
            case '+' -> reliefEvaluator.applyAsLong(old + operationValue.applyAsLong(old));
            default -> throw new IllegalStateException("Unexpected operation '" + operationOperator + "'.");
        };

        final int testDivisor = Integer.parseInt(testString.substring(21));

        final int testTrueId = Integer.parseInt(ifTrueString.substring(29));
        final int testFalseId = Integer.parseInt(ifFalseString.substring(30));

        return new Monkey(itemsList, operation, testDivisor, testTrueId, testFalseId);
    }
}
