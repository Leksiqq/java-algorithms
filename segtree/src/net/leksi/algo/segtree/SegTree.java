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
package net.leksi.algo.segtree;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SegTree {
    private int max_level;
    private int n2;
    private int trlen;
    private int n;
    private DriverUpdater updater = null;
    private DriverInitializator setter = null;
    private DriverGetter getter = null;
    private DriverInitializator initializator = null;
    private int get_result;
    private int get_pos;
    private boolean break_cycle = false;

    final public void setUpdater(final DriverUpdater updater) {
        this.updater = updater;
    }
    final public DriverUpdater getUpdater() {
        return updater;
    }

    final public void setSetter(final DriverInitializator setter) {
        this.setter = setter;
    }
    final public DriverInitializator getSetter() {
        return setter;
    }

    final public void setGetter(final DriverGetter getter) {
        this.getter = getter;
    }
    final public DriverGetter getGetter() {
        return getter;
    }

    final public void setInitializator(final DriverInitializator initializator) {
        this.initializator = initializator;
    }
    final public DriverInitializator getInitializator() {
        return initializator;
    }

    final public void configure(final int n) {
        this.n = n;
        max_level = (int) Math.ceil(Math.log(n) / Math.log(2));
        n2 = 1 << max_level;
        trlen = (n2 << 1) - 1;
    }

    final public String treeToString(final Function<Integer, String> itemToString) {
        Function<Integer, String> its = itemToString == null ? p -> "true" : itemToString;
        StringBuilder sb = new StringBuilder();
        int[] start = new int[]{0};
        int stop = 1;
        int[] w = new int[]{n2};
        sb.append("[");
        while(start[0] < trlen) {
            sb.append(IntStream.range(start[0], stop).mapToObj(p -> (p - start[0]) * w[0] < n ? its.apply(p) : "-")
                    .collect(Collectors.joining(", ", "(", ")")));
            start[0] = stop;
            stop = (stop << 1) + 1;
            w[0] >>= 1;
        }
        sb.append("]");
        return sb.toString();
    }

    final public void init() {
        if(initializator != null) {
            for(int i = 0; i < n; i++) {
                initializator.accept(i, trlen - n2 + i);
            }
        }
    }

    final public void boot() {
        if(updater != null) {
            break_cycle = false;
            int end;
            int prev_end = 0;
            end = trlen - n2 + n - 1;
            int start = trlen >> 1;
            for(int p = start - 1; p >= 0; p--) {
                if(p < start) {
                    prev_end = end;
                    end = (end - 1) >> 1;
                    p = end;
                    start >>= 1;
                }
                int left = (p << 1) + 1;
                updater.accept(QueryType.BOOT, p, left, left < prev_end ? left + 1 : -1);
                if(break_cycle) {
                    break;
                }
            }
        }
    }

    final public void boot(final int pos) {
        if(updater != null && pos < trlen - n2) {
            int start = pos;
            int stop = pos + 1;
            int next;
            while((next = (start << 1) + 1) < trlen - n2) {
                start = next;
                stop = (stop << 1) + 1;
            }
            int prev_end = trlen - n2 + n - 1;
            int end = Math.min(stop - 1, (prev_end - 1) >> 1);
            break_cycle = false;
            for(int p = end; p >= pos; p--) {
                int left = (p << 1) + 1;
                updater.accept(QueryType.BOOT, p, left, left < prev_end ? left + 1 : -1);
                if(break_cycle) {
                    break;
                }
                if(p == start) {
                    prev_end = end;
                    start >>= 1;
                    end = (end - 1) >> 1;
                    p = end + 1;
                }
            }
        }
    }

    final public void breakCycle() {
        break_cycle = true;
    }

    final public void set(final int i) {
        int p = trlen - n2 + i;
        if(setter != null) {
            setter.accept(i, p);
        }
        break_cycle = false;
        if(updater != null) {
            int end = trlen - n2 + n - 1;
            while(p > 0) {
                int left = ((p & 1) == 1) ? p : p - 1;
                p = (p - 1) >> 1;
                updater.accept(QueryType.SET, p, left, left < end ? left + 1 : -1);
                if(break_cycle) {
                    break;
                }
                end = (end - 1) >> 1;
            }
        }
    }

    final public int get(final int l, final int r) {
        break_cycle = false;
        get_result = -1;
        get_pos = 0;
        get(0, l, r, 0, n2);
        return get_result;
    }

    final public int getLength() {
        return n2;
    }

    final public int getTreeLength() {
        return trlen;
    }

    final public int getStart() {
        return trlen - n2;
    }

    private void get(final int pos, final int l, final int r, final int b, final int e) {
        if(!break_cycle) {
            if(b < r && e > l) {
                int e1 = Math.min(e, n);
                if(b >= l && e1 <= r) {
                    if(getter != null) {
                        getter.accept(pos);
                    }
                    if(get_result == -1) {
                        get_result = pos;
                    } else if(updater != null) {
                        updater.accept(QueryType.GET, trlen + get_pos, get_result, pos);
                        get_result = trlen + get_pos;
                        get_pos ^= 1;
                    }
                } else if(e - b > 1) {
                    int left = (pos << 1) + 1;
                    int m = (b + e) >> 1;
                    get(left, l, r, b, m);
                    get(left + 1, l, r, m, e);
                }
            }
        }
    }

}
