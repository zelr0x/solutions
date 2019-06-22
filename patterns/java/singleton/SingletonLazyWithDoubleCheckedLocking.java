/**
 * Lazily initialized.
 * Thread-safety is provided by double-checked locking.
 *
 * @since 1.5 - Before java 1.5, the volatile keyword could work incorrectly
 * with double-checked locking.
 */
public final class SingletonLazyWithDoubleCheckedLocking {
    public static volatile SingletonLazyWithDoubleCheckedLocking instance;

    // Fields and methods

    private SingletonLazyWithDoubleCheckedLocking() {
    }

    public static SingletonLazyWithDoubleCheckedLocking getInstance() {
        if (instance == null) {
            synchronized (SingletonLazyWithDoubleCheckedLocking.class) {
                if (instance == null) {
                    instance = new SingletonLazyWithDoubleCheckedLocking();
                }
            }
        }
        return instance;
    }
}
