import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A container object which always contains a value or an error.
 */
public abstract class Result<T, E> {

	public static <T, E> Result<T, E> ok(final T value) {
		Objects.requireNonNull(value);
		return new Ok<>(value);
	}

	public static <T, E> Result<T, E> err(final E error) {
		Objects.requireNonNull(error);
		return new Err<>(error);
	}

	public abstract T get() throws NoSuchElementException;

	public abstract E getErr() throws NoSuchElementException;

	public abstract boolean isOk();

	public abstract boolean isErr();

	protected abstract T getValue();

	public<U> Result<U, E> map(final Function<T, U> mapper) {
		return Optional.ofNullable(getValue())
			.map(v -> Result.<U, E> ok(mapper.apply(v)))
			.orElseGet(() -> {
				@SuppressWarnings("unchecked")
				final Result<U, E> ret = (Result<U, E>) this;
				return ret;
			});
	}

	public<U> Result<T, U> mapErr(final Function<E, U> mapper) {
		return Optional.ofNullable(getErr())
			.map(e -> Result.<T, U> err(mapper.apply(e)))
			.orElseGet(() -> {
				@SuppressWarnings("unchecked")
				final Result<T, U> ret = (Result<T, U>) this;
				return ret;
			});
	}

	public Result<T, E> orElseGet(final Supplier<? extends Result<T, E>> other) {
		return isOk() ? ok(get()) : other.get();
	}

	public static final class Ok<T, E> extends Result<T, E> {
		private final T value;

		private Ok(final T value) {
			this.value = value;
		}

		@Override
		public T get() {
			return Optional.ofNullable(value)
				.orElseThrow(NoSuchElementException::new);
		}

		@Override
		public E getErr() {
			throw new NoSuchElementException(toString() + ".get()");
		}

		@Override
		public boolean isOk() {
			return true;
		}

		@Override
		public boolean isErr() {
			return false;
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

	public static final class Err<T, E> extends Result<T, E> {
		private final E error;

		private Err(final E error) {
			this.error = error;
		}

		@Override
		public T get() {
			throw new NoSuchElementException(toString() + ".get()");
		}

		@Override
		public E getErr() {
			return error;
		}

		@Override
		public boolean isOk() {
			return false;
		}

		@Override
		public boolean isErr() {
			return true;
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
