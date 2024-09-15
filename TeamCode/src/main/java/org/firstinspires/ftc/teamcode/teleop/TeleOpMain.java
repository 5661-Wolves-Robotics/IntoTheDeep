package org.firstinspires.ftc.teamcode.teleop;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.bot.ITDBot;
import org.firstinspires.ftc.teamcode.bot.commands.ExtendTo;
import org.firstinspires.ftc.teamcode.bot.commands.LocalDrive;
import org.firstinspires.ftc.teamcode.bot.commands.PivotTo;
import org.firstinspires.ftc.teamcode.bot.commands.PowerIntake;
import org.firstinspires.ftc.teamcode.bot.states.BaseState;
import org.firstinspires.ftc.teamcode.bot.states.DepositState;
import org.firstinspires.ftc.teamcode.bot.states.IntakeState;
import org.firstinspires.ftc.teamcode.bot.subsystems.Intake;
import org.firstinspires.ftc.teamcode.bot.subsystems.PivotSlide;
import org.firstinspires.ftc.teamcode.util.StateMachine;

@TeleOp
public class TeleOpMain extends LinearOpMode {

    GamepadEx driver;
    LocalDrive localDrive;
    PowerIntake powerIntake;

    ITDBot bot;
    StateMachine<ITDBot, BaseState> stateMachine;

    IntakeState intakeState;
    DepositState depositState;

    @Override
    public void runOpMode() {
        driver = new GamepadEx(gamepad1);
        bot = new ITDBot(hardwareMap);

        stateMachine = new StateMachine<>(bot);
        stateMachine.setState(new BaseState(stateMachine, "BaseState")).schedule();
        intakeState = new IntakeState(stateMachine, "IntakeState");
        depositState = new DepositState(stateMachine, "DepositState");

        localDrive = new LocalDrive(bot.drive, driver::getLeftX, driver::getLeftY, driver::getRightX);

        powerIntake = new PowerIntake(bot.intake, ()->(
                0.5 + (driver.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) / 2.0) - (driver.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) / 2.0)
        ));

        bot.drive.setDefaultCommand(localDrive);
        bot.intake.setDefaultCommand(powerIntake);

        driver.getGamepadButton(GamepadKeys.Button.A)
                .whenPressed(stateMachine.set(intakeState));
        driver.getGamepadButton(GamepadKeys.Button.B)
                .whenPressed(stateMachine.set(depositState));

        telemetry.addData("angle", bot.arm::getAngle);
        telemetry.addData("extension", bot.arm::getExtension);
        telemetry.addData("pivotCurrent", bot.arm::getCurrent);
        telemetry.addData("extensionCurrent", bot.arm::getExtensionCurrent);

        waitForStart();

        while(opModeIsActive() && !isStopRequested()) {
            stateMachine.run();
            telemetry.addData("currentState", stateMachine.getState());
            CommandScheduler.getInstance().run();
            telemetry.update();
        }
    }

}
