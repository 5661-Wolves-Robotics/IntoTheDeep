package org.firstinspires.ftc.teamcode.util.telemetry;

import androidx.annotation.NonNull;

public class EnumOption <T> extends Option {

    protected T[] vals;
    private int i = 0;

    public EnumOption(T[] vals) {
        this.vals = vals;
    }

    @NonNull
    @Override
    public T getVal() {
        return vals[i];
    }

    @Override
    public void right() {
        i++;
        if(i > vals.length - 1)
            i = 0;
    }

    @Override
    public void left() {
        i--;
        if(i < 0)
            i = vals.length - 1;
    }
}
