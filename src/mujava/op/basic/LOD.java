////////////////////////////////////////////////////////////////////////////
// Module : LOD.java
// Author : Ma, Yu-Seung
// COPYRIGHT 2005 by Yu-Seung Ma, ALL RIGHTS RESERVED.
////////////////////////////////////////////////////////////////////////////

package mujava.op.basic;

import openjava.mop.*;
import openjava.ptree.*;
import java.io.*;

/**
 * <p>
 * Generate LOD (Logical Operator Deletion) mutants -- delete each occurrence of
 * bitwise logical operators (bitwise and-&, bitwise or-|, exclusive or-^)
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005 by Yu-Seung Ma, ALL RIGHTS RESERVED
 * </p>
 * 
 * @author Yu-Seung Ma
 * @version 1.0
 */

public class LOD extends MethodLevelMutator {
	public LOD(FileEnvironment file_env, ClassDeclaration cdecl,
			CompilationUnit comp_unit) {
		super(file_env, comp_unit);
	}

	public void visit(UnaryExpression p) throws ParseTreeException {
		int op = p.getOperator();
		if (op == UnaryExpression.BIT_NOT) {
			outputToFile(p);
		}
	}

	/**
	 * Output LOD mutants to files
	 * 
	 * @param original
	 */
	public void outputToFile(UnaryExpression original) {
		if (comp_unit == null)
			return;

		String f_name;
		num++;
		f_name = getSourceName("LOD");
		String mutant_dir = getMuantID("LOD");

		try {
			PrintWriter out = getPrintWriter(f_name);
			LOD_Writer writer = new LOD_Writer(mutant_dir, out);
			writer.setMutant(original);
			writer.setMethodSignature(currentMethodSignature);
			comp_unit.accept(writer);
			out.flush();
			out.close();
		} catch (IOException e) {
			System.err.println("fails to create " + f_name);
		} catch (ParseTreeException e) {
			System.err.println("errors during printing " + f_name);
			e.printStackTrace();
		}
	}
}
