package org.firstinspires.ftc.teamcode.util;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.Robot;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.Subsystem;

import org.firstinspires.ftc.teamcode.bot.commands.StateMachineCommand;

import java.util.Arrays;

public class StateMachine <B extends Robot, T extends State> {

    private T state = (T)new State(this);
    public final B bot;

    public StateMachine(B bot) {
        this.bot = bot;
    }

    public Command setState(T newState) {
        return new SequentialCommandGroup(
                    state.finish(),
                    newState.initialize(state),
                    new InstantCommand(() -> {
                        state.setActive(false);
                        newState.setActive(true);
                        state = newState;
                    })
            );
    }

    public StateMachineCommand<T> set(T newState) {
        return new StateMachineCommand<>(this, newState);
    }

    public void run() {
        state.execute();
    }

    public String getState() {
        return state.getName();
    }

}
