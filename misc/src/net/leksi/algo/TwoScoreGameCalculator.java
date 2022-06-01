/**
 *
 * @author leksi
 */
package net.leksi.algo;

import java.io.PrintWriter;
import java.util.Stack;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class TwoScoreGameCalculator extends Tree<Move> {

    static final int SHOW_ALL = 1;
    static final int SHOW_OPTIMAL = 2;

    public interface PossibleMovesBuilder<T> {
        void build(final T parent, final int move_number, final int player, final Consumer<Move> add);
    }
    
    private int show = 0;

    private PrintWriter pw = new PrintWriter(System.out);


    public void show(final int value) {
        show = value;
    }

    public void setPrintWriter(final PrintWriter value) {
        this.pw = value;
    }
    
    final protected Consumer<Move> addMove = move -> {
        addChildNode.accept(move);
        move.player = ((Move)move.parent).player ^ 1;
    };

    public int calculate(final Move initial_state, final PossibleMovesBuilder<Move> movies_builder, final Predicate<Move> cannot_move) {
        TreeSet<Move> leaves0 = new TreeSet<>(wcmp);
        TreeSet<Move> leaves1 = new TreeSet<>(wcmp);
        int[] selector = new int[1];
        Supplier<TreeSet<Move>> leaves_src = () -> selector[0] % 2 == 0 ? leaves0 : leaves1;
        Supplier<TreeSet<Move>> leaves_dst = () -> selector[0] % 2 == 1 ? leaves0 : leaves1;
        build(initial_state, (parent, level, add) -> {
            movies_builder.build(parent, level, parent.player ^ 1, addMove);
        }, m -> {
            if(cannot_move.test(m)) {
                if(m.scores[0] == m.scores[1]) {
                    m.variants |= Move.DRAW;
                } else if(m.scores[0] > m.scores[1]) {
                    m.variants |= Move.FIRST;
                } else {
                    m.variants |= Move.SECOND;
                }
                leaves_src.get().add(m);
                return true;
            }
            return false;
        });
        leaves_src.get().forEach(m -> {
            for (Move p = m; p.parent != null; p = (Move) p.parent) {
                ((Move)p.parent).variants |= p.variants;
            }
        });
        Consumer<Move> walk = new Consumer<Move>() {
            @Override
            public void accept(Move move) {
                if(move.player == 0 && move.variants == Move.FIRST || move.player == 1 && move.variants == Move.SECOND) {
                    move.children = null;
                    leaves_src.get().add(move);
                } else {
                    if(move.children != null) {
                        move.children.stream().forEach(child -> accept((Move)child));
                    } else {
                        leaves_src.get().add(move);
                    }
                }
            }
        };
        leaves_src.get().clear();
        walk.accept(root());
        if((show & SHOW_ALL) == SHOW_ALL) {
            pw.println("Scenarios (all):");
            showScenarios(leaves_src.get());
            pw.println("/Scenarios");
        }
        Consumer<Move> remove_rec = new Consumer<Move>() {
            @Override
            public void accept(Move move) {
                if(move.children == null) {
                    leaves_src.get().remove(move);
                    leaves_dst.get().remove(move);
                } else {
                    move.children.forEach(child -> accept((Move)child));
                }
            }
        };
        boolean changed = true;
        while(changed && !leaves_src.get().isEmpty()) {
            changed = false;
            Move move = leaves_src.get().pollFirst();
            boolean found = false;
            if (move.level >= 1) {
                Move parent = (Move) move.parent;
                if(move.player == 0 && move.variants != Move.FIRST || move.player == 1 && move.variants != Move.SECOND) {
                    if(parent.children.size() > 1) {
                        parent.children.remove(move);
                        remove_rec.accept(move);
                        found = true;
                    }
                } else {
                    for (; parent != null; ) {
                        Move grand_parent = (Move) parent.parent;
                        if (grand_parent != null) {
                            if (grand_parent.children.size() > 1) {
                                grand_parent.children.remove(parent);
                                remove_rec.accept(parent);
                                found = true;
                                break;
                            }
                        }
                        parent = grand_parent;
                    }
                    if (!found) {
                        if (move.level > 0) {
                            if (parent.children.size() > 1) {
                                parent.children.removeIf(child -> {
                                    if (((Move) child).variants != move.variants) {
                                        remove_rec.accept((Move) child);
                                        return true;
                                    }
                                    return false;
                                });
                            }
                        }
                    }
                }
            }
            if(!found) {
                leaves_dst.get().add(move);
            } else {
                changed = true;
            }
            selector[0]++;
            leaves_dst.get().clear();
        }
        if((show & SHOW_OPTIMAL) == SHOW_OPTIMAL) {
            pw.println("Scenarios (optimal):");
            showScenarios(leaves1);
            pw.println("/Scenarios");
        }
        int res = -2;
        for(Move move: leaves1) {
            int cur = move.variants == Move.DRAW ? -1 : (
                move.variants == Move.FIRST ? 0 : 1
            );
            if(res != -2) {
                if(cur != res) {
                    res = -2;
                    break;
                }
            } else {
                res = cur;
            }
        }
        return res;
    }

    private void showScenarios(TreeSet<Move> leaves) {
        Stack<Move> path = new Stack<>();
        leaves.stream().forEach((_item) -> {
            for (Move p = _item; p != null; p = (Move) p.parent) {
                path.push(p);
            }
            pw.println("--- scenario ---");
            while (!path.isEmpty()) {
                pw.println(path.pop());
            }
        });
    }

}
