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

public class Move extends TreeNode {
    static final int DRAW = 1;
    static final int FIRST = 2;
    static final int SECOND = 4;

    public int player = 1;
    public int[] scores = new int[2];
    int variants = 0;
    private String variantsToString() {
        String res = ((variants & DRAW) == DRAW ? "DRAW|" : "") + ((variants & FIRST) == FIRST ? "FIRST|" : "") + ((variants & SECOND) == SECOND ? "SECOND|" : "");
        if(res.endsWith("|")) {
            res = res.substring(0, res.length() - 1);
        }
        return res;
    }
    public String toString() {
        return "{id: " + id() + (parent() != null ?  ", player: " + player : "") +
                ", scores: (" + scores[0] + ":" + scores[1] + ")" +
                (parent() != null ? ", parent: " + ((Move)parent()).id() : "") + ", level: " + level() + ", variants: " + variantsToString() + "}";
    }
}
