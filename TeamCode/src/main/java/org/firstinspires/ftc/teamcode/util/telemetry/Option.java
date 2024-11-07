package org.firstinspires.ftc.teamcode.util.telemetry;

import androidx.annotation.NonNull;

public abstract class Option implements IOption{

    public Option(){}

    @NonNull
    @Override
    public Object getVal() {
        return "NULL";
    }
}
