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
import java.util.Arrays;
import java.util.List;

public class GraphChains {
    static public class Edge {

        public int v1, v2;

        public Edge(int v1, int v2) {
            this.v1 = v1;
            this.v2 = v2;
        }
    }
    
    interface DFSchain {
        void get(int u, int endV, String s);
    }
    
    static private class Reenter {
        DFSchain f = null;
    }
    
    static public List<int[]> chainsSearch(List<Edge> edges, int vertex_count) {
        int[] color = new int[vertex_count];
        Reenter ree = new Reenter();
        ArrayList<int[]> res = new ArrayList<>();
        ree.f = (u, endV ,s) -> {
            if (u != endV) {
                color[u] = 2;
            } else {
                res.add(Arrays.stream(s.split("-")).mapToInt(Integer::valueOf).toArray());
                return;
            }
            for (int w = 0; w < edges.size(); w++) {
                Edge e = edges.get(w);
                if (color[e.v2] == 1 && e.v1 == u) {
                    ree.f.get(e.v2, endV, s + "-" + Integer.toString(e.v2));
                    color[edges.get(w).v2] = 1;
                } else if (color[e.v1] == 1 && e.v2 == u) {
                    ree.f.get(e.v1, endV, s + "-" + Integer.toString(e.v1));
                    color[e.v1] = 1;
                }
            }
        };
        for (int i = 0; i < vertex_count - 1; i++) {
            for (int j = i + 1; j < vertex_count; j++) {
                for (int k = 0; k < vertex_count; k++) {
                    color[k] = 1;
                }
                ree.f.get(i, j, Integer.toString(i));
            }
        }
        return res;
    }
}
