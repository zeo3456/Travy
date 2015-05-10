package com.example.travy.model;

import java.util.ArrayList;
import java.util.List;

public class SiteSource {
    private ArrayList<Site> siteList;

    public List<Site> findAllSite() {
        siteList = new ArrayList<Site>();
        Site s1 = new Site("ChIJ4zGFAZpYwokRGUGph3Mf37k");
        Site s2 = new Site("ChIJb8Jg9pZYwokR-qHGtvSkLzs");
        Site s3 = new Site("ChIJKxDbe_lYwokRVf__s8CPn-o");
        siteList.add(s1);
        siteList.add(s2);
        siteList.add(s3);
        return siteList;
    }
}
