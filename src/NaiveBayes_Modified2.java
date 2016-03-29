//package auxiliary;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//package auxiliary;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Michael Kong
 */
public class NaiveBayes_Modified2 {//dumb NBC
    class Elements{
        ArrayList<Double> valueFeature = new ArrayList<Double>();
        ArrayList<Integer> valueSum = new ArrayList<Integer>();
//        HashMap<Double,Integer> value = new HashMap<Double, Integer>();//Double is its value, and integer stores its occurance times.

    }
    //boolean[] isCategory;
    //double[][] features;
    double[] labels;
    ArrayList<Double> labelList = new ArrayList<Double>();
    HashMap<Double,Integer> labelNumList = new HashMap<Double, Integer>();
    //    ArrayList<Integer> labelNumList = new ArrayList<Integer>();
    Elements[][] elementsAll;
    public NaiveBayes_Modified2() {
    }

    /**
     * @train主要完成求一些概率 1.labels中的不同取值的概率f(Yi);  对应28,29行两段代码
     * 2.将训练数组按目标值分类存储   第37行代码
     */
    public void train(boolean[] isCategory, double[][] features, double[] labels) {
        //this.isCategory = isCategory;
        //this.features = features;
        this.labels = labels;
        for(int i=0;i<labels.length;i++) {
            if (!labelList.contains(labels[i])) {
                labelList.add(labels[i]);
                labelNumList.put(labels[i],1);
                //System.out.println("add label: "+labels[i]);
            }
            else{
                //int index = labelList.indexOf(labels[i]);
                //labelNumList.remove(index);
                labelNumList.put(labels[i],labelNumList.get(new Double(labels[i]))+1);
            }
        }

        elementsAll = new Elements[labelList.size()][features[0].length];

        for(int i=0;i<labelList.size();i++)
            for(int j=0;j<features[0].length;j++)
                elementsAll[i][j] = new Elements();

        for(int i=0;i<features.length;i++){
            int label_Location = labelList.indexOf(labels[i]);
            for(int j=0;j<features[0].length;j++){
                Elements elements = elementsAll[label_Location][j];
                Double key = features[i][j];
                if(elements.valueFeature.contains(key)){
                    int loc = elements.valueFeature.indexOf(key);
                    elements.valueSum.set(loc, elements.valueSum.get(loc)+1);
                }
                else{
                    elements.valueFeature.add(key);
                    elements.valueSum.add(1);
                }
                /*if(elements.value.containsKey(key)) {
                    int temp = elements.value.get(key);
                    elements.value.put(features[i][j],temp+1);
                }
                else
                    elements.value.put(features[i][j],1);*/
            }
        }
        //for(int i=0;i<)
    }

    /**
     * 3.在Y的条件下，计算Xi的概率 f(Xi/Y)；
     * 4.返回使得Yi*Xi*...概率最大的那个label的取值
     */
    public double predict(double[] testfeatures) {
        double[] possibility = new double[labelList.size()];
        for(int i=0;i<possibility.length;i++){//for certain labels
            double result_ForThisLabel=1;
            double numOfThisLabel = labelNumList.get(labelList.get(i));
            int occurace_features = 0;
            for(int j=0;j<testfeatures.length;j++) {
                ArrayList<Double> valueFeature_temp = elementsAll[i][j].valueFeature;
                ArrayList<Integer> valueSum_temp = elementsAll[i][j].valueSum;
                occurace_features = valueSum_temp.get(valueFeature_temp.indexOf(testfeatures[j]));
                //HashMap<Double,Integer> map = elementsAll[i][j].value;
                //occurace_features = map.get(testfeatures[j]);
                result_ForThisLabel = result_ForThisLabel * occurace_features/numOfThisLabel;
            }
            result_ForThisLabel = result_ForThisLabel * numOfThisLabel/labels.length;
            possibility[i] = result_ForThisLabel;
        }
        double max_Label = -1;
        double max_Value = -1;
        for(int i=0;i<possibility.length;i++){
            if(possibility[i]>=max_Value){
                max_Label = labelList.get(i);
                max_Value = possibility[i];
                //System.out.println("modified2");
            }
        }
        return max_Label;
    }
    public void display(){
        System.out.println("labelList:"+labelList.toString());
        System.out.println("labelNumList:"+labelNumList.toString());

        for(int i=0;i<elementsAll.length;i++){
            for(int j=0;j<elementsAll[0].length;j++){
                System.out.print(i+" "+j+" ");
                System.out.println("content1:" + elementsAll[i][j].valueFeature.toString());
                System.out.println("content2:" + elementsAll[i][j].valueSum.toString());
            }
        }
    }
    public static void main(String[] args){
        NaiveBayes_Modified2 modified = new NaiveBayes_Modified2();
        double[][] features = {{1,1},{2,2},{1,4},{3,3},{1,2},{1,3},{2,3},{1,4},{2,4},{3,2}};
        double[] label = {0,1,3,0,0,3,1,1,0,1};
        boolean[] isCategory = {true,true,true,true,true,true};
        modified.train(isCategory,features,label);
        modified.display();
        // System.out.println(lab)
    }
}