package Project;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

import Logic.*;

public class Main {

    public static void main(String[] args) {

        List<String> javaList = new LinkedList<>();

        File initialDir = new File("Test\\InitialDir");
        File finalDir = new File("Test\\ResultDir");

        try {
            SearchJavaFile searchJavaFileInDirectory = new SearchJavaFile(initialDir);
            Thread thread = new Thread(searchJavaFileInDirectory);

            thread.start();
            thread.join();

            javaList = searchJavaFileInDirectory.call();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }


        try {
            var threads = new LinkedList<Thread>();

            for (var path : javaList) {
                File file = new File(path);

                Thread thread = new Thread(new ChangeJavaFile(path, finalDir.getPath() + "\\" + file.getName()));
                threads.add(thread);
                thread.start();
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }


    }
}
