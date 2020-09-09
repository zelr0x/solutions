public final class ObjectUtil {
    public static boolean toBoolOrFalse(Object boolObj) {
        if (boolObj == null) return false;
        if (boolObj instanceof Boolean) return (boolean) boolObj;
        if (boolObj instanceof Number) return ((Number) boolObj).byteValue() != 0;
        return false;
    }
    
    private ObjectUtil() {
        throw new AssertionError();
    }
}
