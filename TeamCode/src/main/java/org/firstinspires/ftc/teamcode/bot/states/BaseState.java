package org.firstinspires.ftc.teamcode.bot.states;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.ScheduleCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.bot.ITDBot;
import org.firstinspires.ftc.teamcode.bot.commands.pivotslide.ExtendTo;
import org.firstinspires.ftc.teamcode.bot.commands.pivotslide.MPPivotTo;
import org.firstinspires.ftc.teamcode.bot.commands.pivotslide.ManualSlideControl;
import org.firstinspires.ftc.teamcode.bot.commands.drive.LocalDrive;
import org.firstinspires.ftc.teamcode.bot.commands.pivotslide.PivotTo;
import org.firstinspires.ftc.teamcode.bot.commands.intake.IntakeControl;
import org.firstinspires.ftc.teamcode.bot.commands.hanghooks.SetHookAngle;
import org.firstinspires.ftc.teamcode.bot.commands.intake.SetIntakeDropdownPosition;
import org.firstinspires.ftc.teamcode.bot.commands.intake.ToggleClaw;
import org.firstinspires.ftc.teamcode.bot.commands.pivotslide.RetractSlideToZero;
import org.firstinspires.ftc.teamcode.bot.subsystems.HangingHooks;
import org.firstinspires.ftc.teamcode.bot.subsystems.Intake;
import org.firstinspires.ftc.teamcode.bot.subsystems.PivotSlide;
import org.firstinspires.ftc.teamcode.teleop.TeleOpMain;
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
    private ManualSlideControl manualPivotSlideControl;
    private SetHookAngle setHookAngle;

    private final double PLACER_TURN_SPEED = 0.5;

    @Override
    public Command initialize(State from) {

        TeleOpMain.DRIVER_TURN_SPEED = 1.0;
        TeleOpMain.DRIVER_MOVE_SPEED = 1.0;

        switch(from.getName()) {
            case "HangState":
            case "SpecimenState":
            case "LowDepositState":
            case "DepositState": //Coming from deposit state
                return new SequentialCommandGroup(
                        new SetIntakeDropdownPosition(intake, 0.3),
                        new WaitCommand(200),
                        new ExtendTo(arm, 0.15).withTimeout(1000),
                        new MPPivotTo(arm, 4.5).withTimeout(1200),
                        new RetractSlideToZero(arm)
                );
            case "IntakeState":
                return new SequentialCommandGroup(
                        new ScheduleCommand(new SetIntakeDropdownPosition(intake, 0.8).perpetually()),
                        new RetractSlideToZero(arm)
                );
            case "BaseState":
                return new InstantCommand();
            default: //Default initialization
                localDrive = new LocalDrive(
                        drive,
                        () -> driver.getLeftX() * TeleOpMain.DRIVER_MOVE_SPEED,
                        () -> driver.getLeftY() * TeleOpMain.DRIVER_MOVE_SPEED,
                        () -> driver.getRightX() * TeleOpMain.DRIVER_TURN_SPEED + ((placer.getButton(GamepadKeys.Button.DPAD_RIGHT) ? 1 : 0) - (placer.getButton(GamepadKeys.Button.DPAD_LEFT) ? 1 : 0)) * PLACER_TURN_SPEED);

                powerIntake = new IntakeControl(intake, placer::getLeftX);

                setIntakeDropdownPosition = new SetIntakeDropdownPosition(intake, 0.8).perpetually();
                clawToggle = new ToggleClaw(intake);

                setHookAngle = new SetHookAngle(hooks, 200);

                manualPivotSlideControl = new ManualSlideControl(arm, ()->(
                        placer.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) - placer.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER)
                ), () -> -placer.getRightY());

                return new ParallelCommandGroup(
                        new InstantCommand(() -> {
                            placer.getGamepadButton(GamepadKeys.Button.X)
                                            .toggleWhenPressed(new ConditionalCommand(
                                                    setIntakeDropdownPosition,
                                                    new InstantCommand(),
                                                    () -> !machine.getState().getName().equals("DepositState")
                                            ));

                            placer.getGamepadButton(GamepadKeys.Button.A)
                                            .whenPressed(clawToggle);

                            placer.getGamepadButton(GamepadKeys.Button.Y)
                                            .toggleWhenPressed(setHookAngle);

                            drive.setDefaultCommand(localDrive);
                            intake.setDefaultCommand(powerIntake);
                            arm.setDefaultCommand(manualPivotSlideControl);
                            hooks.setDefaultCommand(new SetHookAngle(hooks, 0.0));
                        }),
                        new RetractSlideToZero(arm)
                );
        }
    }
}
