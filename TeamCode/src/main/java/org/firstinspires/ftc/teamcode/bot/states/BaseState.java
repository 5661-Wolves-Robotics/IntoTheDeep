package org.firstinspires.ftc.teamcode.bot.states;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.bot.ITDBot;
import org.firstinspires.ftc.teamcode.bot.commands.ExtendAndPivotTo;
import org.firstinspires.ftc.teamcode.bot.commands.ExtendTo;
import org.firstinspires.ftc.teamcode.bot.commands.IntakeDropdownToggle;
import org.firstinspires.ftc.teamcode.bot.commands.LocalDrive;
import org.firstinspires.ftc.teamcode.bot.commands.ManualPivotSlideControl;
import org.firstinspires.ftc.teamcode.bot.commands.PivotTo;
import org.firstinspires.ftc.teamcode.bot.commands.IntakeControl;
import org.firstinspires.ftc.teamcode.bot.commands.RetractSlideToZero;
import org.firstinspires.ftc.teamcode.bot.commands.SetIntakeDropdown;
import org.firstinspires.ftc.teamcode.bot.subsystems.Intake;
import org.firstinspires.ftc.teamcode.bot.subsystems.PivotSlide;
import org.firstinspires.ftc.teamcode.util.DualInputStateMachine;
import org.firstinspires.ftc.teamcode.util.State;

import java.util.function.BooleanSupplier;

public class BaseState extends State {

    protected final Intake intake;
    protected final PivotSlide arm;
    protected final MecanumDrive drive;
    protected final GamepadEx driver;
    protected final GamepadEx placer;

    protected LocalDrive localDrive;
    protected IntakeControl powerIntake;
    protected final ManualPivotSlideControl manualPivotSlideControl;

    public BaseState(DualInputStateMachine<ITDBot, BaseState> machine) {
        super(machine);
        ITDBot bot = machine.bot;
        intake = bot.intake;
        arm = bot.arm;
        drive = bot.drive;
        driver = machine.driver1;
        placer = machine.driver2;

        localDrive = new LocalDrive(drive, driver::getLeftX, driver::getLeftY, driver::getRightX);

        powerIntake = new IntakeControl(intake, ()->(
                0.5 + (driver.isDown(GamepadKeys.Button.DPAD_LEFT) ? -0.5 : 0) + (driver.isDown(GamepadKeys.Button.DPAD_RIGHT) ? 0.5 : 0)
        ), ()->(
                0.5 + (driver.isDown(GamepadKeys.Button.LEFT_BUMPER) ? 0.5 : 0) + (driver.isDown(GamepadKeys.Button.RIGHT_BUMPER) ? -0.5 : 0)
        ));

        manualPivotSlideControl = new ManualPivotSlideControl(arm, ()->(
                driver.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) - driver.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER)
        ), driver::getRightY);
    }

    @Override
    public Command initialize(State from) {

        switch(from.getName()) {
            case "DepositState": //Coming from deposit state
                return new SequentialCommandGroup(
                        new RetractSlideToZero(arm),
                        new PivotTo(arm, 0)
                );

            default: //Default initialization
                return new SequentialCommandGroup(
                        new SetIntakeDropdown(intake, false),
                        new InstantCommand(() -> {

                            driver.getGamepadButton(GamepadKeys.Button.X)
                                    .whenPressed(new IntakeDropdownToggle(intake));

                            drive.setDefaultCommand(localDrive);
                            intake.setDefaultCommand(powerIntake);
                            arm.setDefaultCommand(manualPivotSlideControl);
                        })
                );

        }
    }
}
