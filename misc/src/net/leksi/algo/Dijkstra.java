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

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Dijkstra {

    public static void apply(Map<Integer, Map<Integer, Integer>> map, int root, int[] result) {
        Arrays.fill(result, Integer.MAX_VALUE);
        result[root] = 0;
        TreeSet<Integer> visited = new TreeSet<>();
        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.comparingInt(x -> result[x]));
        pq.addAll(IntStream.range(0, result.length).boxed().collect(Collectors.toList()));
        while (!pq.isEmpty()) {
            int v = pq.poll();
            visited.add(v);
            if(map.containsKey(v)) {
                for(Map.Entry<Integer, Integer> u: map.get(v).entrySet()) {
                    if(!visited.contains(u.getKey())) {
                        if(result[u.getKey()] > result[v] + u.getValue()) {
                            pq.remove(u.getKey());
                            result[u.getKey()] = result[v] + u.getValue();
                            pq.add(u.getKey());
                        }
                    }
                }
            }
        }
    }
}
