package ru.cynteka;

import java.util.List;
import java.util.ListIterator;

public class Utils {
    public static boolean isPositiveInt(String s) {
        int i;
        try {
            i = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return i > 0;
    }

    // Remove strings at the beginning of the list, unless it is a positive integer
    public static List<String> removeLeadNonPosInt(List<String> list) {
        ListIterator<String> iter = list.listIterator();
        while (iter.hasNext()) {
            if (!isPositiveInt(iter.next())) {
                iter.remove();
            } else {
                break;
            }
        }
        return list;
    }
}