package org.firstinspires.ftc.teamcode.bot.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.util.State;
import org.firstinspires.ftc.teamcode.util.StateMachine;

public class StateMachineCommand extends CommandBase{

    private final StateMachine<?, ?> machine;
    private final State state;

    public StateMachineCommand(StateMachine<?, ?> machine, State state) {
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
