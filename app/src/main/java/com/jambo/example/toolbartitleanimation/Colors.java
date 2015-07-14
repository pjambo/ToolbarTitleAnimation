package com.jambo.example.toolbartitleanimation;

/**
 * Created by JAMBO on 7/14/15.
 */
public class Colors {

    private int primaryDark;
    private int primaryLight;

    public Colors(int primaryLight, int primaryDark){
        this.primaryLight = primaryLight;
        this.primaryDark = primaryDark;
    }

    public void setPrimaryDark(int primaryDark) {
        this.primaryDark = primaryDark;
    }

    public void setPrimaryLight(int primaryLight) {
        this.primaryLight = primaryLight;
    }

    public int getPrimaryDark() {
        return primaryDark;
    }

    public int getPrimaryLight() {
        return primaryLight;
    }

}
