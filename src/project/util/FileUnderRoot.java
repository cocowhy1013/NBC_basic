package project.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author coco
 * @created 2015-3-11
 */
public class FileUnderRoot {
	ArrayList<String> filePathUnderRoot;

	public FileUnderRoot() {
		filePathUnderRoot = new ArrayList<String>();
	}

	public void displayArrayList() {
		for (String names : filePathUnderRoot) {
			System.out.println(names);
		}
	}

	public ArrayList<String> getArrayFiles() {
		return filePathUnderRoot;
	}

	public void getFileUnderRoot(String rootPath) {
		// get operator and mutants' path information for further analysis
		File root = new File(rootPath);
		if (!root.exists()) {
			FileUtil.createDir(rootPath);
		}
		File[] files = root.listFiles();
		for (File file : files) {
			// System.out.println(file.getName());
			// System.out.println("---"+file.getPath());
			if (file.isDirectory())
				getFileUnderRoot(file.getAbsolutePath());
			if (file.getName().endsWith(".class")) {
				// System.out.println("***********"+file.getName());
				filePathUnderRoot.add(file.getPath());
				// System.out.println(get_op(classpath));
			}
		}

	}
	public void getSourceFileUnderRoot(String rootPath) {
		// get operator and mutants' path information for further analysis
		File root = new File(rootPath);
		if (!root.exists()) {
			FileUtil.createDir(rootPath);
		}
		File[] files = root.listFiles();
		for (File file : files) {
			// System.out.println(file.getName());
			// System.out.println("---"+file.getPath());
			if (file.isDirectory())
				getSourceFileUnderRoot(file.getAbsolutePath());
			if (file.getName().endsWith(".java")&&(!file.getName().endsWith("2.java"))) {
				// System.out.println("***********"+file.getName());
				filePathUnderRoot.add(file.getPath());
				// System.out.println(get_op(classpath));
			}
			if(file.getName().endsWith("2.java")){
				file.delete();
			}
		}

	}
	public void getOriginSourceFileUnderRoot(String rootPath) {
		// get operator and mutants' path information for further analysis
		File root = new File(rootPath);
		if (!root.exists()) {
			FileUtil.createDir(rootPath);
		}
		File[] files = root.listFiles();
		for (File file : files) {
			// System.out.println(file.getName());
			// System.out.println("---"+file.getPath());
			if (file.isDirectory())
				getOriginSourceFileUnderRoot(file.getAbsolutePath());
			if (file.getName().endsWith(".java")&&(!file.getName().endsWith("2.java"))&&(!file.getPath().contains("instrument"))) {
				// System.out.println("***********"+file.getName());
				filePathUnderRoot.add(file.getPath());
				// System.out.println(get_op(classpath));
			}
			if(file.getName().endsWith("2.java")){
				file.delete();
			}
		}

	}
	public void getInstrumentClassFileUnderRoot(String rootPath) {
		// get operator and mutants' path information for further analysis
		File root = new File(rootPath);
		if (!root.exists()) {
			FileUtil.createDir(rootPath);
		}
		File[] files = root.listFiles();
		for (File file : files) {
			// System.out.println(file.getName());
			// System.out.println("---"+file.getPath());
			if (file.isDirectory())
				getInstrumentClassFileUnderRoot(file.getAbsolutePath());
			if (file.getName().endsWith(".class")&&(!file.getName().endsWith("2.class"))&&(file.getPath().contains("instrument"))) {
				// System.out.println("***********"+file.getName());
				filePathUnderRoot.add(file.getPath());
				// System.out.println(get_op(classpath));
			}
			if(file.getName().endsWith("2.class")){
				file.delete();
			}
		}

	}
	public static String getFileNameFromPath(String path){
		File file = new File(path);
		return file.getName();
	}
	public static String addFolderInstrumentToPath(String path) throws IOException{
		
		String filename = getFileNameFromPath(path);
		//if(!path.contains("instrument")){
		File file = new File(path.replace(filename, "instrument"));
		if(!file.exists())
			file.mkdir();
		else
			file.delete();
		return path.replace(filename, "instrument\\"+filename);
		
	}
	public static void deleteFolderInstrument(String path) throws IOException{
		
		String filename = getFileNameFromPath(path);
		if(!path.contains("instrument")){
			File file = new File(path.replace(filename, "instrument"));
			if(!file.exists())
				file.mkdir();
			else
				file.delete();
		}
	}
	public static void main(String[] args) throws IOException {
		FileUnderRoot fR = new FileUnderRoot();
		fR.getInstrumentClassFileUnderRoot("F:/mutation/result/isLeapYear");
		fR.displayArrayList();
		ArrayList<String> f = fR.getArrayFiles();
		for (String names : f) {
			
			
				System.out.println(names);
				//System.out.println(addFolderInstrumentToPath(names));
			
		}
	}
}
