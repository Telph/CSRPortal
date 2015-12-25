/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service.dao;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to store and retreive the list of Engineers/Staffs corresponding to a complaint area
 * @author Janakiraman
 */
public class ServiceEngineersKb {
    public static List<String> getHardwareEngineers()
    {
        List<String> hwEngineers = new ArrayList<String>();
        hwEngineers.add("Roy");
        hwEngineers.add("Jame");
        hwEngineers.add("Don");
        return hwEngineers;
    }
    public static List<String> getSoftwareEngineers()
    {
        List<String> swEngineers = new ArrayList<String>();
        swEngineers.add("Greg");
        swEngineers.add("Chris");
        swEngineers.add("Jack");
        return swEngineers;
    }
    public static List<String> getWarrantyClaimStaffs()
    {
        List<String> warrantyClaimStaffs = new ArrayList<String>();
        warrantyClaimStaffs.add("Sarah");
        warrantyClaimStaffs.add("Christy");
        return warrantyClaimStaffs;
    }
    public static List<String> getNewProductEnquiryStaffs()
    {
        List<String> newEnquiryStaffs = new ArrayList<String>();
        newEnquiryStaffs.add("Merve");
        newEnquiryStaffs.add("Canyurt");
        return newEnquiryStaffs;
    }
}
