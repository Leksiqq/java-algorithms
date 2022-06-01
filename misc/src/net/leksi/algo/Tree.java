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

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Tree<T extends TreeNode> {
    
    public interface ChildrenBuilder<T> { 
        void build(final T parent, final int level, final Consumer<T> add);
    }
    
    final protected Comparator<TreeNode> wcmp = (x, y) -> x.id() - y.id();
    private T root = null;
    private T current_parent = null;
    private Predicate<T> is_leaf;
    final private Deque<T> queue = new ArrayDeque<>();
    private int maxLevel = 0;

    public int maxLevel() {
        return maxLevel;
    }

    final protected Consumer<T> addChildNode = child -> {
        child.parent = current_parent;
        if(current_parent != null) {
            if(current_parent.children == null) {
                current_parent.children = new TreeSet<TreeNode>(wcmp);
            }
            current_parent.children.add(child);
            child.level = current_parent.level + 1;
            if(maxLevel < child.level) {
                maxLevel = child.level;
            }
        }
        if(!is_leaf.test(child)) {
            queue.offer(child);
        }
    };
    
    public void build(final T root, final ChildrenBuilder<T> children_builder, final Predicate<T> is_leaf) {
        this.is_leaf = is_leaf;
        maxLevel = 0;
        current_parent = null;
        queue.clear();
        this.root = root;
        addChildNode.accept(root);
        while (!queue.isEmpty()) {
            current_parent = queue.poll();
            children_builder.build(current_parent, current_parent.level + 1, addChildNode);
        }
        TreeNode.id_gen = 0;
    }
    
    public T root() {
        return root;
    }

    public void removeChild(final TreeNode parent, final TreeNode child) {
        if(parent.children != null) {
            parent.children.remove(child);
            child.parent = null;
        }
    }
}
