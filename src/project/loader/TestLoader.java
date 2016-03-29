package project.loader;

import project.util.FileUnderRoot;
import project.util.FileUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * @author coco
 * @created 2015-3-7
 */
public class TestLoader {

	public void LoaderMutantClass(String path, String[] classfile,
			String testfile) {
		// path: path of class file (include mutation and other file)
		// ------class files
		// test file: test file for project, method: test() to test project
		System.out.println("loader:"+path+"->"+testfile);
		SelfClassLoader loader1 = new SelfClassLoader("loader1");
		loader1.setPath(path);
		try {
			for (int i = 0; i < classfile.length; i++) {
				loader1.loadClass(classfile[i]);
			}
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Class Test = null;
		try {
			Test = loader1.loadClass(testfile); // load test class
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// Class Test = loader1.loadClass("Test");
		Object foo = null;
		try {
			foo = Test.newInstance(); // create test instance for invoke test()
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// C_after2.getMethod(name, parameterTypes)
		Method m1 = null;
		try {
			m1 = foo.getClass().getMethod("test", new Class[] {});// invoke
																	// test()
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			m1.invoke(foo, new Object[] {}); // invoke test()
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("loader successfully");
	}

	public void LoaderMutantClass2(String path, String testfile,int k,int time) {
		// path: path of class file (include mutation and other file)
		// ------class files
		// test file: test file for project, method: test() to test project


		//System.out.println("loader2:"+path+"->"+testfile);
		SelfClassLoader loader1 = new SelfClassLoader("loader1");
		loader1.setPath(path);
		FileUnderRoot fRoot = new FileUnderRoot();
		fRoot.getFileUnderRoot(path);
		ArrayList<String> list = fRoot.getArrayFiles();
		for (String names : list) {
			//System.out.println("name before:!!!" + names);
			names = FileUtil.returnFileName(names);
			//System.out.println("name:!!!" + names);
			if (!names.matches(testfile)) {
				try {
					//System.out.println("load+"+names);
					loader1.loadClass(names.replaceAll(".class", ""));
					//System.out.println("load over");
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		Class Test = null;
		try {
			Test = loader1.loadClass(testfile); // load test class
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// Class Test = loader1.loadClass("Test");
		Object foo = null;
		try {
			foo = Test.newInstance(); // create test instance for invoke test()
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// C_after2.getMethod(name, parameterTypes)
		Method m1 = null;
		try {
			//m1 = foo.getClass().getMethod("test", new Class[] {});// invoke
			m1 = foo.getClass().getMethod("test",int.class,int.class);// invoke
																	// test()
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			//m1.invoke(foo, new Object[] {}); // invoke test()
			m1.invoke(foo, k,time); // invoke test()
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("loader2 successfully");
	}
	public void LoaderMutantClassForEveryTesti(String path, String testfile,String test_i) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException {
		// path: path of class file (include mutation and other file)
		// ------class files
		// test file: test file for project, method: test() to test project
		System.out.println("loader2:"+path+"->"+testfile);
		SelfClassLoader loader1 = new SelfClassLoader("loader1");
		loader1.setPath(path);
		FileUnderRoot fRoot = new FileUnderRoot();
		fRoot.getFileUnderRoot(path);
		ArrayList<String> list = fRoot.getArrayFiles();
		for (String names : list) {
			System.out.println("name before:!!!" + names);
			names = FileUtil.returnFileName(names);
			System.out.println("name:!!!" + names);
			if (!names.matches(testfile)) {
				try {
					System.out.println("load+"+names);
					loader1.loadClass(names.replaceAll(".class", ""));
					System.out.println("load over");
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		Class Test = null;
		try {
			Test = loader1.loadClass(testfile); // load test class
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// Class Test = loader1.loadClass("Test");
		Object foo = null;
		try {
			foo = Test.newInstance(); // create test instance for invoke test()
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// C_after2.getMethod(name, parameterTypes)
		Method m1 = null;
		
			m1 = foo.getClass().getMethod(test_i, new Class[] {});// invoke
																	// test()
		
			m1.invoke(foo, new Object[] {}); // invoke test()
		
		System.out.println("loader2 successfully");
	}
	public void LoaderMutantClassForAllTesti(String path, String testfile,int test_i_sum) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		// path: path of class file (include mutation and other file)
		// ------class files
		// test file: test file for project, method: test() to test project
		System.out.println("loader2:"+path+"->"+testfile);
		SelfClassLoader loader1 = new SelfClassLoader("loader1");
		loader1.setPath(path);
		FileUnderRoot fRoot = new FileUnderRoot();
		fRoot.getFileUnderRoot(path);
		ArrayList<String> list = fRoot.getArrayFiles();
		for (String names : list) {
			System.out.println("name before:!!!" + names);
			names = FileUtil.returnFileName(names);
			System.out.println("name:!!!" + names);
			if (!names.matches(testfile)) {
				try {
					System.out.println("load+"+names);
					loader1.loadClass(names.replaceAll(".class", ""));
					System.out.println("load over");
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		Class Test = null;
		try {
			Test = loader1.loadClass(testfile); // load test class
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// Class Test = loader1.loadClass("Test");
		Object foo = null;
		try {
			foo = Test.newInstance(); // create test instance for invoke test()
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// C_after2.getMethod(name, parameterTypes)
		for(int i=0;i<test_i_sum;i++){
			Method m1 = null;
			System.out.println("test:"+String.valueOf(i));	
			m1 = foo.getClass().getMethod("test"+String.valueOf(i), new Class[] {});// invoke
						// test()
			m1.invoke(foo, new Object[] {}); // invoke test()
		
		}		
		System.out.println("loader2 successfully");
	}
	public static void main(String[] args) throws Exception {
		// PrintStream console= System.out;
		// PrintStream out = new PrintStream(new BufferedOutputStream(
		// new FileOutputStream("d://haha.txt")));
		// System.setOut(out);
		// System.setErr(out);
		 SelfClassLoader loader2 = new SelfClassLoader("loader1");
		 loader2.setPath("F:/GD/test_subject/");
		 Class clazz= loader2.loadClass("PairOfPrimeNumber");
		 Class Test = loader2.loadClass("Test");
		 
		// Class Test = loader1.loadClass("Test");
		 Object foo = Test.newInstance();
		// C_after2.getMethod(name, parameterTypes)
		 Method m1 = foo.getClass().getMethod("test", new Class[]{});
		 m1.invoke(foo, new Object[]{});
		// out.close();
		// System.setOut(console);
		// System.setErr(console);
		System.out.println("Success!=========================================");
		//TestLoader tL = new TestLoader();
		//tL.LoaderMutantClass(test_root,"Test");
		//tL.LoaderMutantClass2("D:/GD/test_subject/", "Test");
		
		//TestLoader tL1 = new TestLoader();
		// tL.LoaderMutantClass(test_root,"Test");
		/*tL1.LoaderMutantClass2("D:/mutation/result/PairOfPrimeNumber/traditional_mutants/boolean_isPrimeNumber(int)/AOIS_7", "PairOfPrimeNumber");/*
		 * MyClassLoader loader1 = new MyClassLoader("loader1");
		 * loader1.setPath("d://"); Class C_after2 =
		 * loader1.loadClass("C_after2"); Class haha =
		 * loader1.loadClass("haha"); Object foo = haha.newInstance(); //
		 * C_after2.getMethod(name, parameterTypes) Method m1 =
		 * foo.getClass().getMethod("n", new Class[]{}); m1.invoke(foo, new
		 * Object[]{});
		 */

		// ///////////////////////////////////////////////////////////////////
		/*
		 * MyClassLoader loader2 = new MyClassLoader("loader2");
		 * loader2.setPath("d://mutation//"); Class C_after3 =
		 * loader2.loadClass("C_after2"); Object foo3 = C_after3.newInstance();
		 * // C_after2.getMethod(name, parameterTypes) Method m3 =
		 * foo3.getClass().getMethod("m", new Class[]{}); m3.invoke(foo3, new
		 * Object[]{});
		 */
		// C_after2 a =(C_after2) C_after2.newInstance();
		// a.m();
		/*
		 * MyClassLoader l2 = new MyClassLoader("loader2",l1); //��L1��Ϊ���ĸ�������
		 * Class doccc = l2.loadClass("Dog"); doccc.newInstance();
		 */
	}
}