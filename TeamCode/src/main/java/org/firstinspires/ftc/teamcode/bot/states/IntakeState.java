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
import org.firstinspires.ftc.teamcode.bot.commands.SetIntakePos;
import org.firstinspires.ftc.teamcode.bot.commands.SinusoidalRaise;
import org.firstinspires.ftc.teamcode.bot.subsystems.Intake;
import org.firstinspires.ftc.teamcode.util.DualInputStateMachine;
import org.firstinspires.ftc.teamcode.util.State;
import org.firstinspires.ftc.teamcode.util.StateMachine;

public class IntakeState extends BaseState {

    public IntakeState(DualInputStateMachine<ITDBot, BaseState> machine) {
        super(machine);
    }

    @Override
    public Command initialize(State from) {
        super.initialize(from);

        switch(from.getName()) {
            case "DepositState":
                return new SequentialCommandGroup(
                        new ExtendTo(arm, 0.3),
                        new PivotTo(arm, 90),
                        new ParallelCommandGroup(
                                new ExtendTo(arm, 0.1),
                                new PivotTo(arm, 180),
                                new SetIntakePos(intake, Intake.INTAKING)
                        )
                );
            case "BaseState":
                return new ParallelCommandGroup(
                        new ExtendTo(arm, 0.1),
                        new PivotTo(arm, 180),
                        new SetIntakePos(intake, Intake.INTAKING)
                );
            default:
                return new InstantCommand();
        }
    }
}
