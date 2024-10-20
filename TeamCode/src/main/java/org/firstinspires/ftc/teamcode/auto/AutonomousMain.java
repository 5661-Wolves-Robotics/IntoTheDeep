package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Vector2d;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Drawing;
import org.firstinspires.ftc.teamcode.bot.ITDBot;
import org.firstinspires.ftc.teamcode.bot.commands.DriveTrajectorySequence;

@Autonomous
public class AutonomousMain extends LinearOpMode {

    @Override
    public void runOpMode() {
        ITDBot bot = new ITDBot(hardwareMap, telemetry);

        CommandScheduler.getInstance().registerSubsystem(bot.drive);

        CommandScheduler.getInstance().schedule(
                new DriveTrajectorySequence(bot.drive, builder -> builder
                        .splineTo(new Vector2d(30, 30), Math.PI / 2)
                        .splineTo(new Vector2d(0, 60), Math.PI)
                        .build())
        );

        telemetry.addLine("INITIALIZED");
        telemetry.update();

        waitForStart();

        while(opModeIsActive() && !isStopRequested()){
            CommandScheduler.getInstance().run();

            TelemetryPacket packet = new TelemetryPacket();
            packet.fieldOverlay().setStroke("#3F51B5");
            Drawing.drawRobot(packet.fieldOverlay(), bot.drive.pose);
            FtcDashboard.getInstance().sendTelemetryPacket(packet);
        }
    }
}
