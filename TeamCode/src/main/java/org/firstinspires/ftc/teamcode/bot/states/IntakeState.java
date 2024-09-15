package org.firstinspires.ftc.teamcode.bot.states;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.gamepad.GamepadEx;

import org.firstinspires.ftc.teamcode.bot.ITDBot;
import org.firstinspires.ftc.teamcode.bot.commands.ExtendTo;
import org.firstinspires.ftc.teamcode.bot.commands.PivotTo;
import org.firstinspires.ftc.teamcode.bot.commands.SinusoidalRaise;
import org.firstinspires.ftc.teamcode.util.State;
import org.firstinspires.ftc.teamcode.util.StateMachine;

public class IntakeState extends BaseState {

    public IntakeState(StateMachine<ITDBot, BaseState> machine, String name) {
        super(machine, name);
    }

    @Override
    public Command initialize(State from) {
        super.initialize(from);

        switch(from.getName()) {
            case "DepositState":
                return new SequentialCommandGroup(
                        new ExtendTo(arm, 0.3),
                        new PivotTo(arm, 20),
                        new ParallelCommandGroup(
                                new ExtendTo(arm, 0.5),
                                new PivotTo(arm, 5)
                        )
                );
            case "BaseState":
                return new ParallelCommandGroup(
                        new SinusoidalRaise(arm, 1000, 20, 5),
                        new ExtendTo(arm, 0.5)
                );
            default:
                return new InstantCommand();
        }
    }
}
