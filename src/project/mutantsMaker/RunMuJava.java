package project.mutantsMaker;
import mujava.MutationSystem;
import mujava.cmd.MutantsGenerator;
import project.loader.TestLoader;
import project.util.FileUtil;

import java.util.Map;

//import project.instrument.GeneratorInstrument;

/**
 * @author coco
 * @created 2015-1-9
 */
public class RunMuJava {
	GetMutantsClass m;

	public void runMujava(String[] file_names, String[] traditional_ops,
			String[] class_ops) {
		MutantsGenerator
				.generateMutants(file_names, class_ops, traditional_ops);
	}

	public void genMutantsStep() throws Exception {
		// prepare for mutation
		System.out.println("The main method starts");
		MutationSystem.setJMutationStructure();
		MutationSystem.recordInheritanceRelation();

		// set file for mutation
		String[] file_names = { "isLeapYear.java" };
		// String[] file_names = {"isLeapYear.java"};

		// set mutation operators
		String[] class_ops = { "IHI" };
		String[] traditional_ops = { "AORB", "AOIS" };
		// generate mutants
		MutantsGenerator
				.generateMutants(file_names, class_ops, traditional_ops);
		// Thread.sleep(10000);

		// get operators information we need for mutation files
		m = new GetMutantsClass();
		// m.getMutantsClass("F:/mutation/result/C_after");
		// m.getMutantsClass("F:/mutation/result/isLeapYear");
		m.getMutantsClass("F:/mutation/result/isLeapYear");
		// }
		// public void genInstrumentStep(){
		// Generator2 gen = new Generator2().generator(classpath, targetpath);
		m.display_oppath_map();

		// choose one mutants to exchange original files
		String classpath = m.get_path("AOIS_1");
		// String classpath =
		// "F:/mutation/result/isLeapYear/traditional_mutants/int_isLeap(int)/AOIS_3/isLeapYear.class";

		String targetpath = System.getProperty("user.dir") + "\\bin\\"
				+ m.get_classname() + ".class";
		// String targetpath = System.getProperty("user.dir") +
		// "\\bin\\C_after.class";
		// String targetpath = "E:/Workspace9/MuJava-master/bin/C_after.class";
		//GeneratorInstrument gen = new GeneratorInstrument();
		//gen.generator(classpath, targetpath);
		System.out.println(classpath + "---" + targetpath);
	}

	public Map<String, String> genMutantsStepWithParameter(String[] file_names,
			String[] class_ops, String[] traditional_ops) throws Exception {
		// prepare for mutation
		System.out.println("The main method starts");
		MutationSystem.setJMutationStructure();
		MutationSystem.recordInheritanceRelation();

		// set file for mutation
		// String[] file_names = {"isLeapYear.java"};
		// String[] file_names = {"isLeapYear.java"};

		// set mutation operators
		// String[] class_ops = {"IHI"};
		// String[] traditional_ops = {"AORB","AOIS"};
		// generate mutants
		for (int i = 0; i < file_names.length; i++) {
			String a = file_names[i].replaceAll(".java", "");
			a = a.replaceAll(".class", "");
			FileUtil.deleteFileAndFolder("F:/mutation/result/" + a);
		}
		MutantsGenerator
				.generateMutants(file_names, class_ops, traditional_ops);
		// Thread.sleep(10000);

		// get operators information we need for mutation files
		m = new GetMutantsClass();
		// m.getMutantsClass("F:/mutation/result/C_after");
		// m.getMutantsClass("F:/mutation/result/isLeapYear");
		for (int i = 0; i < file_names.length; i++) {
			String a = file_names[i].replaceAll(".java", "");
			a = a.replaceAll(".class", "");
			System.out.println("a:"+a);
			m.getMutantsClass("F:/mutation/result/" + a);
		}
		// }
		// public void genInstrumentStep(){
		// Generator2 gen = new Generator2().generator(classpath, targetpath);
		System.out
				.println("Map:==============================================");
		m.display_oppath_map();
		Map<String, String> allpath = m.get_allpath();
		// choose one mutants to exchange original files
		// String classpath = m.get_path("AOIS_1");
		return allpath;

	}

	public void testStep() {

	}

	public static void main(String[] args) throws Exception {
		// prepare for mutation
		/*
		 * System.out.println("The main method starts");
		 * MutationSystem.setJMutationStructure();
		 * MutationSystem.recordInheritanceRelation();
		 * 
		 * //set file for mutation String[] file_names = {"isLeapYear.java"};
		 * //String[] file_names = {"isLeapYear.java"};
		 * 
		 * //set mutation operators String[] class_ops = {"IHI"}; String[]
		 * traditional_ops = {"AORB","AOIS"}; //generate mutants
		 * MutantsGenerator.generateMutants(file_names, class_ops,
		 * traditional_ops); //Thread.sleep(10000);
		 */
		// get operators information we need for mutation files
		/*
		 * GetMutantsClass m = new GetMutantsClass();
		 * //m.getMutantsClass("F:/mutation/result/C_after");
		 * //m.getMutantsClass("F:/mutation/result/isLeapYear");
		 * m.getMutantsClass("F:/mutation/result/isLeapYear");
		 * 
		 * //Generator2 gen = new Generator2().generator(classpath, targetpath);
		 * m.display_oppath_map();
		 */
		// choose one mutants to exchange original files
		// String classpath = m.get_path("AOIS_1");
		/*
		 * String classpath =
		 * "F:/mutation/result/isLeapYear/traditional_mutants/int_isLeap(int)/AOIS_3/isLeapYear.class"
		 * ;
		 * 
		 * //String targetpath = System.getProperty("user.dir") +
		 * "\\bin\\"+m.get_classname()+".class"; //String targetpath =
		 * System.getProperty("user.dir") + "\\bin\\C_after.class"; String
		 * targetpath = "F:/GD/test_subject/isLeapYear.class";
		 * GeneratorInstrument gen = new GeneratorInstrument();
		 * gen.generator(classpath, targetpath);
		 * System.out.println(classpath+"---"+targetpath);
		 */
		TestLoader tL2 = new TestLoader();
		// tL.LoaderMutantClass(test_root,"Test");
		tL2.LoaderMutantClass2("F:/GD/test_subject/", "LeapTest2",0,0);
		// test without using hotswap, so Class is old version.
		// C_after c = new C_after();
		// c.m();
		// System.out.println(classpath+"---"+targetpath);
		// isLeapYear.isLeap(1800);

		// test after hotswap, so Class is latest version.
		/*
		 * try { // ÿ�ζ�������һ���µ�������� HotswapClass cl = new HotswapClass("bin", new
		 * String[]{"isLeapYear"}); Class cls = cl.loadClass("isLeapYear");
		 * Object foo = cls.newInstance(); Method m1 =
		 * foo.getClass().getMethod("isLeap", int.class); m1.invoke(foo, 398);
		 * // Method m1 = foo.getClass().getMethod("isLeap", new Class[]{}); //
		 * m1.invoke(foo, new Object[]{});
		 * 
		 * } catch(Exception ex) { ex.printStackTrace(); }
		 */
	}
}
