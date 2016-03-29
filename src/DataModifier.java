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
        	
        	/*if(isLabelExist(labels,instancelabel)==false && isChosedToModify(possibility)){
        		int modify_label = (int)(Math.random()*5);
    			while(isLabelExist(labels,modify_label)==true){
    				modify_label = (int)(Math.random()*5);
    			}
    			//System.out.println(modify_label+"!="+instancelabel);

    			target = target + modify_label;
        	}
        	else */
        	//{
        		target = target + (int)instancelabel;
        		//System.out.println(instancelabel);
        	//}
        	pw.println(target);
        }
        
        reader.close();
        pw.close();

        return path.replace(".txt", "_modify"+modify_time+"_"+k+".txt");
		
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
	public String modifyRandomStep(String trainFilePath, String testLine,double possibility, int modify_time,int k)
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
	public static void main(String[] args) throws IOException{
		double[][] attribute = {{3,2,4,1,4,5,0,4,3}};
		double[] label = {0};
		//1,2,3,4,5,6,7,0
		DataModifier modifier = new DataModifier();
		modifier.modifyRandom("train.txt",attribute, label, 0.1,1,1);
	}
}
