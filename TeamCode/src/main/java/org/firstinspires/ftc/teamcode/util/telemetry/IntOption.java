package org.firstinspires.ftc.teamcode.util.telemetry;

import androidx.annotation.NonNull;

public class IntOption extends Option {

    protected int val = 0;

    public IntOption(int start){
        val = start;
    }

    @NonNull
    @Override
    public Object getVal() {
        return val;
    }

    @Override
    public void right() {
        val++;
    }

    @Override
    public void left() {
        val--;
    }
}
