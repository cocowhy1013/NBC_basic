//package auxiliary;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//package auxiliary;

import java.util.ArrayList;

/**
 *
 * @author Michael Kong
 */
public class NaiveBayes_Modified {//dumb NBC

    boolean[] isCategory;
    double[][] features;
    double[] labels;
    public NaiveBayes_Modified() {
    }

    /**
     * @train主要完成求一些概率 1.labels中的不同取值的概率f(Yi);  对应28,29行两段代码
     * 2.将训练数组按目标值分类存储   第37行代码
     */
    public void train(boolean[] isCategory, double[][] features, double[] labels) {
        this.isCategory = isCategory;
        this.features = features;
        this.labels = labels;
    }

    /**
     * 3.在Y的条件下，计算Xi的概率 f(Xi/Y)；
     * 4.返回使得Yi*Xi*...概率最大的那个label的取值
     */
    public double predict(double[] testfeatures) {
        ArrayList<Double> labelList = new ArrayList<Double>();
        for(int i=0;i<labels.length;i++) {
            if (!labelList.contains(labels[i])) {
                labelList.add(labels[i]);
                //System.out.println("add label: "+labels[i]);
            }
        }
        double[] possibility = new double[labelList.size()];
        //for(int i=0;i<possibility.length;i++)
        //    possibility[i] = 1E-10;
        double[][] isFeatureForLabel = new double[labelList.size()][features[0].length];
        double[] isLabel = new double[labelList.size()];

        for(int i=0;i<features.length;i++){
            int location = labelList.indexOf(labels[i]);
            isLabel[location]++;
            for(int j=0;j<features[0].length;j++){
                //System.out.println("Length:"+features.length);
                //System.out.println("Length:"+features[0].length+" "+Arrays.toString(features[0]));
                //System.out.println("Length:"+testfeatures.length+" "+Arrays.toString(testfeatures));
                if(Math.abs(features[i][j]-testfeatures[j])<0.0001){
                    //System.out.println("gap"+features[i][j]+" "+testfeatures[j]);
                    isFeatureForLabel[location][j]++;
                    //System.out.println("location"+location);
                    //System.out.println("caculate:"+i+" "+j+" "+Arrays.toString(features[i]));
                }
            }
        }
        for(int i=0;i<labelList.size();i++){
            possibility[i] = isLabel[i]/labels.length;
            //System.out.println("possibility:"+possibility[i]);
            for(int j=0;j<isFeatureForLabel[i].length;j++){
                //possibility[i] = possibility[i]*(isFeatureForLabel[i][j])/isLabel[i];
                possibility[i] = possibility[i]/(isFeatureForLabel[i][j])/isLabel[i];

                //System.out.println("isFeatureForLabel:"+(isFeatureForLabel[i][j])/isLabel[i]);
            }
            //System.out.println("Label:"+labelList.get(i)+" possibility:"+possibility[i]);
        }
        double max_Label = -1;
        double max_Value = -1;
        for(int i=0;i<possibility.length;i++){
            if(possibility[i]>=max_Value){
                max_Label = labelList.get(i);
                max_Value = possibility[i];
                //System.out.println(possibility[i]);
            }
        }
        return max_Label;
    }
    public static void main(String [] args){
        NaiveBayes_Modified modified = new NaiveBayes_Modified();
        double[][] features = {{1,1},{2,2},{1,4},{3,3},{1,2},{1,3},{2,3},{1,4},{2,4},{3,2}};
        double[] label = {0,1,2,0,0,2,1,1,0,1};
        boolean[] isCategory = {true,true,true,true,true,true};
        modified.train(isCategory,features,label);
        double[] test_features = {1,3};
        System.out.println("predict: "+modified.predict(test_features));
    }
}