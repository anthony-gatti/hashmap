import java.util.Comparator;

public class TreeMap<K extends Comparable<K>, V> {
    private TreeNode root;
    private int size;
    private Comparator<K> comp;
    static int iterations = 0;

    private class TreeNode {
        MapEntry<K, V> value;
        TreeNode left;
        TreeNode right;

        TreeNode(K key, V val) {
            value = new MapEntry<>(key, val);
            left = right = null;
        }
    }

    public TreeMap(Comparator<K> c) {
        root = null;
        size = 0;
        comp = c;
    }

    /**
     * getter for size
     * 
     * @return int
     */
    public int size() {
        return size;
    }

    /**
     * shows if the map is empty
     * 
     * @return boolean
     */
    public boolean isEmpty() {
        return (size == 0);
    }

    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * finds if the tree contains an email
     * 
     * @param key
     * @return boolean
     */
    public boolean containsKey(K key) {
        TreeNode node = root;
        while (node != null) {
            if (comp.compare(key, node.value.getKey()) < 0)
                node = node.left;
            else if (comp.compare(key, node.value.getKey()) > 0)
                node = node.right;
            else
                return true;
        }
        return false;
    }

    /**
     * returns a node from the tree
     * 
     * 
     * @param key
     * @return V
     */
    public V get(K key) {
        iterations = 0;
        TreeNode node = root;
        while (node != null) {
            iterations++;
            if (comp.compare(key, node.value.getKey()) < 0) {
                node = node.left;
            } else if (comp.compare(key, node.value.getKey()) > 0) {
                node = node.right;
            } else {
                return node.value.getValue();
            }
        }
        return null;
    }

    /**
     * adds a new node
     * 
     * @param key
     * @param value
     * @return boolean
     */
    public boolean add(K key, V value) {
        if (root == null) {
            root = new TreeNode(key, value);
        } else {
            TreeNode parent, node;
            parent = null;
            node = root;
            while (node != null) {
                parent = node;
                if (comp.compare(key, node.value.getKey()) < 0) {
                    node = node.left;
                } else if (comp.compare(key, node.value.getKey()) > 0) {
                    node = node.right;
                } else {
                    return true; // break;
                }
            }
            if (comp.compare(key, parent.value.getKey()) < 0)
                parent.left = new TreeNode(key, value);
            else
                parent.right = new TreeNode(key, value);
        }
        size++;
        return true;
    }

    /**
     * removes a node
     * 
     * @param key
     * @return boolean
     */
    public boolean remove(K key) {
        TreeNode parent, node;
        parent = null;
        node = root;
        // Find value first
        while (node != null) {
            if (comp.compare(key, node.value.getKey()) < 0) {
                parent = node;
                node = node.left;
            } else if (comp.compare(node.value.getKey(), key) > 0) {
                parent = node;
                node = node.right;
            } else
                break;
        }
        if (node == null)
            return false;

        // Case 1: node has no children
        if (node.left == null && node.right == null) {
            if (parent == null) {
                root = null;
            } else {
                if (parent.left == node) {
                    parent.left = null;
                } else {
                    parent.right = null;
                }
            }
        }
        // case 2: node has one right child
        else if (node.left == null) {
            if (parent == null) {
                root = node.right;
            } else {
                if (parent.left == node) {
                    parent.left = node.right;
                } else {
                    parent.right = node.right;
                }
            }
        }
        // case 2: node has one left child
        else if (node.right == null) {
            if (parent == null) {
                root = node.left;
            } else {
                if (parent.left == node) {
                    parent.left = node.left;
                } else {
                    parent.right = node.left;
                }
            }
        }
        // case 3: node has two children
        else {
            TreeNode rightMostParent = node;
            TreeNode rightMost = node.left;
            while (rightMost.right != null) {
                rightMostParent = rightMost;
                rightMost = rightMost.right;
            }
            node.value = rightMost.value;
            if (rightMostParent.left == rightMost) {
                rightMostParent.left = rightMost.left;
            } else {
                rightMostParent.right = rightMost.left;
            }
        }
        size--;
        return true;
    }

    public void inorder() {
        inorder(root);
    }

    /**
     * prints the tree inorder
     * 
     * @param node
     */
    public void inorder(TreeNode node) {
        if (node != null) {
            inorder(node.left);
            System.out.print(node.value.getValue() + " ");
            inorder(node.right);
        }
    }

    public void preorder() {
        preorder(root);
    }

    /**
     * prints the tree preorder
     * 
     * @param node
     */
    public void preorder(TreeNode node) {
        if (node != null) {
            System.out.print(node.value.getValue() + " ");
            preorder(node.left);
            preorder(node.right);
        }
    }

    public void postorder() {
        postorder(root);
    }

    /**
     * prints the tree postorder
     * 
     * @param node
     */
    public void postorder(TreeNode node) {
        if (node != null) {
            postorder(node.left);
            postorder(node.right);
            System.out.print(node.value.getValue() + " ");
        }
    }

    /**
     * @return int
     */
    public int getIterations() {
        return iterations;
    }

}