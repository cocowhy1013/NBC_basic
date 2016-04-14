package project.loader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by Coco on 2016/3/31.haha
 */
public class ResultAnalyzer {
    public void faultDectection(String rootpath) throws FileNotFoundException {
        File root = new File(rootpath);
        for(File f: root.listFiles()){
            if(!f.getName().contains("result"))
                continue;
            int isfaultDetected = 0;
            int detectedTimes = 0;
            double detectedAverageTime = 0;

            int firstDectected = 0;
            Scanner scan = new Scanner(f);
            int lineno = 0;
            while (scan.hasNext()){
                String line = scan.nextLine();
                lineno++;
                if(line.contains("Result: false")){
                    isfaultDetected = 1;
                    detectedTimes++;
                    if(firstDectected==0)
                        firstDectected = lineno;
                    detectedAverageTime = detectedTimes + lineno;
                }
            }
            scan.close();
            System.out.println("File: "+f.getName());
            System.out.print("IsDetected: " + isfaultDetected);
            if(isfaultDetected!=0){
                System.out.print("FirstDectectedAt: " + firstDectected);
                System.out.print("DetectedTimes: " + detectedTimes);
                if(detectedTimes!=0)
                    System.out.print("DetectedAverageLine: " + detectedAverageTime / (double) detectedTimes);
            }
            System.out.println();
        }
    }
    public static void main(String[] args) throws FileNotFoundException {
        ResultAnalyzer analyzer = new ResultAnalyzer();
        analyzer.faultDectection("E:\\Workspace Intelij\\NBC_basic\\dataset2\\1-finish");
    }
}
