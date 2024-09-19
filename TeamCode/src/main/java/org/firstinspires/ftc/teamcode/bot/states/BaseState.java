package org.firstinspires.ftc.teamcode.bot.states;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.bot.ITDBot;
import org.firstinspires.ftc.teamcode.bot.commands.ExtendTo;
import org.firstinspires.ftc.teamcode.bot.commands.LocalDrive;
import org.firstinspires.ftc.teamcode.bot.commands.PivotTo;
import org.firstinspires.ftc.teamcode.bot.commands.PowerIntake;
import org.firstinspires.ftc.teamcode.bot.commands.SetIntakePos;
import org.firstinspires.ftc.teamcode.bot.commands.SinusoidalRaise;
import org.firstinspires.ftc.teamcode.bot.subsystems.Intake;
import org.firstinspires.ftc.teamcode.bot.subsystems.PivotSlide;
import org.firstinspires.ftc.teamcode.util.DualInputStateMachine;
import org.firstinspires.ftc.teamcode.util.State;
import org.firstinspires.ftc.teamcode.util.StateMachine;

public class BaseState extends State {

    protected final Intake intake;
    protected final PivotSlide arm;
    protected final MecanumDrive drive;
    protected final GamepadEx driver;
    protected final GamepadEx placer;

    public BaseState(DualInputStateMachine<ITDBot, BaseState> machine) {
        super(machine);
        ITDBot bot = machine.bot;
        intake = bot.intake;
        arm = bot.arm;
        drive = bot.drive;
        driver = machine.driver1;
        placer = machine.driver2;
    }

    protected LocalDrive localDrive;
    protected PowerIntake powerIntake;

    @Override
    public Command initialize(State from) {
        switch(from.getName()) {
            case "DepositState": //Coming from deposit state
                return new SequentialCommandGroup(
                        new ExtendTo(arm, 0.3),
                        new PivotTo(arm, 20),
                        new ParallelCommandGroup(
                                new ExtendTo(arm, 0.0),
                                new PivotTo(arm, 5),
                                new SetIntakePos(intake, Intake.STORED)
                        )
                );

            case "IntakeState": //Coming from intake state
                return new ParallelCommandGroup(
                        new SetIntakePos(intake, Intake.STORED),
                        new PivotTo(arm, 5),
                        new ExtendTo(arm, 0.0)
                );

            default: //Default initialization
                return new InstantCommand(() -> {
                    localDrive = new LocalDrive(drive, driver::getLeftX, driver::getLeftY, driver::getRightX);

                    powerIntake = new PowerIntake(intake, ()->(
                            0.5 + (placer.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) / 2.0) - (placer.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) / 2.0)
                    ));

                    drive.setDefaultCommand(localDrive);
                    intake.setDefaultCommand(powerIntake);
                });

        }
    }
}
