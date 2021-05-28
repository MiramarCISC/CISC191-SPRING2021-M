package edu.sdccd.cisc191.m;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;

public class MoveRequest {

    private int srow;
    private int scol;
    private int erow;
    private int ecol;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String toJSON(MoveRequest move) throws Exception {
        return objectMapper.writeValueAsString(move);
    }

    public static MoveRequest fromJSON(String input) throws Exception {
        return objectMapper.readValue(input, MoveRequest.class);
    }

    public MoveRequest(int srow, int scol, int erow, int ecol) {
        this.srow = srow;
        this.scol = scol;
        this.erow = erow;
        this.ecol = ecol;


    }

    public MoveRequest() {

    }

    public int getSrow() {
        return srow;
    }

    public void setSrow(int srow) {
        this.srow = srow;
    }

    public int getScol() {
        return scol;
    }

    public void setScol(int scol) {
        this.scol = scol;
    }

    public int getErow() {
        return erow;
    }

    public void setErow(int erow) {
        this.erow = erow;
    }

    public int getEcol() {
        return ecol;
    }

    public void setEcol(int ecol) {
        this.ecol = ecol;
    }

}