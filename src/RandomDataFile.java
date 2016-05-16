import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class RandomDataFile {

	public static void randomFile(String filepath,int instance) throws FileNotFoundException{
		PrintWriter pw = new PrintWriter(new File(filepath));
		//int instance = 1000;
		int attribute = 9;//without label
		int[] attr_scale = {10,10,10,10,10,10,10,10,10,10};
		int label_scale = 5;
		String line = "";
		for(int k = 0; k < attribute+1;k++){
			if (k != attribute)
				line = line + k + ",";
			else 
				line = line + k;
		}
		pw.println(line);
		for(int i = 0; i < instance; i++){
			line = "";
			for(int j = 0; j < attribute+1; j++){
				if (j != attribute)
					line = line + (int)(Math.random()*attr_scale[j]) + ",";
				else 
					line = line + (int)(Math.random()*label_scale);
			}
			pw.println(line);
		}
		pw.close();
	} 
	public static void main(String[] args) throws FileNotFoundException{
		randomFile("E:\\MT\\dataset\\trainCSV_30.txt",30);
		randomFile("E:\\MT\\dataset\\testCSV_30.txt",1);
	}
	
}
