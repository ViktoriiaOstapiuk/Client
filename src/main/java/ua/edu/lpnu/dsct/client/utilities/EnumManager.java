package ua.edu.lpnu.dsct.client.utilities;

public class EnumManager {
    public static <E extends Enum<E>>  boolean contains(Class<E> enumClass, String test) {
        for(E enumValue: enumClass.getEnumConstants()) {
            if(enumValue.name().equals(test)) {
                return true;
            }
        }
        return false;
    }

    public static <E extends Enum<E>> Enum<E> parse(Class<E> enumClass, String test) throws IllegalArgumentException {
        String upper = test.toUpperCase();

        if(!contains(enumClass, upper)) {
           throw new IllegalArgumentException("Unknown " + enumClass.getSimpleName().toLowerCase() + ": " + upper);
        }
        return E.valueOf(enumClass, upper);
    }
}
