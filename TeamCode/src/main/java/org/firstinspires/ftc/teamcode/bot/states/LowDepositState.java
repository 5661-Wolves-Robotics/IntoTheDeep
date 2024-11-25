package org.firstinspires.ftc.teamcode.bot.states;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ScheduleCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.bot.ITDBot;
import org.firstinspires.ftc.teamcode.bot.commands.pivotslide.ExtendTo;
import org.firstinspires.ftc.teamcode.bot.commands.pivotslide.PivotTo;
import org.firstinspires.ftc.teamcode.bot.commands.pivotslide.RetractSlideToZero;
import org.firstinspires.ftc.teamcode.bot.commands.intake.SetIntakeDropdownPosition;
import org.firstinspires.ftc.teamcode.util.DualInputStateMachine;
import org.firstinspires.ftc.teamcode.util.State;

public class LowDepositState extends BaseState{

    public LowDepositState(DualInputStateMachine<ITDBot, BaseState> machine) {
        super(machine);
    }

    @Override
    public Command initialize(State from) {

        switch(from.getName()){
            case "BaseState":
            case "IntakeState":
                return new SequentialCommandGroup(
                        new ScheduleCommand(new SetIntakeDropdownPosition(intake, 0.8).perpetually()),
                        new RetractSlideToZero(arm).withTimeout(3000),
                        new ScheduleCommand(new SetIntakeDropdownPosition(intake, 0.5).perpetually()),
                        new PivotTo(arm, 99).withTimeout(700),
                        new ExtendTo(arm, 0.4).withTimeout(2000),
                        new ScheduleCommand(new SetIntakeDropdownPosition(intake, 0.9).perpetually())
                );
            case "DepositState":
                return new SequentialCommandGroup(
                        new ScheduleCommand(new SetIntakeDropdownPosition(intake, 0.5).perpetually()),
                        new WaitCommand(100),
                        new ExtendTo(arm, 0.4),
                        new ScheduleCommand(new SetIntakeDropdownPosition(intake, 0.9).perpetually())
                );
            case "SpecimenState":
            case "HangState":
                return new SequentialCommandGroup(
                        new ScheduleCommand(new SetIntakeDropdownPosition(intake, 0.5).perpetually()),
                        new WaitCommand(100),
                        new ExtendTo(arm, 0.4),
                        new PivotTo(arm, 99),
                        new ScheduleCommand(new SetIntakeDropdownPosition(intake, 0.9).perpetually())
                );
            default:
                return new InstantCommand();
        }
    }


}
