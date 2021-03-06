/*
 * MIT License
 *
 * Copyright (c) 2022 Alexey Zakharov <leksi@leksi.net>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */
package net.leksi.algo;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MEX {
    private static ArrayList<Integer> used = new ArrayList<>();
    private static int serial = 0;
    
    public static Integer apply(final int[] arr) {
        serial++;
        for(int i = 0; i < arr.length; i++) {
            if(used.size() <= arr[i]) {
                used.addAll(IntStream.range(used.size(), arr[i] + 1).mapToObj(v -> 0).collect(Collectors.toList()));
            }
            used.set(arr[i], serial);
        }
        int res = 0;
        for(; res < used.size() && used.get(res) == serial; res++) {}
        return res;
    }
    
    public static Integer apply(final IntStream stream) {
        serial++;
        stream.forEach(v -> {
            if(used.size() <= v) {
                used.addAll(IntStream.range(used.size(), v + 1).mapToObj(v1 -> 0).collect(Collectors.toList()));
            }
            used.set(v, serial);
        });
        int res = 0;
        for(; res < used.size() && used.get(res) == serial; res++) {}
        return res;
    }
    
    public static Integer apply(final Stream<Integer> stream) {
        serial++;
        stream.forEach(v -> {
            if(used.size() <= v) {
                used.addAll(IntStream.range(used.size(), v + 1).mapToObj(v1 -> 0).collect(Collectors.toList()));
            }
            used.set(v, serial);
        });
        int res = 0;
        for(; res < used.size() && used.get(res) == serial; res++) {}
        return res;
    }
}
