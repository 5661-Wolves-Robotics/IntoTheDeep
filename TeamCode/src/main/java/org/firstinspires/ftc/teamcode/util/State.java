package org.firstinspires.ftc.teamcode.util;

import androidx.annotation.NonNull;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;

import java.util.function.BooleanSupplier;

public class State {

    protected StateMachine<?, ?> machine;
    private boolean active;

    public State(StateMachine<?, ?> machine) {
        this.machine = machine;
    }

    public Command initialize(State from){return new InstantCommand();}
    public void execute(){}
    public Command finish(){return new InstantCommand();}

    public String getName() {
        return this.getClass().getSimpleName();
    }

    public boolean isActive() {return active; }

    protected void setActive(boolean active) {this.active = active;}
}
