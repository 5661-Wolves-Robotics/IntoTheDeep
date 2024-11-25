package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Drawing;
import org.firstinspires.ftc.teamcode.bot.ITDBot;
import org.firstinspires.ftc.teamcode.bot.commands.drive.LocalDrive;
import org.firstinspires.ftc.teamcode.bot.commands.pivotslide.SetEndpointPosition;
import org.firstinspires.ftc.teamcode.bot.states.BaseState;
import org.firstinspires.ftc.teamcode.bot.states.DepositState;
import org.firstinspires.ftc.teamcode.util.DualInputStateMachine;

@TeleOp
public class ArmEndpointTest extends LinearOpMode {

    GamepadEx driver, placer;
    ITDBot bot;

    DualInputStateMachine<ITDBot, BaseState> stateMachine;

    ElapsedTime timer = new ElapsedTime();

    @Override
    public void runOpMode() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        ITDBot bot = new ITDBot(hardwareMap, telemetry, new Pose2d(8.5, -72 + 7.795, Math.toRadians(90)));


        driver  = new GamepadEx(gamepad1);
        placer  = new GamepadEx(gamepad2);

        new LocalDrive(bot.drive, driver::getLeftX, driver::getLeftY, () -> driver.getRightX() + ((placer.getButton(GamepadKeys.Button.DPAD_RIGHT) ? 1 : 0) - (placer.getButton(GamepadKeys.Button.DPAD_LEFT) ? 1 : 0)) * 0.1).schedule();
        new SetEndpointPosition(bot.drive, bot.arm, new Vector2d(8.5, -72 + 7.795)).schedule();

        waitForStart();

        bot.drive.setPosition();

        timer.reset();

        while(opModeIsActive() && !isStopRequested()) {
            telemetry.update();

            TelemetryPacket packet = new TelemetryPacket();
            packet.fieldOverlay().setStroke("#3F51B5");
            Drawing.drawRobot(packet.fieldOverlay(), bot.drive.pose);
            FtcDashboard.getInstance().sendTelemetryPacket(packet);

            CommandScheduler.getInstance().run();
        }

        CommandScheduler.getInstance().reset();
    }

}
