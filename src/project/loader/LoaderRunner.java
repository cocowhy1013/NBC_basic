package project.loader;

import project.mutantsMaker.GetMutantsClass;
import project.mutantsMaker.RunMuJava;
import project.util.FileUtil;

import java.util.Date;
import java.util.Map;

/**
 * Created by Coco on 2016/3/28.
 */
public class LoaderRunner {
    public static void main(String[] args) throws Exception {
        //String mutant_root = "F:/mutation/";
        String test_root = "E:\\Mutation\\loader2\\test_subject\\";
        String test_file = "E:\\Mutation\\loader2\\test_file\\";
        //String subject_root = "F:/GD/actual_subject/";
        String oracle_root = "E:\\Mutation\\loader2\\subject\\";
        RunMuJava run = new RunMuJava();
        String[] file_names = FileUtil.returnSonSimpleClassFileNamesExceptWHY(oracle_root);
        //{ "ArrayUtils.java","HeapSort.java"  };
        // String[] class_ops = {"IHI"};
        // String[] traditional_ops = {"AORB","AOIS"};
        final String test_filename = "Main_Modified2";
        String name1 = "NaiveBayes_Modified2";//method that is mutated using Mujava
        String name2 = "NaiveBayes_Modified2$Elements";
        //final String test_helpfilename = "AWHY_testhelp";

        final int MR_times = 500;

        // get txt oracle for comparing later
        //FileUtil.setSystemOut(oracle_path);
        for (int i = 0; i < file_names.length; i++) {
            FileUtil.copyFile(oracle_root + file_names[i].replaceAll(".java", ".class"), test_root
                    + file_names[i].replaceAll(".java", ".class"));
            //System.out.println(file_names[i]);
        }
        FileUtil.copyFile(test_file + test_filename + ".class", test_root
                + test_filename + ".class");

        //FileUtil.copyFile(test_file + test_helpfilename + ".class", test_root
        //		+ test_helpfilename + ".class");

        long t1 = new Date().getTime();
        //System.out.println("haha");
        TestLoader tL1 = new TestLoader();
        //tL.LoaderMutantClass(test_root,"Test");
        //int[] param = {0};
        tL1.LoaderMutantClass2(test_root, test_filename,0,MR_times);
        //FileUtil.backSystemOut();
        //FileUtil.deleteFileAndFolder(test_path);
        long t2 = new Date().getTime();
        long time_offset = t2 - t1;
        time_offset =438838;
        System.out.println("Time offset: " + (t2 - t1));
        System.out.println(file_names.length + file_names[0]);


        GetMutantsClass m = new GetMutantsClass();
        //System.out.println("F:/GD/Presentation/"
        //		+ file_names[i].replaceAll(".class", ""));

        m.getMutantsClass("E:\\Mutation\\result\\"
                + name1 + "");
        Map<String, String> map = m.get_allpath();
        int number = 1;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println("Processing sum:"+map.size()+" file:");
            String op_map_now = entry.getKey();
            number++;
            System.out.println(entry.getValue());
            System.out
                    .println("number:"+number);
            System.out
                    .println("=============================================2");
            if(number<9)
                continue;
            if(number==2||number==26||number==51||number==52)
                continue;
            //if(number<=52)
            //    continue;
           /* if(number<=9)
                continue;
            if(number==14||number==16||number==17||number==19||number==23)
                continue;
            if(number==29||number==30||number==31||number==32||number==34)
                continue;*/
            FileUtil.deleteFolderContent(test_root);
            for (int i = 0; i < file_names.length; i++) {
                if(file_names[i].compareTo(name1)!=0&&file_names[i].compareTo(name2)!=0) {
                    FileUtil.copyFile(oracle_root + file_names[i].replaceAll(".java", ".class"), test_root
                            + file_names[i].replaceAll(".java", ".class"));
                }
            }
            FileUtil.copyFile(entry.getValue(),
                    test_root + name1+".class");
            FileUtil.copyFile(entry.getValue().replace(name1,name2),
                    test_root + name2+".class");

            FileUtil.copyFile(test_file + test_filename + ".class",
                    test_root + test_filename + ".class");
            System.out
                    .println("=============================================3");
            Thread.sleep(3000);

            //FileUtil.setSystemOut(test_path);

            // TestLoader tL2 = new TestLoader();
            // tL.LoaderMutantClass(test_root,"Test");
            // tL2.LoaderMutantClass2("F:/GD/test_subject/", test_filename);

            final int finalNumber = number;
            Thread service = new Thread() {
                @Override
                public void run() {
                    System.out.println("---run start---");
                    try {
                        TestLoader tL2 = new TestLoader();
                        // tL.LoaderMutantClass(test_root,"Test");
                        tL2.LoaderMutantClass2("E:\\Mutation\\loader2\\test_subject\\",
                                test_filename, finalNumber,MR_times);

                        System.out.println("---run end---");
                    } catch (Exception e) {
                        System.out.println("---run interrupted---");

                        // e.printStackTrace();
                        // FileUtil.backSystemOut();
                    }
                    System.out.println("---run end---");

                }
            };
            service.start();
            long time = time_offset * 10;
            while (service.isAlive() && time > 0) {
                //System.out.println("sleep 1000" + service.isAlive());
                Thread.sleep(1000);
                time = time - 1000;
            }

            if (service.isAlive() == true) {

                service.stop();
                Thread.sleep(3000);
                //FileUtil.backSystemOut();
                continue;
            }
            //FileUtil.backSystemOut();
        }

        //caculate_result.close();
        System.out.println("Success!!!");
    }
}