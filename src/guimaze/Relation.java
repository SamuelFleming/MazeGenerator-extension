package guimaze;

import java.util.List;
import java.util.ArrayList;

public class Relation{
    /**
     * @author sam.fleming
     * @version 1
     *
     */
    public List<int[]> rel;

    public Relation(int[] a, int[] b){
        rel = new ArrayList<int[]>();
        rel.add(a);
        rel.add(b);
    }

    public void printRel(){
        System.out.print("{ (" + this.rel.get(0)[0] + "," + this.rel.get(0)[1] + "), (" + this.rel.get(1)[0] + "," + this.rel.get(1)[1] + ") }, ");
    }
}
