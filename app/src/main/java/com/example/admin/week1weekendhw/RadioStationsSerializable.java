package com.example.admin.week1weekendhw;

import java.io.Serializable;

/**
 * Created by Admin on 9/5/2017.
 */


public class RadioStationsSerializable implements Serializable {
    private int StationID;



    private String StationName;
    private String StationLink;


    public int getStationID() {
        return StationID;
    }

    public void setStationID(int stationID) {
        StationID = stationID;
    }

    public String getStationName() {
        return StationName;
    }

    public void setStationName(String stationName) {
        StationName = stationName;
    }

    public String getStationLink() {
        return StationLink;
    }



    public void setStationLink(String stationLink) {
        StationLink = stationLink;
    }



}
