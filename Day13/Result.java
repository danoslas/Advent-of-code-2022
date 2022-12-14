public enum Result {

    RIGHT_ORDER, NOT_RIGHT_ORDER, DUNNO;

    public Result merge(final Result result) {
        if (this == DUNNO) {
            return result;
        }
        return this;
    }
}
