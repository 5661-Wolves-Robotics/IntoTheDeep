package org.firstinspires.ftc.teamcode.bot.states;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ScheduleCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.bot.ITDBot;
import org.firstinspires.ftc.teamcode.bot.commands.pivotslide.ExtendTo;
import org.firstinspires.ftc.teamcode.bot.commands.pivotslide.PivotTo;
import org.firstinspires.ftc.teamcode.bot.commands.pivotslide.RetractSlideToZero;
import org.firstinspires.ftc.teamcode.bot.commands.intake.SetIntakeDropdownPosition;
import org.firstinspires.ftc.teamcode.util.DualInputStateMachine;
import org.firstinspires.ftc.teamcode.util.State;

public class DepositState extends BaseState{

    public DepositState(DualInputStateMachine<ITDBot> machine) {
        super(machine);
    }

    @Override
    public Command initialize(State from) {

        switch(from.getName()){
            case "HangState":
            case "BaseState":
            case "IntakeState":
                return new SequentialCommandGroup(
                            new ScheduleCommand(new SetIntakeDropdownPosition(intake, 0.8).perpetually()),
                            new RetractSlideToZero(arm).withTimeout(3000),
                            new ScheduleCommand(new SetIntakeDropdownPosition(intake, 0.5).perpetually()),
                            new PivotTo(arm, 99).withTimeout(700),
                            new ExtendTo(arm, 1.1).withTimeout(2000),
                            new ScheduleCommand(new SetIntakeDropdownPosition(intake, 0.9).perpetually())
                    );
            case "LowDepositState":
                return new SequentialCommandGroup(
                        new ScheduleCommand(new SetIntakeDropdownPosition(intake, 0.5).perpetually()),
                        new ExtendTo(arm, 1.1),
                        new ScheduleCommand(new SetIntakeDropdownPosition(intake, 0.9).perpetually())
                );
            case "SpecimenDeposit":
                return new SequentialCommandGroup(
                        new PivotTo(arm, 99),
                        new ScheduleCommand(new SetIntakeDropdownPosition(intake, 0.5).perpetually()),
                        new ExtendTo(arm, 1.1),
                        new ScheduleCommand(new SetIntakeDropdownPosition(intake, 0.9).perpetually())
                );
            default:
                return new InstantCommand();
        }
    }


}
