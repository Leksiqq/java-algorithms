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

package net.leksi.algo.datas;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Datas {

    final static String SPACE = " ";
    
    public static TreeMap<Integer, Integer> mapc(final int[] a) {
        return IntStream.range(0, a.length).collect(
                () -> new TreeMap<Integer, Integer>(),
                (res, i) -> {
                    res.put(a[i], res.getOrDefault(a[i], 0) + 1);
                },
                Map::putAll
        );
    }

    public static TreeMap<Long, Integer> mapc(final long[] a) {
        return IntStream.range(0, a.length).collect(
                () -> new TreeMap<Long, Integer>(),
                (res, i) -> {
                    res.put(a[i], res.getOrDefault(a[i], 0) + 1);
                },
                Map::putAll
        );
    }

    public static <T> TreeMap<T, Integer> mapc(final T[] a, Comparator<T> cmp) {
        return IntStream.range(0, a.length).collect(
                cmp != null ? () -> new TreeMap<T, Integer>(cmp) : () -> new TreeMap<T, Integer>(),
                (res, i) -> {
                    res.put(a[i], res.getOrDefault(a[i], 0) + 1);
                },
                Map::putAll
        );
    }

    public static <T> TreeMap<T, Integer> mapc(final T[] a) {
        return mapc(a, null);
    }

    public static TreeMap<Integer, Integer> mapc(final IntStream a) {
        return a.collect(
                () -> new TreeMap<Integer, Integer>(),
                (res, v) -> {
                    res.put(v, res.getOrDefault(v, 0) + 1);
                },
                Map::putAll
        );
    }

    public static TreeMap<Long, Integer> mapc(final LongStream a) {
        return a.collect(
                () -> new TreeMap<Long, Integer>(),
                (res, v) -> {
                    res.put(v, res.getOrDefault(v, 0) + 1);
                },
                Map::putAll
        );
    }

    public static <T> TreeMap<T, Integer> mapc(final Stream<T> a, Comparator<T> cmp) {
        return a.collect(
                cmp != null ? () -> new TreeMap<T, Integer>(cmp) : () -> new TreeMap<T, Integer>(),
                (res, v) -> {
                    res.put(v, res.getOrDefault(v, 0) + 1);
                },
                Map::putAll
        );
    }

    public static <T> TreeMap<T, Integer> mapc(final Stream<T> a) {
        return mapc(a, null);
    }

    public static <T> TreeMap<T, Integer> mapc(final Collection<T> a, Comparator<T> cmp) {
        return mapc(a.stream(), cmp);
    }

    public static <T> TreeMap<T, Integer> mapc(final Collection<T> a) {
        return mapc(a.stream());
    }

    public static TreeMap<Integer, List<Integer>> mapi(final int[] a) {
        return IntStream.range(0, a.length).collect(
                () -> new TreeMap<Integer, List<Integer>>(),
                (res, i) -> {
                    if (!res.containsKey(a[i])) {
                        res.put(a[i], Stream.of(i).collect(Collectors.toList()));
                    } else {
                        res.get(a[i]).add(i);
                    }
                },
                Map::putAll
        );
    }

    public static TreeMap<Long, List<Integer>> mapi(final long[] a) {
        return IntStream.range(0, a.length).collect(
                () -> new TreeMap<Long, List<Integer>>(),
                (res, i) -> {
                    if (!res.containsKey(a[i])) {
                        res.put(a[i], Stream.of(i).collect(Collectors.toList()));
                    } else {
                        res.get(a[i]).add(i);
                    }
                },
                Map::putAll
        );
    }

    public static <T> TreeMap<T, List<Integer>> mapi(final T[] a, Comparator<T> cmp) {
        return IntStream.range(0, a.length).collect(
                cmp != null ? () -> new TreeMap<T, List<Integer>>(cmp) : () -> new TreeMap<T, List<Integer>>(),
                (res, i) -> {
                    if (!res.containsKey(a[i])) {
                        res.put(a[i], Stream.of(i).collect(Collectors.toList()));
                    } else {
                        res.get(a[i]).add(i);
                    }
                },
                Map::putAll
        );
    }

    public static <T> TreeMap<T, List<Integer>> mapi(final T[] a) {
        return mapi(a, null);
    }

    public static TreeMap<Integer, List<Integer>> mapi(final IntStream a) {
        int[] i = new int[]{0};
        return a.collect(
                () -> new TreeMap<Integer, List<Integer>>(),
                (res, v) -> {
                    if (!res.containsKey(v)) {
                        res.put(v, Stream.of(i[0]).collect(Collectors.toList()));
                    } else {
                        res.get(v).add(i[0]);
                    }
                    i[0]++;
                },
                Map::putAll
        );
    }

    public static TreeMap<Long, List<Integer>> mapi(final LongStream a) {
        int[] i = new int[]{0};
        return a.collect(
                () -> new TreeMap<Long, List<Integer>>(),
                (res, v) -> {
                    if (!res.containsKey(v)) {
                        res.put(v, Stream.of(i[0]).collect(Collectors.toList()));
                    } else {
                        res.get(v).add(i[0]);
                    }
                    i[0]++;
                },
                Map::putAll
        );
    }

    public static <T> TreeMap<T, List<Integer>> mapi(final Stream<T> a, Comparator<T> cmp) {
        int[] i = new int[]{0};
        return a.collect(
                cmp != null ? () -> new TreeMap<T, List<Integer>>(cmp) : () -> new TreeMap<T, List<Integer>>(),
                (res, v) -> {
                    if (!res.containsKey(v)) {
                        res.put(v, Stream.of(i[0]).collect(Collectors.toList()));
                    } else {
                        res.get(v).add(i[0]);
                    }
                    i[0]++;
                },
                Map::putAll
        );
    }

    public static <T> TreeMap<T, List<Integer>> mapi(final Stream<T> a) {
        return mapi(a, null);
    }

    public static <T> TreeMap<T, List<Integer>> mapi(final Collection<T> a, Comparator<T> cmp) {
        return mapi(a.stream(), cmp);
    }

    public static <T> TreeMap<T, List<Integer>> mapi(final Collection<T> a) {
        return mapi(a.stream());
    }

    public static List<int[]> listi(final int[] a) {
        return IntStream.range(0, a.length).mapToObj(i -> new int[]{a[i], i}).collect(Collectors.toList());
    }

    public static List<long[]> listi(final long[] a) {
        return IntStream.range(0, a.length).mapToObj(i -> new long[]{a[i], i}).collect(Collectors.toList());
    }

    public static <T> List<Pair<T, Integer>> listi(final T[] a) {
        return IntStream.range(0, a.length).mapToObj(i -> new Pair<T, Integer>(a[i], i)).collect(Collectors.toList());
    }

    public static List<int[]> listi(final IntStream a) {
        int[] i = new int[]{0};
        return a.mapToObj(v -> new int[]{v, i[0]++}).collect(Collectors.toList());
    }

    public static List<long[]> listi(final LongStream a) {
        int[] i = new int[]{0};
        return a.mapToObj(v -> new long[]{v, i[0]++}).collect(Collectors.toList());
    }

    public static <T> List<Pair<T, Integer>> listi(final Stream<T> a) {
        int[] i = new int[]{0};
        return a.map(v -> new Pair<T, Integer>(v, i[0]++)).collect(Collectors.toList());
    }

    public static <T> List<Pair<T, Integer>> listi(final Collection<T> a) {
        int[] i = new int[]{0};
        return a.stream().map(v -> new Pair<T, Integer>(v, i[0]++)).collect(Collectors.toList());
    }

    public static String join(final int[] a) {
        return Arrays.stream(a).mapToObj(Integer::toString).collect(Collectors.joining(SPACE));
    }

    public static String join(final long[] a) {
        return Arrays.stream(a).mapToObj(Long::toString).collect(Collectors.joining(SPACE));
    }

    public static <T> String join(final T[] a) {
        return Arrays.stream(a).map(v -> Objects.toString(v)).collect(Collectors.joining(SPACE));
    }

    public static <T> String join(final T[] a, final Function<T, String> toString) {
        return Arrays.stream(a).map(v -> toString.apply(v)).collect(Collectors.joining(SPACE));
    }

    public static <T> String join(final Collection<T> a) {
        return a.stream().map(v -> Objects.toString(v)).collect(Collectors.joining(SPACE));
    }

    public static <T> String join(final Collection<T> a, final Function<T, String> toString) {
        return a.stream().map(v -> toString.apply(v)).collect(Collectors.joining(SPACE));
    }

    public static <T> String join(final Stream<T> a) {
        return a.map(v -> Objects.toString(v)).collect(Collectors.joining(SPACE));
    }

    public static <T> String join(final Stream<T> a, final Function<T, String> toString) {
        return a.map(v -> toString.apply(v)).collect(Collectors.joining(SPACE));
    }

    public static <T> String join(final IntStream a) {
        return a.mapToObj(Integer::toString).collect(Collectors.joining(SPACE));
    }

    public static <T> String join(final LongStream a) {
        return a.mapToObj(Long::toString).collect(Collectors.joining(SPACE));
    }

    public static List<Integer> list(final int[] a) {
        return Arrays.stream(a).mapToObj(Integer::valueOf).collect(Collectors.toList());
    }

    public static List<Integer> list(final IntStream a) {
        return a.mapToObj(Integer::valueOf).collect(Collectors.toList());
    }

    public static List<Long> list(final long[] a) {
        return Arrays.stream(a).mapToObj(Long::valueOf).collect(Collectors.toList());
    }

    public static List<Long> list(final LongStream a) {
        return a.mapToObj(Long::valueOf).collect(Collectors.toList());
    }

    public static <T> List<T> list(final Stream<T> a) {
        return a.collect(Collectors.toList());
    }

    public static <T> List<T> list(final Collection<T> a) {
        return a.stream().collect(Collectors.toList());
    }

    public static <T> List<T> list(final T[] a) {
        return Arrays.stream(a).collect(Collectors.toList());
    }

    public static String yesNo(final boolean res) {
        return res ? "YES" : "NO";
    }

    public static String dump(Object obj) {
        String res = "";
        if(obj != null) {
            Class cl = obj.getClass();
            String cls = cl.getName();
            if(cls.startsWith("[")) {
                res += "[";
                for(int i = 0;; i++) {
                    try {
                        Object o = Array.get(obj, i);
                        String s = dump(o);
                        if(i > 0) {
                             res += ", ";
                        }
                        res += s;
                    } catch(ArrayIndexOutOfBoundsException ex) {
                        break;
                    }
                }
                res += "]";
            } else if(Collection.class.isAssignableFrom(cl)) {
                @SuppressWarnings("unchecked")
                final Object s = ((Collection)obj).stream().map(v -> dump(v)).collect(Collectors.joining(", ", "[", "]"));
                res += s.toString();
            } else if(Map.class.isAssignableFrom(cl)) {
                @SuppressWarnings("unchecked")
                final Object s = ((Map)obj).entrySet().stream().map(v -> dump(v)).collect(Collectors.joining(", ", "{", "}"));
                res += s.toString();
            } else if (Character.class.isInstance(obj)
                || Integer.class.isInstance(obj)
                || Long.class.isInstance(obj)
                || Float.class.isInstance(obj)
                || Double.class.isInstance(obj)
                || String.class.isInstance(obj)
            ) {
                res += Objects.toString(obj);
            } else if(Map.Entry.class.isInstance(obj)) {
                res += dump(((Map.Entry)obj).getKey()) + "=" + dump(((Map.Entry)obj).getValue());
            } else if(Stream.class.isInstance(obj)) {
                @SuppressWarnings("unchecked")
                final Object s = ((Stream)obj).map(v -> dump(v)).collect(Collectors.joining(", ", "[", "]"));
                res += s.toString();
            } else {
                res += Stream.concat(Arrays.stream(obj.getClass().getFields()).map(v -> {
                    String name = v.getName();
                    String val;
                    try {
                        Object o = v.get(obj);
                        if(o != null && v.isAnnotationPresent(Dump.class)) {
                            Dump an = v.getAnnotation(Dump.class);
                            Class ocl = o.getClass();
                            val = "{";
                            for(String fn: an.fields()) {
                                try {
                                    Field f = ocl.getField(fn);
                                    val += fn + "=" + dump(f.get(o)) + ", ";
                                } catch(NoSuchFieldException nsfex){
                                    try {
                                        @SuppressWarnings("unchecked")
                                        final Method m = ocl.getMethod(fn);
                                        val += fn + "=" + dump(m.invoke(o)) + ", ";
                                    } catch(NoSuchMethodException | IllegalArgumentException | InvocationTargetException nsmex){}
                                }
                            }
                            if(val.endsWith(", ")) {
                                val = val.substring(0, val.length() - 2);
                            }
                            val += "}";
                        } else {
                            val = dump(o);
                        }
                    } catch(IllegalAccessException ex) {
                        val = "N/A";
                    }
                    return  name + "=" + val;
                }), Arrays.stream(obj.getClass().getMethods()).filter(m -> m.isAnnotationPresent(Getter.class)).map(m -> {
                    String name = m.getName();
                    String val;
                    try {
                        Object o = m.invoke(obj);
                        if(o != null && m.isAnnotationPresent(Dump.class)) {
                            Dump an = m.getAnnotation(Dump.class);
                            Class ocl = o.getClass();
                            val = "{";
                            for(String fn: an.fields()) {
                                try {
                                    Field f = ocl.getField(fn);
                                    val += fn + "=" + dump(f.get(o)) + ", ";
                                } catch(NoSuchFieldException nsfex){
                                    try {
                                        @SuppressWarnings("unchecked")
                                        final Method m1 = ocl.getMethod(fn);
                                        val += fn + "=" + dump(m1.invoke(o)) + ", ";
                                    } catch(NoSuchMethodException | IllegalArgumentException | InvocationTargetException nsmex){}
                                }
                            }
                            if(val.endsWith(", ")) {
                                val = val.substring(0, val.length() - 2);
                            }
                            val += "}";
                        }else {
                            val = dump(o);
                        }
                    } catch(IllegalAccessException | InvocationTargetException ex) {
                        val= "N/A";
                    }
                    return name + "=" + val;
                })).collect(Collectors.joining(", ", "{" + obj.getClass().getName() + ": ", "}"));
//                res = obj.getClass().getName();
            }
        }
        if(res.length() == 0) {
            res = "<null>";
        }
        return res;
    }
}
