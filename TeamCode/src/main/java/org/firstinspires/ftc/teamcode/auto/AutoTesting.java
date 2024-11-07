package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.bot.ITDBot;
import org.firstinspires.ftc.teamcode.bot.commands.drive.DriveTrajectorySequence;
import org.firstinspires.ftc.teamcode.bot.commands.pivotslide.ExtendAndPivotTo;
import org.firstinspires.ftc.teamcode.bot.commands.pivotslide.PivotTo;
import org.firstinspires.ftc.teamcode.bot.commands.pivotslide.RetractSlideToZero;
import org.firstinspires.ftc.teamcode.bot.commands.intake.SetClaw;
import org.firstinspires.ftc.teamcode.bot.commands.intake.SetIntakeDropdownPosition;
import org.firstinspires.ftc.teamcode.bot.commands.intake.SetIntakeYaw;

@Autonomous
public class AutoTesting extends LinearOpMode {

    @Override
    public void runOpMode() {
        ITDBot bot = new ITDBot(hardwareMap, telemetry, new Pose2d(8.5, -72 + 7.795, Math.toRadians(90)));

        telemetry.addLine("INITIALIZED");
        telemetry.update();

        SequentialCommandGroup group = new SequentialCommandGroup(

        );
        group.schedule();

        waitForStart();

        bot.drive.setPosition();

        while(opModeIsActive() && !isStopRequested()){
            CommandScheduler.getInstance().run();
            telemetry.addData("x", bot.drive.localizer.getEncoderX());
            telemetry.update();
        }

        CommandScheduler.getInstance().reset();
    }
}
