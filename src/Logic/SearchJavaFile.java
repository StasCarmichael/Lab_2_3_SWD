package Logic;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class SearchJavaFile implements Runnable, Callable<List<String>> {

    private List<String> javaFile;
    private File dir;

    public void listFilesForFolder(File folder, List<String> allFile) {
        for (File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory())
                listFilesForFolder(fileEntry, allFile);
            else
                allFile.add(fileEntry.getPath());
        }
    }

    public SearchJavaFile(File dir) throws FileNotFoundException {
        if (dir.isDirectory())
            this.dir = dir;
        else
            throw new FileNotFoundException();
    }

    @Override
    public List<String> call() {
        return javaFile;
    }

    @Override
    public void run() {

        try {
            var list = new LinkedList<String>();
            listFilesForFolder(dir, list);

            var javaFilePath = new LinkedList<String>();

            String regular = "\\.java$";
            Pattern pattern = Pattern.compile(regular);
            Matcher matcher;

            for (var path : list) {
                matcher = pattern.matcher(path);

                if (matcher.find())
                    javaFilePath.add(path);
            }

            javaFile = javaFilePath;

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

}

