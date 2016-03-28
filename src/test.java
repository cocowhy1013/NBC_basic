import java.util.ArrayList;

/**
 * Created by Coco on 2016/3/28.
 */
public class test {
    boolean isClassfication[];
    ArrayList<Double> lblClass=new ArrayList<Double>();  //存储目标值的种类
    ArrayList<Integer>lblCount=new ArrayList<Integer>();//存储目标值的个数
    ArrayList<Float>lblProba=new ArrayList<Float>();//存储对应的label的概率
    //CountProbility countlblPro;
    /*@ClassListBasedLabel是将训练数组按照 label的顺序来分类存储*/

    boolean []isCatory;
    double[][]features;
    private double[]labels;

    ArrayList<ArrayList<ArrayList<Double>>> ClassListBasedLabel=new  ArrayList<ArrayList<ArrayList<Double>>> ();
    ArrayList<ArrayList<ArrayList<Double>>> ClassListBasedLabel2=new ArrayList<ArrayList<ArrayList<Double>>> () ;

    public test() {
    }
    public void getClassListBasedLabel
            (ArrayList<Double>lblClass,ArrayList<ArrayList<Double>>trainingList)
    {
        /*for(double num:lblClass)
        {
            ArrayList<ArrayList<Double>> elements=new ArrayList<ArrayList<Double>>();
            for(ArrayList<Double>element:trainingList)
            {
                if(element.get(element.size()-1).equals(num))
                    elements.add(element);
            }
            ClassListBasedLabel.add(elements);
        }*/
        for(double num = 0;num<lblClass.size();num++){
            ArrayList<ArrayList<Double>> elements=new ArrayList<ArrayList<Double>>();
            /*for(int i=0;i<trainingList.size();i++){
                ArrayList<Double>element = trainingList.get(i);
                if(element.get(element.size()-1).equals(num))
                    elements.add(element);
            }*/
           // ClassListBasedLabel2.add(elements);
        }
        //return ClassListBasedLabel;
    }
    public double getMean(ArrayList<ArrayList<Double>> elements,int index)
    {
        double sum=0.0;
        double Mean;

        for(ArrayList<Double> element:elements)
        {
            sum+=element.get(index);

        }
        Mean=sum/(double)elements.size();
        return  Mean;
    }
    public double getSdev(ArrayList<ArrayList<Double>> elements,int index)
    {
        double dev=0.0;
        double Mean;
        Mean=getMean(elements,index);
        for(ArrayList<Double> element:elements)
        {
            dev+=Math.pow((element.get(index)-Mean),2);
        }
        dev=Math.sqrt(dev/elements.size());
        return  dev;
    }
}
