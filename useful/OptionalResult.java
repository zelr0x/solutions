import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A container object which may contain a value, an error or nothing.
 */
public abstract class OptionalResult<T, E> {

	private static final OptionalResult<?, ?> EMPTY = of(null);

	public static <T, E> OptionalResult<T, E> ofNullable(final T value) {
		return value == null ? empty() : of(value);
	}

	public static <T, E> OptionalResult<T, E> of(final T value) {
		return new Ok<>(value);
	}

	public static <T, E> OptionalResult<T, E> ofErr(final E error) {
		return new Err<>(error);
	}

	public static <T, E> OptionalResult<T, E> empty() {
		@SuppressWarnings("unchecked")
		final OptionalResult<T, E> res = (OptionalResult<T, E>) EMPTY;
		return res;
	}

	public abstract T get() throws NoSuchElementException;

	public abstract E getError();

	protected abstract T getValue();

	public boolean isNotEmpty() {
		return !isEmpty();
	}

	public boolean isEmpty() {
		return hasNoValue() && hasNoErr();
	}

	public boolean hasNoValue() {
		return !hasValue();
	}

	public boolean hasValue() {
		return getValue() != null;
	}

	public boolean hasNoErr() {
		return !hasErr();
	}

	public boolean hasErr() {
		return getError() != null;
	}

	public<U> OptionalResult<U, E> map(final Function<T, U> mapper) {
		return Optional.ofNullable(getValue())
			.map(v -> OptionalResult.<U, E>of(mapper.apply(v)))
			.orElseGet(() -> {
				@SuppressWarnings("unchecked")
				final OptionalResult<U, E> ret = (OptionalResult<U, E>) this;
				return ret;
			});
	}

	public<U> OptionalResult<T, U> mapErr(final Function<E, U> mapper) {
		return Optional.ofNullable(getError())
			.map(e -> OptionalResult.<T, U> ofErr(mapper.apply(e)))
			.orElseGet(() -> {
				@SuppressWarnings("unchecked")
				final OptionalResult<T, U> ret = (OptionalResult<T, U>) this;
				return ret;
			});
	}

	public OptionalResult<T, E> orElseGet(final Supplier<? extends OptionalResult<T, E>> other) {
		return hasValue() ? of(get()) : other.get();
	}

	public static final class Ok<T, E> extends OptionalResult<T, E> {
		private final T value;

		private Ok(final T value) {
			this.value = value;
		}

		@Override
		public E getError() {
			return null;
		}

		@Override
		public T get() {
			return Optional.ofNullable(value)
				.orElseThrow(NoSuchElementException::new);
		}

		@Override
		public String toString() {
			return "Ok(" + value + ")";
		}

		@Override
		protected T getValue() {
			return value;
		}
	}

	public static final class Err<T, E> extends OptionalResult<T, E> {
		private final E error;

		private Err(final E error) {
			this.error = error;
		}

		@Override
		public E getError() {
			return error;
		}

		@Override
		public T get() {
			throw new NoSuchElementException(toString() + ".get()");
		}

		@Override
		public String toString() {
			return "Err(" + error + ")";
		}

		@Override
		protected T getValue() {
			return null;
		}
	}
}
