package Logic;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class ChangeJavaFile implements Runnable {

    private String initialPath;
    private String finalPath;

    public ChangeJavaFile(String initialPath, String finalPath) throws FileNotFoundException {

        String regular = "\\.java$";
        Pattern pattern = Pattern.compile(regular);
        Matcher matcher;

        matcher = pattern.matcher(initialPath);
        if (matcher.find())
            this.initialPath = initialPath;
        else throw new FileNotFoundException();


        matcher = pattern.matcher(finalPath);
        if (matcher.find())
            this.finalPath = finalPath;
        else throw new FileNotFoundException();

    }


    private List<String> ReadFile(String path) throws IOException {

        var list = new LinkedList<String>();

        try (FileInputStream fis = new FileInputStream(path);
             InputStreamReader reader = new InputStreamReader(fis);
             BufferedReader read = new BufferedReader(reader)) {

            String line = read.readLine();

            while (line != null) {
                list.add(line);
                line = read.readLine();
            }

        } catch (IOException ex) {
            throw ex;
        }

        return list;
    }

    public void WriteFile(String path, List<String> list) throws IOException {
        try (FileWriter writer = new FileWriter(path, false)) {

            for (var str : list) {
                writer.write(str + "\n");
            }

            writer.flush();
        } catch (IOException ex) {
            throw ex;
        }
    }


    @Override
    public void run() {

        try {
            var data = ReadFile(initialPath);
            var resultData = new LinkedList<String>();

            String regular = "^(( )|\t)*//";
            Pattern pattern = Pattern.compile(regular);
            Matcher matcher;

            for (var str : data) {
                matcher = pattern.matcher(str);

                if (!matcher.find())
                    resultData.add(str);
            }


            String regular1 = "/\\*";
            Pattern pattern1 = Pattern.compile(regular1);
            Matcher matcher1;

            String regular2 = "\\*/";
            Pattern pattern2 = Pattern.compile(regular2);
            Matcher matcher2;

            boolean key = false;
            LinkedList<String> answer = new LinkedList<String>();
            for (var str : resultData) {
                matcher1 = pattern1.matcher(str);
                matcher2 = pattern2.matcher(str);

                if (matcher1.find())
                    key = true;

                if (!key)
                    answer.add(str);

                if (matcher2.find())
                    key = false;

            }

            WriteFile(finalPath, answer);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

}
