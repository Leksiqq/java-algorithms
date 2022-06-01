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

import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

import static net.leksi.algo.datas.Datas.mapi;

public class HighsWithLowsSeg {
    public static interface ItemHandler {
        boolean handle(final int value, final int startInclusive, final int position, final int endExclusive);
    }

    public static void apply(final int[] a, final ItemHandler handler, final boolean strict) {
        TreeMap<Integer, List<Integer>> mapi = mapi(a);
        TreeSet<Integer> set = new TreeSet<>();

        int[] centers = mapi.keySet().stream().mapToInt(v -> v.intValue()).toArray();
        l: for (int i = centers.length - 1; i >= 0 ; --i) {
            if(strict) {
                set.addAll(mapi.get(centers[i]));
            }
            for(int j = 0; j < mapi.get(centers[i]).size(); ++j) {
                int centerPos = mapi.get(centers[i]).get(j);
                Integer left = set.lower(centerPos);
                if(left == null) {
                    left = -1;
                }
                Integer right = set.higher(centerPos);
                if(right == null) {
                    right = a.length;
                }
                if(!handler.handle(centers[i], left, centerPos, right)) {
                    break l;
                }
            }
            if(!strict) {
                set.addAll(mapi.get(centers[i]));
            }
        }
    }
}
