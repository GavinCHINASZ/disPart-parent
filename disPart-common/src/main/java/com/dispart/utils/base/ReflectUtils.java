package com.dispart.utils.base;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @ Author     : zj
 * @ Date       : 2019/1/8 13:24
 * @ Description:
 */
public class ReflectUtils {

    public static Map<String ,String> reflect(Object o) {
        try {
            Map<String, String> map = new HashMap<>();
            Class cls = o.getClass();
            Field[] fields = addAll(o.getClass().getDeclaredFields(),o.getClass().getSuperclass().getDeclaredFields()) ;
            for(int i=0; i<fields.length; i++){
                Field f = fields[i];
                f.setAccessible(true);
                if (f.get(o) != null){
                    map.put(f.getName(),String.valueOf(f.get(o)));
                }
            }
            return map;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T[] addAll(final T[] array1, final T... array2) {
        if (array1 == null) {
            return (array2);
        } else if (array2 == null) {
            return (array1);
        }
        final Class<?> type1 = array1.getClass().getComponentType();
        @SuppressWarnings("unchecked") // OK, because array is of type T
        final
        // a处
                T[] joinedArray = (T[]) Array.newInstance(type1, array1.length + array2.length);
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        try {
            // b处
            System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        } catch (final ArrayStoreException ase) {
            final Class<?> type2 = array2.getClass().getComponentType();
            if (!type1.isAssignableFrom(type2)) {
                throw new IllegalArgumentException("Cannot store " + type2.getName() + " in an array of "
                        + type1.getName(), ase);
            }
            throw ase;
        }
        return joinedArray;
    }
}
