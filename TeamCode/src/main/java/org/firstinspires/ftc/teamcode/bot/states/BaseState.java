package org.firstinspires.ftc.teamcode.bot.states;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.ScheduleCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.bot.ITDBot;
import org.firstinspires.ftc.teamcode.bot.commands.pivotslide.ExtendTo;
import org.firstinspires.ftc.teamcode.bot.commands.pivotslide.HangSlide;
import org.firstinspires.ftc.teamcode.bot.commands.drive.LocalDrive;
import org.firstinspires.ftc.teamcode.bot.commands.pivotslide.ManualPivotSlideControl;
import org.firstinspires.ftc.teamcode.bot.commands.pivotslide.PivotTo;
import org.firstinspires.ftc.teamcode.bot.commands.intake.IntakeControl;
import org.firstinspires.ftc.teamcode.bot.commands.hanghooks.SetHookAngle;
import org.firstinspires.ftc.teamcode.bot.commands.intake.SetIntakeDropdownPosition;
import org.firstinspires.ftc.teamcode.bot.commands.intake.ToggleClaw;
import org.firstinspires.ftc.teamcode.bot.subsystems.HangingHooks;
import org.firstinspires.ftc.teamcode.bot.subsystems.Intake;
import org.firstinspires.ftc.teamcode.bot.subsystems.PivotSlide;
import org.firstinspires.ftc.teamcode.util.DualInputStateMachine;
import org.firstinspires.ftc.teamcode.util.State;

public class BaseState extends State {

    protected final Intake intake;
    protected final PivotSlide arm;
    protected final MecanumDrive drive;
    protected final HangingHooks hooks;
    protected final GamepadEx driver;
    protected final GamepadEx placer;

    public BaseState(DualInputStateMachine<ITDBot, BaseState> machine) {
        super(machine);
        ITDBot bot = machine.bot;
        intake = bot.intake;
        arm = bot.arm;
        drive = bot.drive;
        hooks = bot.hooks;
        driver = machine.driver1;
        placer = machine.driver2;
    }

    private LocalDrive localDrive;
    private IntakeControl powerIntake;
    private Command setIntakeDropdownPosition;
    private ToggleClaw clawToggle;
    private ManualPivotSlideControl manualPivotSlideControl;
    private SetHookAngle setHookAngle;
    private HangSlide hangSlide;

    private final double PLACER_TURN_SPEED = 0.5;

    private boolean canExtend = true;

    @Override
    public Command initialize(State from) {



        switch(from.getName()) {
            case "DepositState": //Coming from deposit state
                return new SequentialCommandGroup(
                        new InstantCommand(setIntakeDropdownPosition::cancel),
                        new ExtendTo(arm, 0.05),
                        new PivotTo(arm, 0).withTimeout(500),
                        new ExtendTo(arm, 0.05),
                        new InstantCommand(() -> canExtend = true),
                        new ScheduleCommand(setIntakeDropdownPosition)
                );
            case "BaseState":
                return new InstantCommand();
            default: //Default initialization
                localDrive = new LocalDrive(drive, driver::getLeftX, driver::getLeftY, () -> driver.getRightX() + ((placer.getButton(GamepadKeys.Button.DPAD_RIGHT) ? 1 : 0) - (placer.getButton(GamepadKeys.Button.DPAD_LEFT) ? 1 : 0)) * PLACER_TURN_SPEED);

                powerIntake = new IntakeControl(intake, () -> -placer.getLeftX());

                manualPivotSlideControl = new ManualPivotSlideControl(arm, ()->(
                        placer.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) - placer.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER)
                ), ()->(
                        -(placer.getButton(GamepadKeys.Button.DPAD_DOWN) ? 1 : 0) + (placer.getButton(GamepadKeys.Button.DPAD_UP) ? 1 : 0)
                ));

                setIntakeDropdownPosition = new SetIntakeDropdownPosition(intake, 0.7).perpetually();
                clawToggle = new ToggleClaw(intake);

                setHookAngle = new SetHookAngle(hooks, 200);

                hangSlide = new HangSlide(arm, ()->(
                        placer.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) - placer.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER)
                ), ()->(
                        -(placer.getButton(GamepadKeys.Button.DPAD_DOWN) ? 1 : 0) + (placer.getButton(GamepadKeys.Button.DPAD_UP) ? 1 : 0)
                ));

                return new SequentialCommandGroup(
                        new InstantCommand(() -> {
                            placer.getGamepadButton(GamepadKeys.Button.X)
                                            .toggleWhenPressed(setIntakeDropdownPosition);

                            placer.getGamepadButton(GamepadKeys.Button.A)
                                            .whenPressed(clawToggle);

                            placer.getGamepadButton(GamepadKeys.Button.Y)
                                            .toggleWhenPressed(setHookAngle);

                            driver.getGamepadButton(GamepadKeys.Button.X)
                                            .whenPressed(new ConditionalCommand(
                                                    new ExtendTo(arm, 0.7),
                                                    new InstantCommand(),
                                                    ()->canExtend
                                            ));

                            placer.getGamepadButton(GamepadKeys.Button.B)
                                    .whenPressed(new ConditionalCommand(
                                            new ParallelCommandGroup(
                                                    new ExtendTo(arm, 0.05),
                                                    new ScheduleCommand(setIntakeDropdownPosition)
                                            ),
                                            new InstantCommand(),
                                            ()->canExtend
                                    ));

                            driver.getGamepadButton(GamepadKeys.Button.Y)
                                            .toggleWhenPressed(hangSlide);

                            drive.setDefaultCommand(localDrive);
                            intake.setDefaultCommand(powerIntake);
                            arm.setDefaultCommand(manualPivotSlideControl);
                            hooks.setDefaultCommand(new SetHookAngle(hooks, 0.0));
                        }),
                        new ScheduleCommand(setIntakeDropdownPosition)
                );

        }
    }

    @Override
    public Command finish() {
        return new InstantCommand(() -> {
            canExtend = false;
        });
    }
}
