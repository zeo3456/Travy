package com.example.travy.model;

import java.util.HashMap;
import java.util.Set;

public class User {

    private static HashMap<String, String[]> ListOfUserName = new HashMap<String, String[]>();

    public static void addUser(String NewUserEmail, int id, String NewUserName, String PW) {
        String[] info = new String[3];
        info[0] = id + "";
        info[1] = NewUserName;
        info[2] = PW;
        ListOfUserName.put(NewUserEmail, info);
    }

    public static boolean CheckExist(String NewUserName) {
        return ListOfUserName.containsKey(NewUserName);
    }

    public static Set<String> getUser() {
        return ListOfUserName.keySet();
    }

    public static String getPW(String key) {
        String[] info = ListOfUserName.get(key);
        return info[2];
    }

    public static String getID(String key) {
        String[] info = ListOfUserName.get(key);
        return info[0];
    }

//    public static void PrintOutList(){
//        String toPrint= "";
//        Set<String> names = ListOfUserName.keySet();
////        Set<String[]> infos = (Set<String[]>) ListOfUserName.values();
//        String k = Arrays.toString(names.toArray());
////        String v = Arrays.toString(infos.toArray());
//        Log.i("A!", k + " ");
//    }
//    public static void LoadUsersIntoDB(File file){
//        try {
//            Scanner in2 = new Scanner(file);
//            while (in2.hasNext()) {
//                Log.i("in2Test",in2.next());
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//
//    }
}