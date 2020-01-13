package dataStructure;

import java.util.Comparator;
/**
 * This class implements a node comperator.
 * The comparison is by the weight of the nodes.
 * @return -1 if the right node is bigger.
 * @return 1 if the left node is bigger.
 * @return 0 if equals.
 */
public class nodeComperator implements Comparator<node> {
    @Override
    public int compare(node o1, node o2) {
        if(o1.getWeight()>o2.getWeight()){
            return 1;
        }
        if(o1.getWeight()<o2.getWeight()){
            return -1;
        }
        return 0;
    }
}
