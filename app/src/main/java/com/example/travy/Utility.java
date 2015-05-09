package com.example.travy;
import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Hao on 5/8/2015.
 */
public class Utility {
    public static File GetFilePlace(String filename)  {
        java.io.File root = new java.io.File(Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
                + "");
        File fileDir = new File(root.getAbsolutePath());
        if (!fileDir.isDirectory()) {
            fileDir.mkdir();
        }
        File file = new File(fileDir, filename);
        if(!file.isFile()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
    public static boolean FindExistUser(File file, String email){
        Scanner in2 = null;
        try {
            in2 = new Scanner(file);
            while (in2.hasNext()) {
                if(in2.next().equals(email)){
                    return true;
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean AddToFile(File file, String toAdd) throws IOException {

        String toPrint = "";
        Scanner in = new Scanner(file);
        while (in.hasNextLine()) {
            String line = in.nextLine();
            toPrint += (line + " & ");
        }
        toPrint += toAdd;

        FileWriter filewriter = new FileWriter(file);
        BufferedWriter out = new BufferedWriter(filewriter);
        out.write(toPrint);
        out.close();



        return true;
    }
    public static boolean DeleteFromFile(File file, String toDelete) throws IOException {

        String toPrint = "";
        Scanner in = new Scanner(file);

        while (in.hasNextLine()) {
            String line = in.nextLine();
            toPrint += (line + " & ");
        }
//        toPrint += toAdd;

        FileWriter filewriter = new FileWriter(file);
        BufferedWriter out = new BufferedWriter(filewriter);
        out.write(toPrint);
        out.close();



        return true;
    }
}
