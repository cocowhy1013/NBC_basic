//package dm13;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

//import auxiliary.DataSet;
//import auxiliary.Evaluation;
public class Main_Origin {

    public static double[] arrayFromArrayList(ArrayList<Double> list){
        double[] array = new double[list.size()];
        for(int i=0;i<list.size();i++)
            array[i] = list.get(i);
        return array;

    }
    public static double[] processMain(String trainFile, String testFile) throws FileNotFoundException{
        Scanner scan = new Scanner(new File(testFile));
        ArrayList<Double> result = new ArrayList<Double>();
        String line = scan.nextLine();//remove first line
        int result_no = 0;
        //line = scan.nextLine();
        while(scan.hasNextLine()){

            line=scan.nextLine();
            String[] parts = line.split(",");
            String testLine = "";
            for(int i=0;i<parts.length;i++){
                testLine = testLine + parts[i];
                if(i!=parts.length-1)
                    testLine = testLine + ",";
            }
//			System.out.println(testLine);


            DataSet dataset = new DataSet(trainFile);

            //String test = "3,2,4,1,4,5,0,4,3";
            String[] atts = testLine.split(",");

            double[][] features = new double[1][atts.length-1];
            double[] labels = new double[1];

            int ind = 0;

            for (int i = 0; i < atts.length - 1; i++) {
                features[ind][i] = Double.parseDouble(atts[i]);
            }
            labels[ind] = Double.parseDouble(atts[atts.length - 1]);

            // DataSet dataset2 = new DataSet(path+"test.txt");
            // conduct 10-cv
            Evaluation eva = new Evaluation(dataset,features,labels, "NaiveBayes_Origin");
            result.add(eva.testWithData2()[0]);

            //eva.crossValidation();
            // print mean and standard deviation of accuracy

        }
        scan.close();
        return arrayFromArrayList(result);
    }

    public static double processSingleTestMain(String trainFile, String line) throws FileNotFoundException{

        String[] parts = line.split(",");
        String testLine = "";
        for(int i=0;i<parts.length;i++){
            testLine = testLine + parts[i];
            if(i!=parts.length-1)
                testLine = testLine + ",";
        }
        //System.out.println(testLine);


        DataSet dataset = new DataSet(trainFile);

        //String test = "3,2,4,1,4,5,0,4,3";
        String[] atts = testLine.split(",");

        double[][] features = new double[1][atts.length-1];
        double[] labels = new double[1];

        int ind = 0;

        for (int i = 0; i < atts.length - 1; i++) {
            features[ind][i] = Double.parseDouble(atts[i]);
        }
        labels[ind] = Double.parseDouble(atts[atts.length - 1]);

        // DataSet dataset2 = new DataSet(path+"test.txt");
        // conduct 10-cv 
        Evaluation eva = new Evaluation(dataset,features,labels, "NaiveBayes_Origin");
        double[] result = eva.testWithData2();
        return result[0];
        //eva.crossValidation();
    }
    public static double[] modifyMain(String trainFile, String testFile, double possibility, int number) throws IOException{
        Scanner scan = new Scanner(new File(testFile));
        ArrayList<Double> result = new ArrayList<Double>();
        String line = scan.nextLine();//remove first line
        //line = scan.nextLine();
        while(scan.hasNextLine()){
            line=scan.nextLine();
            DataModifier modifier = new DataModifier();
            String targetFile = modifier.modifyRandomStep(trainFile,line, possibility,number,0);
            //System.out.println("-----"+);
            result.add(processSingleTestMain(targetFile,line));
        }
        scan.close();
        return arrayFromArrayList(result);

    }
    public static void main(String[] args) throws IOException{

        String resultFile = "dataset\\1train_result.txt";
        String trainFile = "dataset\\1train.txt";
        String testFile = "dataset\\1test.txt";
        System.out.println(Arrays.toString(processMain(trainFile, testFile)));
        /*PrintWriter pw = new PrintWriter(new File(resultFile));

        int modify_times = 10;
        for(int time = 0; time < modify_times; time ++){
            double[] result1 = modifyMain(trainFile,testFile,0.9,time);
            System.out.println("length1:"+result1.length);
            for(int i=0;i<result1.length;i++)
                ;//System.out.println(result1[i]);

            double [] result2 = processMain(trainFile,testFile);
            System.out.println("length2:"+result2.length);
            for(int i=0;i<result2.length;i++)
                ;//System.out.println(result2[i]);

            boolean isViolated = false;
            if(result1.length!=result2.length)
                ;//System.out.println("Label length does not match!");
            else {
                for(int i=0;i<result1.length;i++){
                    if(result1[i]!=result2[i]){
                        isViolated = true;
                        break;
                    }
                }
                System.out.println("Does it satisfy MR? --- "+!isViolated);
                System.out.println("Continue? --- "+!isViolated);
                pw.println("file:modify_"+time+" Result: "+!isViolated);
            }
        }
        pw.close();
        System.out.println("Finish! ");*/
        //processSingleTestMain("dataset\\1train.txt","1,4,5,0,4,7,5,5,2");
        //double a = processSingleTestMain("dataset\\1train.txt","1,4,5,0,4,7,5,5,2");
        //System.out.println(a);
    }
}
