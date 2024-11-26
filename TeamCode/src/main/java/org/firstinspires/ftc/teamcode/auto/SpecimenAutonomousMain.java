package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.auto.commands.GetSpecimenFromPlayer;
import org.firstinspires.ftc.teamcode.auto.commands.PickUpSampleForPlayer;
import org.firstinspires.ftc.teamcode.auto.commands.PlaceHighSpecimen;
import org.firstinspires.ftc.teamcode.auto.commands.PlaceHighSpecimenFromPlayer;
import org.firstinspires.ftc.teamcode.bot.ITDBot;
import org.firstinspires.ftc.teamcode.bot.commands.drive.DriveTrajectorySequence;
import org.firstinspires.ftc.teamcode.bot.commands.pivotslide.ExtendTo;
import org.firstinspires.ftc.teamcode.bot.commands.pivotslide.PivotTo;
import org.firstinspires.ftc.teamcode.bot.commands.pivotslide.RetractSlideToZero;
import org.firstinspires.ftc.teamcode.bot.commands.intake.SetIntakeYaw;

@Autonomous
public class SpecimenAutonomousMain extends LinearOpMode {

    @Override
    public void runOpMode() {
        ITDBot bot = new ITDBot(hardwareMap, telemetry, new Pose2d(8.5, -72 + 7.795, Math.toRadians(90)));

        SequentialCommandGroup group = new SequentialCommandGroup(
                //Place first specimen
                new PlaceHighSpecimen(bot, 48, 0.45,
                        new DriveTrajectorySequence(bot.drive, builder -> builder
                                .waitSeconds(0.2)
                                .splineTo(new Vector2d(8.5, -38), Math.toRadians(90))
                                .build())
                ),
                //============================

                //Pick up closest sample
                new ParallelCommandGroup(
                        new SequentialCommandGroup(
                                new RetractSlideToZero(bot.arm).withTimeout(1500),
                                new PivotTo(bot.arm, 8).withTimeout(600),
                                new WaitCommand(200),
                                new ExtendTo(bot.arm, 0.565).withTimeout(500)
                        ),
                        new DriveTrajectorySequence(bot.drive, builder -> builder
                                .strafeToLinearHeading(new Vector2d(30, -46), Math.toRadians(45))
                                .build()
                        ).withTimeout(2000),
                        new SetIntakeYaw(bot.intake, -0.6)
                ),
                new WaitCommand(50),
                new PickUpSampleForPlayer(bot, new Vector2d(30, -50)),
                //===========================

                //Pick up middle sample
                new ParallelCommandGroup(
                        new DriveTrajectorySequence(bot.drive, builder -> builder
                                .strafeToLinearHeading(new Vector2d(40, -46), Math.toRadians(46))
                                .build()
                        ).withTimeout(2000),
                        new ExtendTo(bot.arm, 0.572).withTimeout(500)
                ),
                new SetIntakeYaw(bot.intake, -0.6),
                new PickUpSampleForPlayer(bot, new Vector2d(40, -50)),
                //=========================

                //Pick up last sample
                new ParallelCommandGroup(
                        new DriveTrajectorySequence(bot.drive, builder -> builder
                                .strafeToLinearHeading(new Vector2d(50, -46), Math.toRadians(46))
                                .build()
                        ).withTimeout(2000),
                        new SequentialCommandGroup(
                                new ExtendTo(bot.arm, 0.3).withTimeout(500),
                                new ExtendTo(bot.arm, 0.568).withTimeout(500)
                        )
                ),
                new SetIntakeYaw(bot.intake, -0.6),
                new PickUpSampleForPlayer(bot, new Vector2d(30, -50)),
                //===========================

                //Place specimen from wall on high rung
                new GetSpecimenFromPlayer(bot, 0.1),
                new PlaceHighSpecimenFromPlayer(bot, 48, 0.43,
                        new DriveTrajectorySequence(bot.drive, builder -> builder
                                .setTangent(Math.toRadians(180))
                                .splineToLinearHeading(new Pose2d(5, -40, Math.toRadians(90)), Math.toRadians(90))
                                .build())
                ),
                //===========================

                new RetractSlideToZero(bot.arm),
                new GetSpecimenFromPlayer(bot, 0.11),
                new PlaceHighSpecimenFromPlayer(bot, 47.5, 0.43,
                        new DriveTrajectorySequence(bot.drive, builder -> builder
                                .setTangent(Math.toRadians(180))
                                .splineToLinearHeading(new Pose2d(0, -40, Math.toRadians(90)), Math.toRadians(90))
                                .build())
                ),

                new RetractSlideToZero(bot.arm),
                new GetSpecimenFromPlayer(bot, 0.11),
                new PlaceHighSpecimenFromPlayer(bot, 47.5, 0.43,
                        new DriveTrajectorySequence(bot.drive, builder -> builder
                                .setTangent(Math.toRadians(180))
                                .splineToLinearHeading(new Pose2d(2.5, -40, Math.toRadians(90)), Math.toRadians(90))
                                .build())
                ),

                //PARK
                new ParallelCommandGroup(
                        new SequentialCommandGroup(
                                new RetractSlideToZero(bot.arm),
                                new PivotTo(bot.arm, 6),
                                new PivotTo(bot.arm, 0),
                                new RetractSlideToZero(bot.arm)
                        ),
                        new DriveTrajectorySequence(bot.drive, builder -> builder
                                .setReversed(true)
                                .splineTo(new Vector2d(48, -60), 0)
                                .build()
                        )
                )
                //=================
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
