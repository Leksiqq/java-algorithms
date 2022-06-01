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

import java.util.Arrays;

public class PartitionProblem {
    public static boolean apply(final int[] s) {
        return apply(s, Arrays.stream(s).sum());
    }

    public static boolean apply(final int[] s, final int sum) {
        int sum1 = sum / 2;
        boolean[][] mat = new boolean[sum1 + 1][s.length + 1];
        for (int i = 0; i < mat[0].length; i++) {
            mat[0][i] = true;
        }
        for (int i = 1; i < mat.length; i++) {
            mat[i][0] = false;
        }
        for (int i = 1; i <= sum1; i++) {
            for (int j = 1; j <= s.length; j++) {
                if (i - s[j - 1] >= 0) {
                    mat[i][j] = mat[i][j - 1] || mat[i - s[j - 1]][j - 1];
                } else {
                    mat[i][j] = mat[i][j - 1];
                }
            }
        }
        return mat[sum1][s.length];
    }


}
