// https://leetcode.com/explore/challenge/card/may-leetcoding-challenge/534/week-1-may-1st-may-7th/3322/

import java.util.function.Predicate;
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class AlgoAreTwoNodesCousinsBinaryIntTree {
    public boolean isCousins(final TreeNode root, final int x, final int y) {
        return root != null
            && root.val != x
            && root.val != y
            && isCousins(searchMatching(root, getValEqOneOfPred(x, y)));
    }

    public boolean isCousins(final NodeSpot[] nodeSpots) {        
        if (nodeSpots == null || nodeSpots.length < 2) return false;

        final NodeSpot first = nodeSpots[0];
        for (int i = 1; i < nodeSpots.length; i++) {
            final NodeSpot nodeSpot = nodeSpots[i];
            if (nodeSpot == null
                    || nodeSpot.getDepth() != first.getDepth()
                    || nodeSpot.getParent().equals(first.getParent())) {
                return false;
            }
        }
        return true;
    }
     
    /**
     * This version works only with two values e.g. with {@link #getValEqOneOfPred}.
     */
    private NodeSpot[] searchMatching(final TreeNode node, final Predicate<TreeNode> pred) {
        return searchMatching(node, pred, null, 0, new NodeSpot[2]);
    }
    
    /**
     * This version works only with two values e.g. with {@link #getValEqOneOfPred}.
     */
    private NodeSpot[] searchMatching(final TreeNode node, final Predicate<TreeNode> pred,
                                      final TreeNode parent, final int depth, final NodeSpot[] res) {
        if (node == null) {
            return res;
        }

        if (pred.test(node)) {
            final int nextIdx = res[0] == null ? 0 : 1;            
            res[nextIdx] = new NodeSpot(parent, depth);
            if (res[1] != null) {
                return res;
            }
        }
        
        searchMatching(node.left, pred, node, depth + 1, res);
        searchMatching(node.right, pred, node, depth + 1, res);
        return res;
    }
    
    private Predicate<TreeNode> getValEqOneOfPred(final int x, final int y) {
        return node -> node != null && (node.val == x || node.val == y);
    }
    
    private static final class NodeSpot {
        final TreeNode parent;
        final int depth;
        
        public NodeSpot(final TreeNode parent, final int depth) {
            this.parent = parent;
            this.depth = depth;
        }
        
        public TreeNode getParent() {
            return parent;
        }
        
        public int getDepth() {
            return depth;
        }
        
        @Override
        public String toString() {
            return "NodeSpot {\n"
                + "  parent: " + parent + ",\n"
                + "  depth: " + depth + "\n"
                + "}";
        }
    }
}
