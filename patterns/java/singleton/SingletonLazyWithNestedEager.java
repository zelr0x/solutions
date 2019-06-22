/**
 * An instance is initialized eagerly in a Holder class,
 * but since it is nested, it is actually initialized lazily.
 * Thread-safety is provided by eager initialization.
 *
 * @since 1.2 - Before java 1.2, garbage collector could collect an instance
 * if the only reference to it was inside the Singleton class itself.
 */
public final class SingletonLazyWithNestedEager {
    // Fields and methods

    private SingletonLazyWithNestedEager() {
    }

    public SingletonLazyWithNestedEager getInstance() {
        return Holder.instance;
    }

    private static final class Holder {
        private Holder() {
            throw new AssertionError();
        }

        private static final SingletonLazyWithNestedEager instance
                = new SingletonLazyWithNestedEager();
    }
}
