package org.firstinspires.ftc.teamcode.util;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.Robot;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

public class StateMachine <B extends Robot> {

    private State state = new State(this);
    public final B bot;
    private Command commandGroup = new InstantCommand();

    public StateMachine(B bot) {
        this.bot = bot;
    }

    public Command setState(State newState) {

        commandGroup = new SequentialCommandGroup(
                    state.finish(),
                    newState.initialize(state),
                    new InstantCommand(() -> {
                        state.setActive(false);
                        newState.setActive(true);
                        state = newState;
                    })
            );

        return commandGroup;
    }

    public void run() {
        state.execute();
    }

    public State getState() {
        return state;
    }

}
