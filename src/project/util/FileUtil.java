package project.util;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

/**
 * @author coco
 * @created 2015-1-16
 */
public class FileUtil {

	static PrintStream console;// save System.out
	static PrintStream out;// save out file (PrintStream) now

	public static void deleteFile(String filepath) {
		File file = new File(filepath);
		if (file.exists() && file.isFile())
			file.delete();
		else
			System.out.println("Fail to delete " + filepath + "!");
	}

	public static void deleteFolderContent(String filepath) {
		File root = new File(filepath);
		if (root.exists()) {
			File[] files = root.listFiles();
			for (File file : files) {
				deleteFileAndFolder(file);
			}
			System.out.println("Clear " + filepath + " successfully!");
		} else {
			createDir(filepath);
		}
	}

	public static void deleteFileAndFolder(String filepath) {
		File file = new File(filepath);
		if (file.exists()) {
			if (file.isFile()) {
				file.delete();
			} else if (file.isDirectory()) {
				File files[] = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					deleteFileAndFolder(files[i]);
				}
			}
			file.delete();
		} else {
			System.out.println("File does not exist.");
			createDir(file.getPath());
		}
	}

	public static void deleteFileAndFolder(File file) {
		if (file.exists()) {
			if (file.isFile()) {
				file.delete();
			} else if (file.isDirectory()) {
				File files[] = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					deleteFileAndFolder(files[i]);
				}
			}
			file.delete();
		} else {
			System.out.println("File does not exist.");
			createDir(file.getPath());
		}
	}

	public static boolean createFile(String destFileName) {
		File file = new File(destFileName);
		if (file.exists()) {
			System.out.println("Fail to create" + destFileName + "!");
			return false;
		}
		if (destFileName.endsWith(File.separator)) {
			System.out.println("Create" + destFileName + "!");
			return false;
		}
		if (!file.getParentFile().exists()) {
			System.out.println("Start to create...");
			if (!file.getParentFile().mkdirs()) {
				System.out.println("Fail to create folder.");
				return false;
			}
		}

		try {
			if (file.createNewFile()) {
				System.out.println("Succeed to create " + destFileName + "!");
				return true;
			} else {
				System.out.println("Fail to create single file: "
						+ destFileName + "!");
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Fail to create single file:" + destFileName
					+ "!");
			return false;
		}
	}

	public static boolean createDir(String destDirName) {
		File dir = new File(destDirName);
		if (dir.exists()) {
			System.out.println("Fail to create" + destDirName);
			return false;
		}
		if (!destDirName.endsWith(File.separator))
			destDirName = destDirName + File.separator;
		// create single folder
		if (dir.mkdirs()) {
			System.out.println("Succeed to create" + destDirName);
			return true;
		} else {
			System.out.println("Succeed to create" + destDirName);
			return false;
		}
	}

	public static String returnFileName(String filePath) {
		String splitName[] = filePath.split("/|\\\\");
		return splitName[splitName.length - 1];

	}

	public static void copyFile(String sourcepath, String targetpath) {
		// copy (move) source path file to target path file
		try {

			int bytesum = 0;
			int byteread = 0;
			File sourcefile = new File(sourcepath);
			File targetfile = new File(targetpath);
			if (targetfile.exists())
				deleteFile(targetpath);

			if (sourcefile.exists()) {
				InputStream inStream = new FileInputStream(sourcepath);
				FileOutputStream fs = new FileOutputStream(targetpath);
				byte[] buffer = new byte[1444];
				// int length;
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread;
					//System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}

		} catch (Exception e) {
			System.out.println("[Exception]: in copy file from " + sourcepath
					+ "to " + targetpath);
			e.printStackTrace();
		}

	}

	public static String findFile(String rootpath, String filename) {
		// find file under certain root, return file's
		// path---------------------wrong
		File root = new File(rootpath);
		File[] files = root.listFiles();
		for (File file : files) {
			System.out.println(file.getName());
			System.out.println("---" + file.getPath());
			if (file.isDirectory()) {
				if (findFile(file.getAbsolutePath(), filename) != null)
					return findFile(file.getAbsolutePath(), filename);
			} else {
				System.out.println("!!!" + filename + " " + file.getName());
				if (filename.equals(file.getName())) {
					System.out.println("-----!!!" + file.getAbsolutePath());
					return file.getAbsolutePath();
				}
			}
		}
		return null;
	}

	public static PrintStream setSystemOut(String outpath)
			throws FileNotFoundException {
		console = System.out;
		out = new PrintStream(new BufferedOutputStream(new FileOutputStream(
				outpath)));
		System.setOut(out);
		System.setErr(out);
		return out;
	}

	public static void backSystemOut() {
		out.close();
		System.setOut(console);
	}
	public static String[] returnSonSimpleFileNames(String rootPath){
		File root = new File(rootPath);
		File[] files = root.listFiles();
		String[] resultNames = new String[files.length];
		System.out.println(resultNames.length);
		int num=0;
		for(File file:files)
	    {  
			System.out.println(file.getName());
			if(file.isFile()){
				resultNames[num]=file.getName();
				num++;
			}
	    }
		String[] resultNames2 = new String[num];
		for(int j=0;j<num;j++){
			resultNames2[j]=resultNames[j];
		}
		System.out.println(resultNames.length);
		System.out.println(resultNames2.length);
		return resultNames2;
		
	}

	public static String[] returnSonSimpleTxtFilePath(String rootPath){
		File root = new File(rootPath);
		File[] files = root.listFiles();
		String[] resultNames = new String[files.length];
		System.out.println(resultNames.length);
		int num=0;
		for(File file:files)
	    {  
			System.out.println(file.getName());
			if(file.isFile()&&file.getName().endsWith(".txt")){
				resultNames[num]=file.getPath();
				num++;
			}
	    }
		String[] resultNames2 = new String[num];
		for(int j=0;j<num;j++){
			resultNames2[j]=resultNames[j];
		}
		System.out.println(resultNames.length);
		System.out.println(resultNames2.length);
		return resultNames2;
		
	}
	public static String[] returnSonSimpleTxtFileName(String rootPath){
		File root = new File(rootPath);
		File[] files = root.listFiles();
		String[] resultNames = new String[files.length];
		System.out.println(resultNames.length);
		int num=0;
		for(File file:files)
	    {  
			System.out.println(file.getName());
			if(file.isFile()&&file.getName().endsWith(".txt")){
				resultNames[num]=file.getName();
				num++;
			}
	    }
		String[] resultNames2 = new String[num];
		for(int j=0;j<num;j++){
			resultNames2[j]=resultNames[j];
		}
		System.out.println(resultNames.length);
		System.out.println(resultNames2.length);
		return resultNames2;
		
	}
	public static String[] returnSonSimpleClassFileNamesExceptWHY(String rootPath){
		File root = new File(rootPath);
		File[] files = root.listFiles();
		String[] resultNames = new String[files.length];
		System.out.println(resultNames.length);
		int num=0;
		for(File file:files)
	    {  
			//System.out.println(file.getName());
			if(file.isFile()&&file.getName().endsWith(".class")&&(!file.getName().contains("WHY"))){
				resultNames[num]=file.getName();
				num++;
			}
	    }
		String[] resultNames2 = new String[num];
		for(int j=0;j<num;j++){
			resultNames2[j]=resultNames[j];
		}
		System.out.println(resultNames.length);
		System.out.println(resultNames2.length);
		return resultNames2;
		
	}
	public static String[] returnSonSimpleClassFileNamesIncludeWHY(String rootPath){
		File root = new File(rootPath);
		File[] files = root.listFiles();
		String[] resultNames = new String[files.length];
		System.out.println(resultNames.length);
		int num=0;
		for(File file:files)
	    {  
			//System.out.println(file.getName());
			if(file.isFile()&&file.getName().endsWith(".class")&&(file.getName().contains("WHY"))){
				resultNames[num]=file.getName();
				num++;
			}
	    }
		String[] resultNames2 = new String[num];
		for(int j=0;j<num;j++){
			resultNames2[j]=resultNames[j];
		}
		System.out.println(resultNames.length);
		System.out.println(resultNames2.length);
		return resultNames2;
		
	}

	public static String[] returnSonSimpleJavaFileNamesExceptWHY(String rootPath){
		File root = new File(rootPath);
		File[] files = root.listFiles();
		String[] resultNames = new String[files.length];
		System.out.println(resultNames.length);
		int num=0;
		for(File file:files)
	    {  
			//System.out.println(file.getName());
			if(file.isFile()&&file.getName().endsWith(".java")&&(!file.getName().contains("WHY"))){
				resultNames[num]=file.getName();
				num++;
			}
	    }
		String[] resultNames2 = new String[num];
		for(int j=0;j<num;j++){
			resultNames2[j]=resultNames[j];
		}
		System.out.println(resultNames.length);
		System.out.println(resultNames2.length);
		return resultNames2;
		
	}
	public static String[] returnSonSimpleJavaFileNamesIncludeWHY(String rootPath){
		File root = new File(rootPath);
		File[] files = root.listFiles();
		String[] resultNames = new String[files.length];
		System.out.println(resultNames.length);
		int num=0;
		for(File file:files)
	    {  
			//System.out.println(file.getName());
			if(file.isFile()&&file.getName().endsWith(".java")&&(file.getName().contains("WHY"))){
				resultNames[num]=file.getName();
				num++;
			}
	    }
		String[] resultNames2 = new String[num];
		for(int j=0;j<num;j++){
			resultNames2[j]=resultNames[j];
		}
		System.out.println(resultNames.length);
		System.out.println(resultNames2.length);
		return resultNames2;
		
	}
	public static boolean existInArray(int [] array , int value){
		for(int i=0;i<array.length;i++)
			if(value == array[i])
				return true;
		return false;
	}
	public static void main(String[] args) throws FileNotFoundException {
		// findFile("D:/mutation/result/C_after/traditional_mutants/void_m()","C_after.java");
		String[] resultNames = returnSonSimpleJavaFileNamesExceptWHY("D:/mutation/src");
		System.out.println("WHY LENGTH:"+resultNames.length);
		for(int i=0;i<resultNames.length;i++)
			System.out.println("WHY-"+resultNames[i]);
		//deleteFolderContent("F:/GD/test_subject");// copyFile("E:/1.txt","E:/2.txt");
		/*
		 * setSystemOut("E:/consoleha.txt"); deleteFile("E:/2.txt");
		 * System.out.println("Success!!"); backSystemOut();
		 */
	}
}
