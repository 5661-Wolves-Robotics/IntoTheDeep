package org.firstinspires.ftc.teamcode.bot.states;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.gamepad.GamepadEx;

import org.firstinspires.ftc.teamcode.bot.ITDBot;
import org.firstinspires.ftc.teamcode.bot.commands.ExtendTo;
import org.firstinspires.ftc.teamcode.bot.commands.PivotTo;
import org.firstinspires.ftc.teamcode.bot.commands.SetIntakePos;
import org.firstinspires.ftc.teamcode.bot.subsystems.Intake;
import org.firstinspires.ftc.teamcode.util.DualInputStateMachine;
import org.firstinspires.ftc.teamcode.util.State;
import org.firstinspires.ftc.teamcode.util.StateMachine;

import java.util.Objects;

public class DepositState extends BaseState{

    public DepositState(DualInputStateMachine<ITDBot, BaseState> machine) {
        super(machine);
    }

    @Override
    public Command initialize(State from) {
        super.initialize(from);

        switch(from.getName()){
            case "IntakeState":
                return new SequentialCommandGroup(
                        new ParallelCommandGroup(
                                new PivotTo(arm, 100),
                                new ExtendTo(arm, 0.3)
                        ),
                        new ParallelCommandGroup(
                                new SetIntakePos(intake, Intake.DEPOSITING),
                                new PivotTo(arm, 80),
                                new ExtendTo(arm, 0.95)
                        )
                );
            case "BaseState":
                return
                    new ParallelCommandGroup(
                            new SetIntakePos(intake, Intake.DEPOSITING),
                            new PivotTo(arm, 80),
                            new ExtendTo(arm, 0.95)
                    );
            default:
                return new InstantCommand();
        }
    }
}
