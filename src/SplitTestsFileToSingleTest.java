import org.apache.commons.io.FileUtils;

import javax.swing.event.ListDataEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by coco on 2016/5/9.
 */
public class SplitTestsFileToSingleTest {
    public static boolean makeDirs(String folder1) {
        String folderName = folder1;
        if (folderName == null || folderName.isEmpty()) {
            return false;
        }

        File folder = new File(folderName);
        return (folder.exists() && folder.isDirectory()) ? true : folder.mkdirs();
    }
    public static void splitTestsFileToSingleTest(String testfile,int times) throws IOException {
        trainTXTtoCSV(testfile.replace("test.txt","train.txt"));
        File file = new File(testfile);
        List<String> lines = FileUtils.readLines(file);
        String logTitle = "0,1,2,3,4,5,6,7,8,9";//lines.get(0);
        String path = file.getPath();
        for(int i=1;i<lines.size();i++){
            makeDirs(path.replace(".txt","\\"+i));
            File output = new File(path.replace(".txt","\\"+i+"\\"+i+".csv"));
            FileUtils.writeStringToFile(output,"",false);//logOutputFile.
            FileUtils.writeStringToFile(output,logTitle+"\n",true);//logOutputFile.
            FileUtils.writeStringToFile(output,lines.get(i)+"\n",true);//logOutputFile.
            for(int j=0;j<times;j++) {
                DataModifier.modifyFileWithSingleTest(testfile.replace("test.txt", "train.csv"),
                        output.getPath(), 1, j, 1);
            }
        }
        System.out.println("Finish "+testfile);

    }
    public static void trainTXTtoCSV(String txtfile) throws IOException {
        File file = new File(txtfile);
        List<String> lines = FileUtils.readLines(file);
        File filecsv = new File(file.getPath().replace(".txt",".csv"));

        FileUtils.writeStringToFile(filecsv,"",false);//logOutputFile.
        FileUtils.writeStringToFile(filecsv,"0,1,2,3,4,5,6,7,8,9"+"\n",true);//logOutputFile.
        for(int i=1;i<lines.size();i++){
            FileUtils.writeStringToFile(filecsv,lines.get(i)+"\n",true);//logOutputFile.
        }
//        FileUtils.copyFile(file,filecsv);
    }
    public static void convertCSVtoARFF(String root,File batFile) throws IOException {
        System.out.println(root);
        File fileroot = new File(root);
        for(File file : fileroot.listFiles()) {
            if (file.isFile()) {
                //if(file.getName().contains("log"))
                //    file.delete();
                if (file.getName().endsWith(".csv")) {
                    FileUtils.writeStringToFile(batFile, "java -Djava.ext.dirs=E:\\MT\\wekaRunner\\lib weka.core.converters.CSVLoader "
                            + file.getPath() + " > " + file.getPath().replace(".csv", ".arff") + "\n", true);//logOutputFile.
                    //System.out.println(file.getPath());
                }
            } else if (file.isDirectory()) {
                convertCSVtoARFF(root + "\\" + file.getName(),batFile);
            }
        }
    }
    public static void convertARFFtoPredict(String root,File batFile) throws IOException {
        System.out.println(root);
        File fileroot = new File(root);
        for(File file : fileroot.listFiles()) {
            if (file.isFile()) {
                //if(file.getName().contains("log"))
                //    file.delete();
                String modify_path = file.getPath();
                String[] parts = modify_path.split("\\\\");
                String testfile_path = file.getParent()+"\\"+parts[parts.length-1].charAt(0)+".arff";

                if (file.getName().endsWith(".arff")&&file.getName().contains("modify")) {
                    FileUtils.writeStringToFile(batFile, "java -Djava.ext.dirs=E:\\MT\\wekaRunner\\lib " +
                            "weka.classifiers.bayes.NaiveBayes -t " + file.getPath() +
                            " -i -k -d " + file.getPath().replace(".arff", ".model") + " -c last\n", true);//logOutputFile.
                    FileUtils.writeStringToFile(batFile, "java -Djava.ext.dirs=E:\\MT\\wekaRunner\\lib " +
                            "weka.classifiers.bayes.NaiveBayes -l " + file.getPath().replace(".arff", ".model") +
                            " -T " + testfile_path + " -c last > "
                            +file.getPath().replace(".arff", "result.txt")+"\n", true);
                    //System.out.println(file.getPath());
                }
            } else if (file.isDirectory()) {
                convertARFFtoPredict(root + "\\" + file.getName(),batFile);
            }
        }
    }
    public static void changeNumericAttribute(String root) throws IOException {
        System.out.println(root);
        File fileroot = new File(root);
        for (File file : fileroot.listFiles()) {
            if (file.isFile()) {
                //if(file.getName().contains("log"))
                //    file.delete();
                String modify_path = file.getPath();
                String[] parts = modify_path.split("\\\\");
                String testfile_path = file.getParent() + "\\" + parts[parts.length - 1].charAt(0) + ".arff";

                if (file.getName().endsWith(".arff") ) {
                    List<String> lines = FileUtils.readLines(file);
                    //File filecsv = new File(file.getPath().replace(".txt", ".csv"));

                    FileUtils.writeStringToFile(file, "", false);//logOutputFile.
                    for (int i = 0; i < lines.size(); i++) {
                        String modifyLine = lines.get(i).replace("numeric", "{0,1,2,3,4,5,6,7,8,9}");
                        FileUtils.writeStringToFile(file, modifyLine + "\n", true);//logOutputFile.

                    }
                }
            }
            else if (file.isDirectory()) {
                changeNumericAttribute(root + "\\" + file.getName());
            }

        }
    }

    public static void batGen_CSVtoARFF(String root) throws IOException {
        File batFile = new File(root+"\\logForARFF.txt");
        FileUtils.writeStringToFile(batFile,"",false);

        convertCSVtoARFF(root,batFile);
    }
    public static void batGen_ARFFtoPredict(String root) throws IOException {
        File batFile = new File(root+"\\runForResult.txt");
        FileUtils.writeStringToFile(batFile,"",false);
        convertARFFtoPredict(root, batFile);
    }
    public static void main(String [] args) throws IOException {
            String root = "F:\\MTfinish\\12";
           // batGen_ARFFtoPredict(root);
        //batGen_CSVtoARFF(root);
        changeNumericAttribute(root);
        root = "F:\\MTfinish\\13";
        // batGen_ARFFtoPredict(root);
        //batGen_CSVtoARFF(root);
        changeNumericAttribute(root);
        root = "F:\\MTfinish\\14";
        // batGen_ARFFtoPredict(root);
        //batGen_CSVtoARFF(root);
        changeNumericAttribute(root);
        root = "F:\\MTfinish\\15";
        // batGen_ARFFtoPredict(root);
        //batGen_CSVtoARFF(root);
        changeNumericAttribute(root);
        root = "F:\\MTfinish\\16";
        // batGen_ARFFtoPredict(root);
        //batGen_CSVtoARFF(root);
        changeNumericAttribute(root);
        root = "F:\\MTfinish\\17";
        // batGen_ARFFtoPredict(root);
        //batGen_CSVtoARFF(root);
        changeNumericAttribute(root);
        root = "F:\\MTfinish\\18";
        // batGen_ARFFtoPredict(root);
        //batGen_CSVtoARFF(root);
        changeNumericAttribute(root);
        root = "F:\\MTfinish\\19";
        // batGen_ARFFtoPredict(root);
        //batGen_CSVtoARFF(root);
    changeNumericAttribute(root);
       /* splitTestsFileToSingleTest("E:\\MT\\dataset\\dataset_NBC\\0\\0test.txt",500);
        splitTestsFileToSingleTest("E:\\MT\\dataset\\dataset_NBC\\1\\1test.txt",500);
        splitTestsFileToSingleTest("E:\\MT\\dataset\\dataset_NBC\\2\\2test.txt",500);
        splitTestsFileToSingleTest("E:\\MT\\dataset\\dataset_NBC\\3\\3test.txt",500);
        splitTestsFileToSingleTest("E:\\MT\\dataset\\dataset_NBC\\4\\4test.txt",500);
        splitTestsFileToSingleTest("E:\\MT\\dataset\\dataset_NBC\\10\\10test.txt",500);
        splitTestsFileToSingleTest("E:\\MT\\dataset\\dataset_NBC\\11\\11test.txt",500);
        splitTestsFileToSingleTest("E:\\MT\\dataset\\dataset_NBC\\12\\12test.txt",500);
        splitTestsFileToSingleTest("E:\\MT\\dataset\\dataset_NBC\\13\\13test.txt",500);
        splitTestsFileToSingleTest("E:\\MT\\dataset\\dataset_NBC\\14\\14test.txt",500);
        splitTestsFileToSingleTest("E:\\MT\\dataset\\dataset_NBC\\15\\15test.txt",500);
        splitTestsFileToSingleTest("E:\\MT\\dataset\\dataset_NBC\\16\\16test.txt",500);
        splitTestsFileToSingleTest("E:\\MT\\dataset\\dataset_NBC\\17\\17test.txt",500);
        splitTestsFileToSingleTest("E:\\MT\\dataset\\dataset_NBC\\18\\18test.txt",500);
        splitTestsFileToSingleTest("E:\\MT\\dataset\\dataset_NBC\\19\\19test.txt",500);
        splitTestsFileToSingleTest("E:\\MT\\dataset\\dataset_NBC\\20\\20test.txt",500);
        splitTestsFileToSingleTest("E:\\MT\\dataset\\dataset_NBC\\50\\50test.txt",500);*/
}

}
