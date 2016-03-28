import java.util.ArrayList;

/**
 * Created by Coco on 2016/3/28.
 */
public class test2 {
    public void function(){
        ArrayList<ArrayList<Double>> elements=new ArrayList<ArrayList<Double>>();
        for(ArrayList<Double> e:elements){
            e.add(Double.parseDouble("111"));
            int i = 0;
            i++;
            e.add(Double.parseDouble(i+""));
        }
    }
}

