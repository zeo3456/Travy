package com.example.travy;
import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Utility {
    public static File GetFilePlace(String filename) {
        java.io.File root = new java.io.File(Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
                + "");
        File fileDir = new File(root.getAbsolutePath());
        if (!fileDir.isDirectory()) {
            fileDir.mkdir();
        }
        File file = new File(fileDir, filename);
        if (!file.isFile()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public static boolean FindExistUser(File file, String email) {
        Scanner in2 = null;
        try {
            in2 = new Scanner(file);
            while (in2.hasNext()) {
                if (in2.next().equals(email)) {
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
    public static boolean AddToFileWithoutCopy(File file, String toAdd) throws IOException {


        FileWriter filewriter = new FileWriter(file);
        BufferedWriter out = new BufferedWriter(filewriter);
        out.write(toAdd);
        out.close();


        return true;
    }

    public static boolean DeleteFromFile(File file, String UserID, String TripName) throws IOException {

//        String toPrint = "";
//        Scanner in = new Scanner(file);
//        while (in.hasNextLine()) {
//            String line = in.nextLine();
//            toPrint += (line + " & ");
//        }
        boolean goOn = true;
        String info = Utility.GetAllInfoFromFile(file);
        String info2 = info;
        String[] info2Split = info2.split(" ");
        if(info2Split.length == 2){
            goOn = false;
            String toDelete = UserID + " " + TripName;
            info = info.replace(toDelete, "");
//            Log.i("DDDD!", info);
        }
        if(goOn) {
//        info.replace(," ");
            String[] EachTrip = info.split(" & ");
            String[] FirstItem = EachTrip[0].split(" ");
//            Log.i("DDDD!", UserID + " " + TripName + " & ");TripName
//            Log.i("DDDD!", " & " + UserID + " " + TripName);
//            Log.i("DDDD!", info);
            if (FirstItem[0].equals(UserID) && FirstItem[1].equals(TripName)) {
                String toDelete = UserID + " " + TripName + " & ";
                info = info.replace(toDelete, "");
            } else {
                String toDelete = " & " + UserID + " " + TripName;
                info = info.replace(toDelete, "");
            }
//            Log.i("DDDD!", info);
//        for (int i = 0; i < EachTrip.length; i++) {
//            if (EachTrip[i].trim().isEmpty()) continue;
//            String[] EachTripDetail = EachTrip[i].split(" ");
//
//        }

//        toPrint += toAdd;
        }
        FileWriter filewriter = new FileWriter(file);
        BufferedWriter out = new BufferedWriter(filewriter);
        out.write(info.trim());
        out.close();

        return true;
    }
    public static boolean DeleteFromFile(File file, String UserID) throws IOException {

        boolean goOn = true;
        String info = Utility.GetAllInfoFromFile(file);
        String info2 = info;
        String[] info2Split = info2.split(" ");
        if(info2Split.length == 1){
            goOn = false;
            String toDelete = UserID;
            info = info.replace(toDelete, "");

        }
        if(goOn) {

            String[] EachTrip = info.split(" & ");
            String[] FirstItem = EachTrip[0].split(" ");

            if (FirstItem[0].equals(UserID)) {
                String toDelete = UserID + " & ";
                info = info.replace(toDelete, "");
            } else {
                String toDelete = " & " + UserID;
                info = info.replace(toDelete, "");
            }
//            Log.i("DDDD!", info);
//        for (int i = 0; i < EachTrip.length; i++) {
//            if (EachTrip[i].trim().isEmpty()) continue;
//            String[] EachTripDetail = EachTrip[i].split(" ");
//
//        }

//        toPrint += toAdd;
        }
        FileWriter filewriter = new FileWriter(file);
        BufferedWriter out = new BufferedWriter(filewriter);
        out.write(info.trim());
        out.close();

        return true;
    }

    public static String GetAllInfoFromFile(File file) throws IOException {
        Scanner in = null;
        try {
            in = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String tr = "";
        while (in.hasNext()) {
            tr += in.next() + " ";
        }

        return tr;
    }
}
