package com.gac5206.covidawareness;

public class CovidItems {
    private String mstate, mpositive, mnegative, mdeaths;

    public CovidItems(String state, String positive, String negative, String deaths){
        mstate = state;
        mpositive = positive;
        mnegative = negative;
        mdeaths = deaths;
    }

    public String getMstate() {
        return mstate;
    }

    public void setMstate(String mstate) {
        this.mstate = mstate;
    }

    public String getMpositive() {
        return mpositive;
    }

    public void setMpositive(String mpositive) {
        this.mpositive = mpositive;
    }

    public String getMnegative() {
        return mnegative;
    }

    public void setMnegative(String mnegative) {
        this.mnegative = mnegative;
    }

    public String getMdeaths() {
        return mdeaths;
    }

    public void setMdeaths(String mdeaths) {
        this.mdeaths = mdeaths;
    }
}
