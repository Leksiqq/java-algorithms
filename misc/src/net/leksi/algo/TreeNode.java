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

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

public class TreeNode {
    static int id_gen  = 0;
    private int id = ++id_gen;
    TreeNode parent = null;
    TreeSet<TreeNode> children = null;
    int level = 0;
    static private TreeSet<TreeNode> empty = new TreeSet<>();
    public  TreeNode parent() {
        return parent;
    }
    public int id() {
        return id;
    }
    public int level() {
        return level;
    }
    public Set<TreeNode> children() {
        return Collections.unmodifiableSet(children != null ? children : empty);
    }
}
