package util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by Coco on 2016/3/28.
 */
public class CopyFileToMutationPath {
    public static void main(String[] args) throws IOException {
        String sourceJavaPath = "E:\\Workspace Intelij\\NBC_basic\\src\\";
        String sourceClassPath = "E:\\Workspace Intelij\\NBC_basic\\out\\production\\NBC_basic\\";

        String sourceJavaFile = "NaiveBayes_Modified.java";
        String sourceClassFile = "NaiveBayes_Modified.class";

        String mutationPath = "E:\\Mutation\\";
        FileUtils.copyFile(new File(sourceJavaPath+sourceJavaFile),
                new File(mutationPath+"src\\"+sourceJavaFile),false);
        FileUtils.copyFile(new File(sourceClassPath+sourceClassFile),
                new File(mutationPath+"classes\\"+sourceClassFile),false);//false: not to preserve date of files

        System.out.println("Sucessfully!");
    }
}
