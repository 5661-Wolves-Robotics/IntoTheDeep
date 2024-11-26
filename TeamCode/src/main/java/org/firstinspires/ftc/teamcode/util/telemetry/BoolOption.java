package org.firstinspires.ftc.teamcode.util.telemetry;

import androidx.annotation.NonNull;

public class BoolOption extends Option{

    protected boolean val;

    public BoolOption(boolean startVal){
        val = startVal;
    }

    @Override
    public void right() {
        val = true;
    }

    @Override
    public void left() {
        val = false;
    }

    @NonNull
    @Override
    public Object getVal() {
        return val;
    }
}
