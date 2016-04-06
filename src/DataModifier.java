//package dm13;

import java.io.*;

public class DataModifier {

	public String modifyPlus(String path, double[][] attributes, double[] labels, double possibility, int modify_time) throws IOException{
		int plus = 1;
		BufferedReader reader = new BufferedReader(new FileReader(path));
        //String[] instanceBefore = reader.readLine().split(","); // attributes info
		PrintWriter pw = new PrintWriter(new File(path.replace(".txt", "_modify"+modify_time+".txt")));

		String line = reader.readLine();
		pw.println(line);
		//System.out.println(line);
		
        while((line = reader.readLine()) != null){
        	
        	String[] parts = line.split(",");
        	String target = "";
        	for(int i=0;i<parts.length-1;i++){
        		if(isAttributeExist(attributes,i,Double.parseDouble(parts[i]))==false 
        				&& isChosedToModify(possibility)){
        			int modify_attribute = (int)Double.parseDouble(parts[i]) + plus;
        			
        			//System.out.println(modify_attribute+"!="+Double.parseDouble(parts[i]));
        			target = target + modify_attribute +",";
        		}
        		else
        			target = target + (int)Double.parseDouble(parts[i]) +",";
        	}
        	int instancelabel = (int)Double.parseDouble(parts[parts.length-1]);
        	
        	if(isLabelExist(labels,instancelabel)==false && isChosedToModify(possibility)){
        		int modify_label =  (int)instancelabel + plus;
    			//System.out.println(modify_label+"!="+instancelabel);

    			target = target + modify_label;
        	}
        	else {
        		target = target + (int)instancelabel;
        		//System.out.println(instancelabel);
        	}
        	pw.println(target);
        }
        
        reader.close();
        pw.close();
        
        return path.replace(".txt", "_modify"+modify_time+".txt");
		
	}
	
	public boolean isLabelExist(double[] labels, double a){
		
		for(int i=0;i<labels.length;i++){
			if(a==labels[i])
				return true;
		}
		return false;
	}
	public boolean isAttributeExist(double[][] labels, int attribute_no, double a){
		
		for(int i=0;i<labels.length;i++){
			if(a==labels[i][attribute_no])
				return true;
		}
		return false;
	}
	public boolean isChosedToModify(double possibility){
		if((Math.random()<possibility))
			return true;
		else 
			return false;
	}

	public String modifyRandom(String path, double[][] attributes, double[] labels, double possibility, int modify_time, int k) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(path));
		//String[] instanceBefore = reader.readLine().split(","); // attributes info
		PrintWriter pw = new PrintWriter(new File(path.replace(".txt", "_modify"+modify_time+"_"+k+".txt")));

		String line = reader.readLine();
		pw.println(line);
//		System.out.println(line);

		while((line = reader.readLine()) != null){

			String[] parts = line.split(",");
			String target = "";
			for(int i=0;i<parts.length-1;i++){
				if(isAttributeExist(attributes,i,Double.parseDouble(parts[i]))==false
						&& isChosedToModify(possibility)){
					int modify_attribute = (int)(Math.random()*8);
					while(isAttributeExist(attributes,i,modify_attribute)==true){
						modify_attribute = (int)(Math.random()*8);
					}
					//System.out.println(modify_attribute+"!="+Double.parseDouble(parts[i]));
					target = target + modify_attribute +",";
				}
				else
					target = target + (int)Double.parseDouble(parts[i]) +",";
			}
			int instancelabel = (int)Double.parseDouble(parts[parts.length-1]);

			target = target + (int)instancelabel;

			pw.println(target);
		}

		reader.close();
		pw.close();

		return path.replace(".txt", "_modify"+modify_time+"_"+k+".txt");

	}
	public String modifyRandomStep(String trainFilePath, String testLine,double possibility, int modify_time,int k)
	//generate follow training files using MR1: complex
					throws IOException{
		String[] parts = testLine.split(",");
		double[][] attribute = new double[1][parts.length-1];
		
		for(int i=0;i<parts.length-1;i++)
			attribute[0][i] = Double.parseDouble(parts[i]);
		double[] label = new double[1];
		label[0] = Double.parseDouble(parts[parts.length-1]);

		DataModifier modifier = new DataModifier();
		return modifier.modifyRandom(trainFilePath,
				attribute, label, possibility,modify_time,k);
	}
	public String modifyTrainForFollowUp_1(String trainFilePath, String testLine,double possibility, int modify_time,int k)
		//Exacly the same as Function: modifyRandomStep in DataModifier.java
		throws IOException{
			String[] parts = testLine.split(",");
			double[][] attribute = new double[1][parts.length-1];

			for(int i=0;i<parts.length-1;i++)
				attribute[0][i] = Double.parseDouble(parts[i]);
			double[] label = new double[1];
			label[0] = Double.parseDouble(parts[parts.length-1]);

			DataModifier modifier = new DataModifier();
			return modifier.modifyRandom(trainFilePath,
					attribute, label, possibility,modify_time,k);
	}
	public String modifyTrainForFollowUp_2(String trainFilePath, String testLine,int operator, int modify_time,int k) throws IOException {
		//Parameter: operator: 1 plus; 2 minus; 3 multiple; 4 divide(not sure)
		BufferedReader reader = new BufferedReader(new FileReader(trainFilePath));
		//String[] instanceBefore = reader.readLine().split(","); // attributes info
		PrintWriter pw = new PrintWriter(new File(trainFilePath.replace(".txt", "_modify"+modify_time+"_"+k+".txt")));

		String line = reader.readLine();
		pw.println(line);
//		System.out.println(line);

		while((line = reader.readLine()) != null){
			String[] parts = line.split(",");
			String target = "";
			int instancelabel = (int)Double.parseDouble(parts[parts.length-1]);
			for(int i=0;i<parts.length-1;i++){
				if(operator==0) {
					target = target + ((int) Double.parseDouble(parts[i]) + 2) + ",";
					instancelabel = instancelabel +2;
				}
				else if (operator==1) {
					target = target + ((int) Double.parseDouble(parts[i]) - 2) + ",";
					instancelabel = instancelabel - 2;
				}
					else if(operator==2) {
					target = target + ((int) Double.parseDouble(parts[i]) * 2) + ",";
					instancelabel = instancelabel * 2;
				}
				else {
					target = target + ((int) Double.parseDouble(parts[i])) + ",";
					instancelabel = instancelabel;
				}
			}

			target = target + (int)instancelabel;
			pw.println(target);
		}

		reader.close();
		pw.close();

		return trainFilePath.replace(".txt", "_modify" + modify_time + "_" + k + ".txt");
	}
	public String modifyTrainForFollowUp_3(String trainFilePath, String testLine,int operator, int modify_time,int k) throws IOException {
		//Parameter: operator: 1 plus; 2 minus; 3 multiple; 4 divide(not sure)
		BufferedReader reader = new BufferedReader(new FileReader(trainFilePath));
		//String[] instanceBefore = reader.readLine().split(","); // attributes info
		PrintWriter pw = new PrintWriter(new File(trainFilePath.replace(".txt", "_modify"+modify_time+"_"+k+".txt")));

		String line = reader.readLine();
		pw.println(line);
//		System.out.println(line);

		while((line = reader.readLine()) != null){
			String[] parts = line.split(",");
			String target = "";
			int instancelabel = (int)Double.parseDouble(parts[parts.length-1]);
			for(int i=0;i<parts.length-1;i++){
				if(operator==0) {
					target = target + ((int) Double.parseDouble(parts[i]) + 2) + ",";
					instancelabel = instancelabel +2;
				}
				else if (operator==1) {
					target = target + ((int) Double.parseDouble(parts[i]) - 2) + ",";
					instancelabel = instancelabel - 2;
				}
				else if(operator==2) {
					target = target + ((int) Double.parseDouble(parts[i]) * 2) + ",";
					instancelabel = instancelabel * 2;
				}
				else {
					target = target + ((int) Double.parseDouble(parts[i])) + ",";
					instancelabel = instancelabel;
				}
			}

			target = target + (int)instancelabel;
			pw.println(target);
		}

		reader.close();
		pw.close();

		return trainFilePath.replace(".txt", "_modify"+modify_time+"_"+k+".txt");
	}

	public static void main(String[] args) throws IOException{
		double[][] attribute = {{3,2,4,1,4,5,0,4,3}};
		double[] label = {0};
		//1,2,3,4,5,6,7,0
		DataModifier modifier = new DataModifier();
		modifier.modifyTrainForFollowUp_2("MRtest.txt"," ",0,0,0);
	}
}
