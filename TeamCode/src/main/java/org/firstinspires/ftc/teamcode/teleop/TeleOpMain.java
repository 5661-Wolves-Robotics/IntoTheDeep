package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Pose2d;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Drawing;
import org.firstinspires.ftc.teamcode.bot.ITDBot;
import org.firstinspires.ftc.teamcode.bot.commands.pivotslide.RetractSlideToZero;
import org.firstinspires.ftc.teamcode.bot.states.BaseState;
import org.firstinspires.ftc.teamcode.bot.states.DepositState;
import org.firstinspires.ftc.teamcode.bot.states.HangState;
import org.firstinspires.ftc.teamcode.bot.states.IntakeState;
import org.firstinspires.ftc.teamcode.bot.states.LowDepositState;
import org.firstinspires.ftc.teamcode.bot.states.SpecimenState;
import org.firstinspires.ftc.teamcode.util.DualInputStateMachine;

@TeleOp
public class TeleOpMain extends LinearOpMode {

    GamepadEx driver, placer;
    ITDBot bot;

    DualInputStateMachine<ITDBot, BaseState> stateMachine;

    ElapsedTime timer = new ElapsedTime();

    public static double DRIVER_TURN_SPEED = 1.0, DRIVER_MOVE_SPEED = 1.0;

    @Override
    public void runOpMode() {

        CommandScheduler.getInstance().reset();
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        bot     = new ITDBot(hardwareMap, telemetry, new Pose2d(-72, 12, 0));

        driver  = new GamepadEx(gamepad1);
        placer  = new GamepadEx(gamepad2);

        stateMachine    = new DualInputStateMachine<>(bot, driver, placer, telemetry);
        BaseState baseState             = new BaseState(stateMachine);
        DepositState depositState       = new DepositState(stateMachine);
        LowDepositState lowDepositState = new LowDepositState(stateMachine);
        IntakeState intakeState         = new IntakeState(stateMachine);
        HangState hangState             = new HangState(stateMachine);
        SpecimenState specimenState     = new SpecimenState(stateMachine);
        stateMachine.set(baseState).schedule();

        driver.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                .whenPressed(stateMachine.set(depositState));
        driver.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                .whenPressed(stateMachine.set(lowDepositState));
        placer.getGamepadButton(GamepadKeys.Button.B)
                .whenPressed(stateMachine.set(baseState));
        driver.getGamepadButton(GamepadKeys.Button.B)
                .whenPressed(stateMachine.set(baseState));
        driver.getGamepadButton(GamepadKeys.Button.X)
                .whenPressed(stateMachine.set(intakeState));
        driver.getGamepadButton(GamepadKeys.Button.Y)
                .whenPressed(stateMachine.set(hangState));
        driver.getGamepadButton(GamepadKeys.Button.A)
                .whenPressed(stateMachine.set(specimenState));

        waitForStart();

        bot.drive.setPosition();

        timer.reset();

        while(opModeIsActive() && !isStopRequested()) {

            telemetry.addData("state", stateMachine.getState().getName());
            telemetry.addData("angle", bot.arm.getPivotAngle());
            telemetry.addData("voltage", bot.arm.getPivotEncoderVoltage());
            telemetry.addData("target", bot.arm.getPivotTarget());
            telemetry.addData("extension", bot.arm.getExtensionWithOffset());
            telemetry.addData("reached", bot.arm.hasSlideReachedLimit());
            telemetry.update();

            TelemetryPacket packet = new TelemetryPacket();
            packet.fieldOverlay().setStroke("#3F51B5");
            Drawing.drawRobot(packet.fieldOverlay(), bot.drive.pose);
            FtcDashboard.getInstance().sendTelemetryPacket(packet);

            stateMachine.run();
            CommandScheduler.getInstance().run();
        }

        CommandScheduler.getInstance().reset();
    }

}
