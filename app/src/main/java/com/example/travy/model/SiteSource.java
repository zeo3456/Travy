package com.example.travy.model;

import java.util.ArrayList;
import java.util.List;

public class SiteSource {

    private static ArrayList<Site> siteList = new ArrayList<Site>();

    public static int getSize() {
        return siteList.size();
    }

    public static void addSite(Site e) {
        siteList.add(e);
    }

    public static void clearSite() {
        siteList = new ArrayList<Site>();
    }

    public static ArrayList<Site> returnList() {
        return siteList;
    }

    public static String getSiteName(int pos) {
        return siteList.get(pos).getPlaceId();
    }

    public static List<Site> findAllSite() {
        return siteList;
    }

    public static void removeSite(int pos) {
        siteList.remove(pos);
    }

}