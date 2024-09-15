package org.firstinspires.ftc.teamcode.util;

import androidx.annotation.NonNull;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;

public class State {

    protected StateMachine<?, ?> machine;
    private final String stateName;

    public State(StateMachine<?, ?> machine, String name) {
        this.machine = machine;
        stateName = name;
    }

    public Command initialize(State from){return new InstantCommand();}
    public void execute(){}
    public Command finish(){return new InstantCommand();}

    public String getName() {
        return stateName;
    }
}
