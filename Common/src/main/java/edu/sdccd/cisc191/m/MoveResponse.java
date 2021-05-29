package edu.sdccd.cisc191.m;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MoveResponse {
    MoveRequest request;
    boolean legal;
    String turn;



    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String toJSON(MoveResponse move) throws Exception {
        return objectMapper.writeValueAsString(move);
    }

    public static MoveResponse fromJSON(String input) throws Exception {
        return objectMapper.readValue(input, MoveResponse.class);

    }
    public static String promotionRequest(MoveResponse promote) throws Exception{
        return objectMapper.writeValueAsString(promote);
    }

    public MoveResponse(MoveRequest request, boolean legal, String turn) {
        this.request = request;
        this.legal = legal;
        this.turn = turn;
    }

    public MoveResponse(MoveRequest request){
        this.request = request;
    }
    public MoveResponse() {

    }

    public String toString() {
        if (legal) {
            return ("This Move from " + request.getSrow() + ","+ request.getScol()+ " to " + request.getErow()+","+request.getEcol() +" is legal");
        } else {
            return ("This Move from " + request.getSrow() + ","+ request.getScol()+ " to " + request.getErow()+","+request.getEcol() +" is not legal");
        }

    }




    public boolean isLegal() {
        return legal;
    }

    public void setLegal(boolean legal) {
        this.legal = legal;
    }

    public MoveRequest getRequest() {
        return request;
    }

    public void setRequest(MoveRequest request) {
        this.request = request;
    }

    public String getTurn() {
        return turn;
    }

    public void setTurn(String turn) {
        this.turn = turn;
    }

}