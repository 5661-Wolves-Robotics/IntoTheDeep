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
import org.firstinspires.ftc.teamcode.teleop.TeleOpMain;
import org.firstinspires.ftc.teamcode.util.DualInputStateMachine;
import org.firstinspires.ftc.teamcode.util.State;

public class IntakeState extends BaseState{

    public IntakeState(DualInputStateMachine<ITDBot, BaseState> machine) {
        super(machine);
    }

    @Override
    public Command initialize(State from) {

        TeleOpMain.DRIVER_TURN_SPEED = 0.4;
        TeleOpMain.DRIVER_MOVE_SPEED = 0.6;

        switch(from.getName()){
            case "BaseState":
                return new ExtendTo(arm, 0.65);
            case "HangState":
            case "SpecimenState":
            case "DepositState":
            case "LowDepositState":
                return new SequentialCommandGroup(
                        new SetIntakeDropdownPosition(intake, 0.3),
                        new WaitCommand(200),
                        new RetractSlideToZero(arm).withTimeout(2000),
                        new PivotTo(arm, 6).withTimeout(500),
                        new ExtendTo(arm, 0.65)
                );
            default:
                return new InstantCommand();
        }
    }
}
