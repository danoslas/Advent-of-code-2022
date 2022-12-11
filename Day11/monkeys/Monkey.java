package monkeys;

import java.util.Deque;
import java.util.List;
import java.util.function.LongPredicate;
import java.util.function.LongUnaryOperator;

public class Monkey {

    private final Deque<Long> items;
    private final LongUnaryOperator worryLevelModify;
    private final LongPredicate worryLevelTest;
    private final int testTrueMonkeyId;
    private final int testFalseMonkeyId;

    private int inspectionsCount = 0;

    Monkey(final Deque<Long> items,
                  final LongUnaryOperator worryLevelModify,
                  final LongPredicate worryLevelTest,
                  final int testTrueMonkeyId,
                  final int testFalseMonkeyId) {

        this.items = items;
        this.worryLevelModify = worryLevelModify;
        this.worryLevelTest = worryLevelTest;
        this.testTrueMonkeyId = testTrueMonkeyId;
        this.testFalseMonkeyId = testFalseMonkeyId;
    }

    public void playRound(final List<Monkey> monkeys) {
        long item;
        while (!items.isEmpty()) {
            item = items.pop();
            inspectionsCount++;

            item = worryLevelModify.applyAsLong(item);
            item /= 3;

            final Monkey monkeyToThrowItemTo =
                    monkeys.get(worryLevelTest.test(item) ? testTrueMonkeyId : testFalseMonkeyId);
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
