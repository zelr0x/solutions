import java.util.Set;

public final class BooleanInterpreter {
        private static final Set<String> TRUE_VALUES = Set.of(
                "истина", "да", "1", "true", "t", "yes", "y", "on");
        private static final Set<String> FALSE_VALUES = Set.of(
                "ложь", "нет", "0", "false", "f", "no", "n", "off");

        /**
         * @return {@literal null} if {@code value} is not a valid boolean string,
         * the {@code value} converted to boolean otherwise.
         */
        public static Boolean getNullable(String value) {
            return getOr(value, null);
        }

        public static Boolean getOr(String value, Boolean fallback) {
            if (value == null) return null;
            String lower = value.toLowerCase();
            if (TRUE_VALUES.contains(lower)) return true;
            if (FALSE_VALUES.contains(lower)) return false;
            return null;
        }

        public static Boolean getNullable(Object value) {
            return getOr(value, null);
        }

        public static Boolean getOr(Object value, Boolean fallback) {
            if (value == null) return fallback;
            if (value instanceof Boolean) return (Boolean) value;
            if (value instanceof Number) return ((Number) value).intValue() != 0;
            if (value instanceof String) return getOr((String) value, fallback);
            return fallback;
        }

        private BooleanInterpreter() {
            throw new AssertionError();
        }
}
