package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.ScheduleCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.auto.commands.DepositSampleInHighBucket;
import org.firstinspires.ftc.teamcode.auto.commands.GetSpecimenFromPlayer;
import org.firstinspires.ftc.teamcode.auto.commands.PickUpSampleForBucket;
import org.firstinspires.ftc.teamcode.auto.commands.PickUpSampleForPlayer;
import org.firstinspires.ftc.teamcode.auto.commands.PlaceHighSpecimen;
import org.firstinspires.ftc.teamcode.auto.commands.PlaceHighSpecimenFromPlayer;
import org.firstinspires.ftc.teamcode.bot.ITDBot;
import org.firstinspires.ftc.teamcode.bot.commands.drive.DriveTrajectorySequence;
import org.firstinspires.ftc.teamcode.bot.commands.intake.SetClaw;
import org.firstinspires.ftc.teamcode.bot.commands.pivotslide.ExtendTo;
import org.firstinspires.ftc.teamcode.bot.commands.pivotslide.PivotTo;
import org.firstinspires.ftc.teamcode.bot.commands.pivotslide.RetractSlideToZero;
import org.firstinspires.ftc.teamcode.bot.commands.intake.SetIntakeYaw;
import org.firstinspires.ftc.teamcode.bot.commands.pivotslide.SetEndpointPosition;

@Autonomous
public class SampleAutonomousMain extends LinearOpMode {

    @Override
    public void runOpMode() {
        ITDBot bot = new ITDBot(hardwareMap, telemetry, new Pose2d(-15.5, -72 + 7.795, Math.toRadians(90)));

        SequentialCommandGroup group = new SequentialCommandGroup(
                //Place first specimen
                new PlaceHighSpecimen(bot, 49, 0.45,
                        new DriveTrajectorySequence(bot.drive, builder -> builder
                                .waitSeconds(0.2)
                                .splineTo(new Vector2d(-8.5, -38), Math.toRadians(90))
                                .build())
                ),
                //============================

                //Pick up closest sample
                new PickUpSampleForBucket(bot, new Vector2d(-30, -46), 135, new Vector2d(-49, -26), 0.6),
                new DepositSampleInHighBucket(bot),

                //Pick up middle sample
                new PickUpSampleForBucket(bot, new Vector2d(-59, -46), 90, new Vector2d(-59, -29), 0),
                new DepositSampleInHighBucket(bot),

                //Pick up last sample
                new PickUpSampleForBucket(bot, new Vector2d(-62, -46), 110, new Vector2d(-69, -29), 0.5),
                new DepositSampleInHighBucket(bot),

                new ParallelCommandGroup(
                        new SequentialCommandGroup(
                                new WaitCommand(100),
                                new RetractSlideToZero(bot.arm).withTimeout(1500),
                                new PivotTo(bot.arm, 8).withTimeout(600)
                        ),
                        new DriveTrajectorySequence(bot.drive, builder -> builder
                                .strafeToLinearHeading(new Vector2d(-50, -50), Math.toRadians(45))
                                .build()
                        ).withTimeout(2000)
                )
        );
        group.schedule();

        telemetry.addLine("INITIALIZED");
        telemetry.update();

        waitForStart();

        bot.drive.setPosition();

        while(opModeIsActive() && !isStopRequested()){
            CommandScheduler.getInstance().run();
        }

        CommandScheduler.getInstance().reset();
    }
}
