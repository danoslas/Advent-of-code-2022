package monkeys;

import java.util.Deque;
import java.util.List;
import java.util.function.LongUnaryOperator;

public class Monkey {

    private final Deque<Long> items;
    private final LongUnaryOperator worryLevelModify;
    private final int testDivisor;
    private final int testTrueMonkeyId;
    private final int testFalseMonkeyId;

    private int inspectionsCount = 0;

    Monkey(final Deque<Long> items,
                  final LongUnaryOperator worryLevelModify,
                  final int testDivisor,
                  final int testTrueMonkeyId,
                  final int testFalseMonkeyId) {

        this.items = items;
        this.worryLevelModify = worryLevelModify;
        this.testDivisor = testDivisor;
        this.testTrueMonkeyId = testTrueMonkeyId;
        this.testFalseMonkeyId = testFalseMonkeyId;
    }

    public void playRound(final List<Monkey> monkeys) {
        long item;
        while (!items.isEmpty()) {
            item = items.pop();
            inspectionsCount++;

            item = worryLevelModify.applyAsLong(item);

            final Monkey monkeyToThrowItemTo =
                    monkeys.get(item % testDivisor == 0 ? testTrueMonkeyId : testFalseMonkeyId);
            throwItemToMonkey(item, monkeyToThrowItemTo);
        }
    }

    private void throwItemToMonkey(final long item, final Monkey monkey) {
        monkey.items.push(item);
    }

    public int getInspectionsCount() {
        return inspectionsCount;
    }
}
