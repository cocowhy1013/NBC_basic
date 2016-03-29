package mujava.cmd;

import java.awt.event.MouseEvent;
import java.io.*;

import mujava.*;
import mujava.util.Debug;

public class MutantsGenerator {

	static public void generateMutants(String[] file_names) {
		generateMutants(file_names, MutationSystem.cm_operators,
				MutationSystem.tm_operators);
	}

	static public void generateClassMutants(String[] file_names) {
		generateMutants(file_names, MutationSystem.cm_operators, null);
	}

	static public void generateClassMutants(String[] file_names,
			String[] class_ops) {
		generateMutants(file_names, class_ops, null);
	}

	static public void generateTraditionalMutants(String[] file_names) {
		generateMutants(file_names, null, MutationSystem.tm_operators);
	}

	static public void generateTraditionalMutants(String[] file_names,
			String[] traditional_ops) {
		generateMutants(file_names, null, traditional_ops);
	}

	static public void generateMutants(String[] file_names, String[] class_ops,
			String[] traditional_ops) {
		// check if any files are selected, return an error message if no files
		// have been selected
		String[] file_list = file_names;
		if (file_list == null || file_list.length == 0) {
			System.err.println("[ERROR] No class is selected.");
			return;
		}

		// check if any operators are selected, return an error message if no
		// files have been selected
		if ((class_ops == null || class_ops.length == 0)
				&& (traditional_ops == null || traditional_ops.length == 0)) {
			System.out.println("[Error] no operators is selected. ");
			return;
		}

		for (int i = 0; i < file_list.length; i++) {
			// file_name = ABSTRACT_PATH - MutationSystem.SRC_PATH
			// For example: org/apache/bcel/Class.java
			String file_name = file_list[i];
			try {
				System.out.println(i + " : " + file_name);
				// [1] Examine if the target class is interface or abstract
				// class
				// In that case, we can't apply mutation testing.

				// Generate class name from file_name
				String temp = file_name.substring(0, file_name.length()
						- ".java".length());
				String class_name = "";
				System.out.println("temp:" + temp);
				for (int j = 0; j < temp.length(); j++) {
					if ((temp.charAt(j) == '\\') || (temp.charAt(j) == '/')) {
						class_name = class_name + ".";
					} else {
						class_name = class_name + temp.charAt(j);
					}
				}
				System.out.println("class:" + class_name);
				int class_type = MutationSystem.getClassType(class_name);
				System.out.println("class_type:" + class_type);
				if (class_type == MutationSystem.NORMAL) { // do nothing?
				} else if (class_type == MutationSystem.MAIN) {
					System.out.println(" -- " + file_name
							+ " class contains 'static void main()' method.");
					System.out
							.println("    Please note that mutants are not generated for the 'static void main()' method");
				}
				// Added on 1/19/2013, no mutants will be generated for a class
				// having only one main method
				else if (class_type == MutationSystem.MAIN_ONLY) {
					System.out
							.println("Class "
									+ file_name
									+ " has only the 'static void main()' method and no mutants will be generated.");
					break;
				} else {
					switch (class_type) {
					case MutationSystem.INTERFACE:
						System.out.println(" -- Can't apply because "
								+ file_name + " is 'interface' ");
						break;
					case MutationSystem.ABSTRACT:
						System.out.println(" -- Can't apply because "
								+ file_name + " is 'abstract' class ");
						break;
					case MutationSystem.APPLET:
						System.out.println(" -- Can't apply because "
								+ file_name + " is 'applet' class ");
						break;
					case MutationSystem.GUI:
						System.out.println(" -- Can't apply because "
								+ file_name + " is 'GUI' class ");
						break;
					}
					deleteDirectory();
					continue;
				}

				// [2] Apply mutation testing
				setMutationSystemPathFor(file_name);

				// File[] original_files = new File[1];
				// original_files[0] = new
				// File(MutationSystem.SRC_PATH,file_name);

				File original_file = new File(MutationSystem.SRC_PATH,
						file_name);

				/*
				 * AllMutantsGenerator genEngine; genEngine = new
				 * AllMutantsGenerator(original_file,class_ops,traditional_ops);
				 * genEngine.makeMutants(); genEngine.compileMutants();
				 */

				ClassMutantsGenerator cmGenEngine;

				// do not generate class mutants if no class mutation operator
				// is selected
				if (class_ops != null) {
					System.out.println("class_ops: " + class_ops[0]);
					cmGenEngine = new ClassMutantsGenerator(original_file,
							class_ops);
					cmGenEngine.makeMutants();
					cmGenEngine.compileMutants();
				}

				// do not generate traditional mutants if no class traditional
				// operator is selected
				if (traditional_ops != null) {
					TraditionalMutantsGenerator tmGenEngine;
					// System.out.println("original_file: " + original_file);
					System.out
							.println("traditional_ops: " + traditional_ops[0]);
					tmGenEngine = new TraditionalMutantsGenerator(
							original_file, traditional_ops);
					tmGenEngine.makeMutants();
					tmGenEngine.compileMutants();
				}

			} catch (OpenJavaException oje) {
				System.out.println("[OJException] " + file_name + " "
						+ oje.toString());
				// System.out.println("Can't generate mutants for " +file_name +
				// " because OpenJava " + oje.getMessage());
				deleteDirectory();
			} catch (Exception exp) {
				System.out.println("[Exception] " + file_name + " "
						+ exp.toString());
				exp.printStackTrace();
				// System.out.println("Can't generate mutants for " +file_name +
				// " due to exception" + exp.getClass().getName());
				// exp.printStackTrace();
				deleteDirectory();
			} catch (Error er) {
				System.out
						.println("[Error] " + file_name + " " + er.toString());
				System.out.println("MutantsGenPanel: ");
				er.printStackTrace();

				// System.out.println("Can't generate mutants for " +file_name +
				// " due to error" + er.getClass().getName());
				deleteDirectory();
			}
		}

		System.out
				.println("------------------------------------------------------------------");
		System.out.println("All files are handled");
	}

	/*
	 * static public void generateMutants(String[] file_names, String[]
	 * class_ops, String[] traditional_ops){ System.out.println(file_names[0]+""
	 * +class_ops[0]+" "+traditional_ops[0]); // file_names = Relative path from
	 * MutationSystem.SRC_PATH String file_name; for(int
	 * i=0;i<file_names.length;i++){ file_name = file_names[i];
	 * System.out.println(file_name); try{ System.out.println(i+" : "
	 * +file_name); String temp =
	 * file_name.substring(0,file_name.length()-".java".length()); String
	 * class_name=""; for(int ii=0;ii<temp.length();ii++){ if(
	 * (temp.charAt(ii)=='\\') || (temp.charAt(ii)=='/') ){ class_name =
	 * class_name + "."; }else{ class_name = class_name + temp.charAt(ii); } }
	 * // Class c = Class.forName(class_name); System.out.println("HAHA"); int
	 * class_type = MutationSystem.getClassType(class_name);
	 * 
	 * if(class_type==MutationSystem.NORMAL){ ; }else
	 * if(class_type==MutationSystem.MAIN){ System.out.println(" -- " +
	 * file_name+ " class contains 'static void main()' method.");
	 * System.out.println(
	 * "    Pleas note that mutants are not generated for the 'static void main()' method"
	 * ); }else{ switch(class_type){ case MutationSystem.MAIN :
	 * System.out.println(" -- Can't apply because " + file_name+
	 * " contains only 'static void main()' method."); break; case
	 * MutationSystem.INTERFACE : System.out.println(" -- Can't apply because "
	 * + file_name+ " is 'interface' "); break; case MutationSystem.ABSTRACT :
	 * System.out.println(" -- Can't apply because " + file_name+
	 * " is 'abstract' class "); break; case MutationSystem.APPLET :
	 * System.out.println(" -- Can't apply because " + file_name+
	 * " is 'applet' class "); break; case MutationSystem.GUI :
	 * System.out.println(" -- Can't apply because " + file_name+
	 * " is 'GUI' class "); break; } deleteDirectory(); continue; }
	 * 
	 * // [2] Apply mutation testing
	 * 
	 * setMutationSystemPathFor(file_name);
	 * 
	 * //File[] original_files = new File[1]; //original_files[0] = new
	 * File(MutationSystem.SRC_PATH,file_name); File original_file = new
	 * File(MutationSystem.SRC_PATH,file_name);
	 * 
	 * ClassMutantsGenerator cmGenEngine; if(class_ops != null){
	 * System.out.println("class_ops: " + class_ops[0]); cmGenEngine = new
	 * ClassMutantsGenerator(original_file,class_ops);
	 * cmGenEngine.makeMutants(); cmGenEngine.compileMutants(); }
	 * 
	 * //do not generate traditional mutants if no class traditional operator is
	 * selected if(traditional_ops != null){ TraditionalMutantsGenerator
	 * tmGenEngine; //System.out.println("original_file: " + original_file);
	 * System.out.println("traditional_ops: " + traditional_ops[0]); tmGenEngine
	 * = new TraditionalMutantsGenerator(original_file,traditional_ops);
	 * tmGenEngine.makeMutants(); tmGenEngine.compileMutants(); }
	 * 
	 * AllMutantsGenerator genEngine; Debug.setDebugLevel(3); genEngine = new
	 * AllMutantsGenerator(original_file,class_ops,traditional_ops);
	 * genEngine.makeMutants(); genEngine.compileMutants();
	 * System.out.println("Make mutants."); }catch(OpenJavaException oje){
	 * System.out.println("[OJException] " + file_name + " " + oje.toString());
	 * //System.out.println("Can't generate mutants for " +file_name +
	 * " because OpenJava " + oje.getMessage()); deleteDirectory();
	 * }catch(Exception exp){ System.out.println("[Exception] " + file_name +
	 * " " + exp.toString()); exp.printStackTrace();
	 * //System.out.println("Can't generate mutants for " +file_name +
	 * " due to exception" + exp.getClass().getName()); //exp.printStackTrace();
	 * deleteDirectory(); }catch(Error er){ System.out.println("[Error] " +
	 * file_names + " " + er.toString());
	 * //System.out.println("Can't generate mutants for " +file_name +
	 * " due to error" + er.getClass().getName()); deleteDirectory(); } }
	 * System.out.println("Success!"); }
	 */
	static void setMutationSystemPathFor(String file_name) {
		try {
			String temp;
			temp = file_name
					.substring(0, file_name.length() - ".java".length());
			temp = temp.replace('/', '.');
			temp = temp.replace('\\', '.');
			int separator_index = temp.lastIndexOf(".");
			if (separator_index >= 0) {
				MutationSystem.CLASS_NAME = temp.substring(separator_index + 1,
						temp.length());
			} else {
				MutationSystem.CLASS_NAME = temp;
			}

			String mutant_dir_path = MutationSystem.MUTANT_HOME + "/" + temp;
			File mutant_path = new File(mutant_dir_path);
			mutant_path.mkdir();

			String class_mutant_dir_path = mutant_dir_path + "/"
					+ MutationSystem.CM_DIR_NAME;
			File class_mutant_path = new File(class_mutant_dir_path);
			class_mutant_path.mkdir();

			String traditional_mutant_dir_path = mutant_dir_path + "/"
					+ MutationSystem.TM_DIR_NAME;
			File traditional_mutant_path = new File(traditional_mutant_dir_path);
			traditional_mutant_path.mkdir();

			String original_dir_path = mutant_dir_path + "/"
					+ MutationSystem.ORIGINAL_DIR_NAME;
			File original_path = new File(original_dir_path);
			original_path.mkdir();

			MutationSystem.CLASS_MUTANT_PATH = class_mutant_dir_path;
			MutationSystem.TRADITIONAL_MUTANT_PATH = traditional_mutant_dir_path;
			MutationSystem.ORIGINAL_PATH = original_dir_path;
			MutationSystem.DIR_NAME = temp;

		} catch (Exception e) {
			System.err.println(e);
		}
	}

	static void deleteDirectory() {
		File originalDir = new File(MutationSystem.MUTANT_HOME + "/"
				+ MutationSystem.DIR_NAME + "/"
				+ MutationSystem.ORIGINAL_DIR_NAME);
		while (originalDir.delete()) {
		}

		File cmDir = new File(MutationSystem.MUTANT_HOME + "/"
				+ MutationSystem.DIR_NAME + "/" + MutationSystem.CM_DIR_NAME);
		while (cmDir.delete()) {
		}

		File tmDir = new File(MutationSystem.MUTANT_HOME + "/"
				+ MutationSystem.DIR_NAME + "/" + MutationSystem.TM_DIR_NAME);
		while (tmDir.delete()) {
		}

		File myHomeDir = new File(MutationSystem.MUTANT_HOME + "/"
				+ MutationSystem.DIR_NAME);
		while (myHomeDir.delete()) {
		}
	}

}