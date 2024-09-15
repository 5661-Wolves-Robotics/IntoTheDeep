package org.firstinspires.ftc.teamcode.bot.states;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.gamepad.GamepadEx;

import org.firstinspires.ftc.teamcode.bot.ITDBot;
import org.firstinspires.ftc.teamcode.bot.commands.ExtendTo;
import org.firstinspires.ftc.teamcode.bot.commands.PivotTo;
import org.firstinspires.ftc.teamcode.util.State;
import org.firstinspires.ftc.teamcode.util.StateMachine;

import java.util.Objects;

public class DepositState extends BaseState{

    public DepositState(StateMachine<ITDBot, BaseState> machine, String name) {
        super(machine, name);
    }

    @Override
    public Command initialize(State from) {
        super.initialize(from);

        switch(from.getName()){
            case "IntakeState":
                return new SequentialCommandGroup(
                        new ParallelCommandGroup(
                                new PivotTo(arm, 20),
                                new ExtendTo(arm, 0.3)
                        ),
                        new ParallelCommandGroup(
                                new PivotTo(arm, 80),
                                new ExtendTo(arm, 0.9)
                        )
                );
            case "BaseState":
                return
                    new ParallelCommandGroup(
                            new PivotTo(arm, 80),
                            new ExtendTo(arm, 0.9)
                    );
            default:
                return new InstantCommand();
        }
    }
}
