/**
 *
 * @author leksi
 */
package net.leksi.algo;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Utility {

    public static void countSort(final int[] src, final int[] dst, final int min, final int max, final int[] map) {
        int n = max - min;
        int[] cnt = new int[n];
        Arrays.fill(cnt, 0);
        int[] pos = new int[n];
        for(int i = 0; i < src.length; i++) {
            cnt[map[i] - min]++;
        }
        pos[0] = 0;
        for(int i = 1; i < n; i++) {
            pos[i] = pos[i - 1] + cnt[i - 1];
        }
        for(int i = 0; i < src.length; i++) {
            dst[pos[map[i] - min]++] = src[i];
        }
    }
    
    public static void countSort(final int[] src, final int[] dst, final int min, final int max) {
        countSort(src, dst, min, max, null);
    }
    
    public static <T> void countSort(final T[] src, final T[] dst, final int min, final int max, final int[] map) {
        int[] idst = new int[src.length];
        int[] isrc = IntStream.range(0, src.length).toArray();
        countSort(isrc, idst, min, max, map);
        for(int i = 0; i < src.length; i++) {
            dst[i] = src[idst[i]];
        }
    }

    public static void suffixes(final int[] src, final int[] dst) {
        suffixes(src, dst, null);
    }
    
    public static void suffixes(final int[] src, final int[] dst, final int[] lcp) {
        int n = src.length + 1;
        int[] prev = new int[2];
        int[] c = new int[n];
        int[] c1 = new int[n];
        int[][][] c0 = new int[2][n][2];
        int[] sel = new int[]{0};
        int min = Integer.MAX_VALUE;
        int max = 0;
        
        for(int i = 0; i < n; i++) {
            if(i < n - 1) {
                if(src[i] - 1 < min) {
                    min = src[i] - 1;
                }
                if(src[i] + 1 > max) {
                    max = src[i] + 1;
                }
            }
            c0[sel[0]][i][0] = c0[sel[0]][i][1] = i;
            c[i] = i < n - 1 ? src[i] : min;
        }
        countSort(c0[sel[0]], c0[sel[0] ^ 1], min, max, c);
        sel[0] ^= 1;
        for(int off = 1; ; off <<= 1) {
            int cc = -1;
            boolean cc_rep = false;
            for(int i = 0; i < n; i++) {
                if(i == 0 || prev[0] != c[c0[sel[0]][i][0]] || prev[1] != c[c0[sel[0]][i][1]]) {
                    prev[0] = c[c0[sel[0]][i][0]];
                    prev[1] = c[c0[sel[0]][i][1]];
                    cc++;
                } else {
                    cc_rep = true;
                }
                c1[c0[sel[0]][i][0]] = cc;
                c0[sel[0]][i][1] = c0[sel[0]][i][0];
            }
            for(int i = 0; i < n; i++) {
                if(cc_rep) {
                    int j = (c0[sel[0]][i][1] - off + n) % n;
                    c0[sel[0]][i][0] = j;
                }
                c[i] = c1[i];
            }
            if(!cc_rep) {
                break;
            }
            countSort(c0[sel[0]], c0[sel[0] ^ 1], 0, n, Arrays.stream(c0[sel[0]]).mapToInt(v -> c[v[0]]).toArray());
            sel[0] ^= 1;
        }
        for(int i = 0; i < c1.length; i++) {
            dst[i] = c0[sel[0]][i][0];
        }
        if(lcp != null) {
            int k = 0;
            for(int i = 0; i < n - 1; i++) {
                int pi = c[i];
                int j = dst[pi - 1];
                while(i + k < src.length && j + k < src.length && src[i + k] == src[j + k]) {
                    k++;
                }
                lcp[pi - 1] = k;
                k = Math.max(k - 1, 0);
            }
        }
    }
    
    public static void z(final int[] src, final int[] dst) {
        int n = src.length;
        int l = 0;
        int r = 0;
        Arrays.fill(dst, 0);
        for(int i = 1; i < n; i++) {
            if(i <= r) {
                dst[i] = Math.min(dst[i - l], r - i + 1);
            }
            while(dst[i] + i < n && src[dst[i]] == src[dst[i] + i]) {
                dst[i]++;
            }
            if(i + dst[i] - 1 > r) {
                l = i;
                r = i + dst[i] - 1;
            }
        }
    }
    
}
