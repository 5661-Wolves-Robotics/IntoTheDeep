package org.firstinspires.ftc.teamcode.bot.states;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ScheduleCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.bot.ITDBot;
import org.firstinspires.ftc.teamcode.bot.commands.pivotslide.ExtendTo;
import org.firstinspires.ftc.teamcode.bot.commands.pivotslide.HangControl;
import org.firstinspires.ftc.teamcode.bot.commands.pivotslide.ManualSlideControl;
import org.firstinspires.ftc.teamcode.bot.commands.pivotslide.PivotTo;
import org.firstinspires.ftc.teamcode.bot.commands.pivotslide.RetractSlideToZero;
import org.firstinspires.ftc.teamcode.bot.commands.intake.SetIntakeDropdownPosition;
import org.firstinspires.ftc.teamcode.util.DualInputStateMachine;
import org.firstinspires.ftc.teamcode.util.State;

public class SpecimenState extends BaseState{


    double extension = 0.5;
    boolean canExtend = true;

    public SpecimenState(DualInputStateMachine<ITDBot, BaseState> machine) {
        super(machine);
    }

    @Override
    public Command initialize(State from) {

        extension = 0.5;
        canExtend = true;

        switch(from.getName()){
            case "BaseState":
            case "IntakeState":
                return new SequentialCommandGroup(
                        new ScheduleCommand(new SetIntakeDropdownPosition(intake, 0.6).perpetually()),
                        new PivotTo(arm, 50).withTimeout(600),
                        new ExtendTo(arm, extension).withTimeout(600)
                );
            case "HangState":
                return new SequentialCommandGroup(
                        new ScheduleCommand(new SetIntakeDropdownPosition(intake, 0.6).perpetually()),
                        new WaitCommand(200),
                        new ExtendTo(arm, extension).withTimeout(600),
                        new PivotTo(arm, 50).withTimeout(600)
                );
            case "DepositState":
            case "LowDepositState":
                return new SequentialCommandGroup(
                        new ScheduleCommand(new SetIntakeDropdownPosition(intake, 0.6).perpetually()),
                        new WaitCommand(200),
                        new RetractSlideToZero(arm),
                        new ExtendTo(arm, extension).withTimeout(600),
                        new PivotTo(arm, 50).withTimeout(600)
                );
            default:
                return new InstantCommand();
        }
    }

    @Override
    public void execute() {
        double pow = placer.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) - placer.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER);
        extension = Range.clip(extension + (pow / 10.0), 0, 0.65);
        if(canExtend) arm.setExtensionTarget(extension);
    }

    @Override
    public Command finish() {
        return new InstantCommand(() -> canExtend = false);
    }
}
