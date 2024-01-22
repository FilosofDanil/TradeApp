package pdf;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        int[] a = {2, 1, 3};
        int[] b = {8, 10, 12, -7, 26, 6};

        System.out.println(find(a));
        System.out.println(find(b));
    }

    static int find(int[] integers) {
        int pivot = integers.length / 2;
        for (int i = 0; i < pivot; i++) {
            int max = integers.length - 1 - i;
            if (Math.abs(integers[max] % 2) != Math.abs(integers[i] % 2)) {
                int num = 1;
                if (i == pivot - 1 && (pivot > 1)) {
                    num = -1;
                }
                if (Math.abs(integers[max] % 2) != Math.abs(integers[i + num] % 2)) {
                    return integers[max];
                } else {
                    return integers[i];
                }
            }
        }
        return integers[pivot];
    }
}
