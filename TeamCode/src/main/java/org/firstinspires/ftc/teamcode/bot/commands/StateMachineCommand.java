package org.firstinspires.ftc.teamcode.bot.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.util.State;
import org.firstinspires.ftc.teamcode.util.StateMachine;

public class StateMachineCommand <T extends State> extends CommandBase{

    private final StateMachine<?, T> machine;
    private final T state;

    public StateMachineCommand(StateMachine<?, T> machine, T state) {
        this.machine = machine;
        this.state = state;
    }

    @Override
    public void initialize() {
        machine.setState(state).schedule();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
