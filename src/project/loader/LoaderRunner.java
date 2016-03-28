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
        String test_root = "E:\\Mutation\\loader\\test_subject\\";
        String test_file = "E:\\Mutation\\loader\\test_file\\";
        //String subject_root = "F:/GD/actual_subject/";
        String oracle_root = "E:\\Mutation\\loader\\subject\\";
        RunMuJava run = new RunMuJava();
        String[] file_names = FileUtil.returnSonSimpleClassFileNamesExceptWHY(oracle_root);
        //{ "ArrayUtils.java","HeapSort.java"  };
        // String[] class_ops = {"IHI"};
        // String[] traditional_ops = {"AORB","AOIS"};
        final String test_filename = "Main_Modified";
        String name1 = "NaiveBayes_Modified";//method that is mutated using Mujava
        //final String test_helpfilename = "AWHY_testhelp";

        String oracle_path = "E:\\Mutation\\loader\\oracle_out.txt";
        String test_path = "E:\\Mutation\\loader\\test_out.txt";



        // get txt oracle for comparing later
        //FileUtil.setSystemOut(oracle_path);
        for (int i = 0; i < file_names.length; i++) {
            FileUtil.copyFile(oracle_root + file_names[i].replaceAll(".java", ".class"), test_root
                    + file_names[i].replaceAll(".java", ".class"));
            System.out.println(file_names[i]);
        }
        FileUtil.copyFile(test_file + test_filename + ".class", test_root
                + test_filename + ".class");

        //FileUtil.copyFile(test_file + test_helpfilename + ".class", test_root
        //		+ test_helpfilename + ".class");

        long t1 = new Date().getTime();
        //System.out.println("haha");
        TestLoader tL1 = new TestLoader();
        //tL.LoaderMutantClass(test_root,"Test");
        tL1.LoaderMutantClass2(test_root, test_filename);
        //FileUtil.backSystemOut();
        //FileUtil.deleteFileAndFolder(test_path);
        long t2 = new Date().getTime();
        long time_offset = t2 - t1;
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
                    .println("=============================================2");
            FileUtil.deleteFolderContent(test_root);
            for (int i = 0; i < file_names.length; i++) {
                if(file_names[i].compareTo(name1)!=0) {
                    FileUtil.copyFile(oracle_root + file_names[i].replaceAll(".java", ".class"), test_root
                            + file_names[i].replaceAll(".java", ".class"));
                }
            }
            FileUtil.copyFile(entry.getValue(),
                    test_root + name1+".class");

            FileUtil.copyFile(test_file + test_filename + ".class",
                    test_root + test_filename + ".class");
            System.out
                    .println("=============================================3");
            Thread.sleep(3000);

            //FileUtil.setSystemOut(test_path);

            // TestLoader tL2 = new TestLoader();
            // tL.LoaderMutantClass(test_root,"Test");
            // tL2.LoaderMutantClass2("F:/GD/test_subject/", test_filename);

            Thread service = new Thread() {
                @Override
                public void run() {
                    System.out.println("---run start---");
                    try {
                        TestLoader tL2 = new TestLoader();
                        // tL.LoaderMutantClass(test_root,"Test");
                        tL2.LoaderMutantClass2("E:\\Mutation\\loader\\test_subject\\",
                                test_filename);

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
                Thread.sleep(100);
                time = time - 100;
            }

            if (service.isAlive() == true) {

                service.stop();
                Thread.sleep(3000);
                //FileUtil.backSystemOut();
                continue;
            }
            //FileUtil.backSystemOut();
        }
        /*for (int i = 0; i < file_names.length; i++) {//change to i = 0 ;!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            System.out.println(file_names.length);
            FileUtil.deleteFolderContent(test_root);
            Thread.sleep(2000);
            //System.exit(0);
            //System.out.println(file_names.length);
            //System.out.println(i);


            GetMutantsClass m = new GetMutantsClass();
            //System.out.println("F:/GD/Presentation/"
            //		+ file_names[i].replaceAll(".class", ""));
            String name1 = file_names[i].replaceAll(".java", "");
            name1 = name1.replaceAll(".class", "");
            m.getMutantsClassSourceIn("F:/GD/Presentation/"
                    + name1 + "");
            Map<String, String> map = m.get_allpath();
            // gen.generator("F:/mutation/result/isLeapYear/traditional_mutants/int_isLeap(int)/AOIS_1/isLeapYear.class","F:/GD/test_subject/isLeapYear.class");
            // gen.generatorAllFiles(entry.getValue(),subject_root,test_root);
            // //!!!!!!!!!!!!!
            int number = 1;
            for (Map.Entry<String, String> entry : map.entrySet()) {

                String op_map_now = entry.getKey();

                number++;
                if (number > 234 || number < 233)// number == 24, dead code
                    // circle!
                    continue;
                System.out.println("Processing file:");
                System.out.println(entry.getValue()
                        + "=======================================");
                // if(number!=24)

				//GeneratorInstrument gen = new GeneratorInstrument();
				// gen.initInstrumentValue();
				//System.out.println(entry.getValue());
				//gen.generatorAllFiles(entry.getValue(), oracle_root, test_root);
                System.out
                        .println("=============================================2");
                FileUtil.copyFile(entry.getValue(),
                        test_root + "Otcas.class");

                FileUtil.copyFile(test_file + test_filename + ".class",
                        test_root + test_filename + ".class");
                //FileUtil.copyFile(test_file + test_helpfilename + ".class", test_root
                //		+ test_helpfilename + ".class");

                System.out
                        .println("=============================================3");
                Thread.sleep(3000);

                FileUtil.setSystemOut(test_path);

                // TestLoader tL2 = new TestLoader();
                // tL.LoaderMutantClass(test_root,"Test");
                // tL2.LoaderMutantClass2("F:/GD/test_subject/", test_filename);

                Thread service = new Thread() {
                    @Override
                    public void run() {
                        System.out.println("---run start---");
                        try {
                            TestLoader tL2 = new TestLoader();
                            // tL.LoaderMutantClass(test_root,"Test");
                            tL2.LoaderMutantClass2("E:\\Mutation\\loader\\test_subject\\",
                                    test_filename);

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
                    System.out.println("sleep 1000" + service.isAlive());
                    Thread.sleep(10);
                    time = time - 10;
                }

                if (service.isAlive() == true) {

                    service.stop();
                    Thread.sleep(3000);
                    FileUtil.backSystemOut();
                    continue;
                }
                FileUtil.backSystemOut();
                System.out.println("Sleep over!" + service.isAlive());
                System.out.println("Sleep");
                System.out.println("Successful!");

            }

        }*/
        //caculate_result.close();
        System.out.println("Success!!!");
    }
}