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
public class AutonomousMain extends LinearOpMode {

    @Override
    public void runOpMode() {
        ITDBot bot = new ITDBot(hardwareMap, telemetry, new Pose2d(16.5, -72 + 7.795, Math.toRadians(90)));

        telemetry.addLine("INITIALIZED");
        telemetry.update();

        SequentialCommandGroup group = new SequentialCommandGroup(
                new ParallelCommandGroup(
                        new SequentialCommandGroup(
                                new SetClaw(bot.intake, true),
                                new SetIntakeDropdownPosition(bot.intake, 0.73)
                        ),
                        new ExtendAndPivotTo(bot.arm, 0.4, 37),
                        new DriveTrajectorySequence(bot.drive, builder -> builder
                                .splineTo(new Vector2d(16.5, -38), Math.toRadians(90))
                                .build())
                ),
                new ParallelCommandGroup(
                        new ExtendAndPivotTo(bot.arm, 0.5, 38),
                        new SetIntakeDropdownPosition(bot.intake, 0.5)
                ),
                new WaitCommand(500),
                new ParallelCommandGroup(
                        new SetClaw(bot.intake, false),
                        new RetractSlideToZero(bot.arm).withTimeout(1500)
                ),
                new ParallelCommandGroup(
                        new DriveTrajectorySequence(bot.drive, builder -> builder
                                .setTangent(0)
                                .splineToLinearHeading(new Pose2d(37, -39, Math.toRadians(28)), 0)
                                .build()
                        ),
                        new SequentialCommandGroup(
                                new WaitCommand(700),
                                new ExtendAndPivotTo(bot.arm, 0.5, 2),
                                new SetIntakeYaw(bot.intake, -0.6),
                                new WaitCommand(700),
                                new PivotTo(bot.arm, -1).withTimeout(700),
                                new SetClaw(bot.intake, true),
                                new WaitCommand(400),
                                new PivotTo(bot.arm, 2),
                                new DriveTrajectorySequence(bot.drive, builder -> builder
                                        .setTangent(Math.toRadians(-45))
                                        .splineToLinearHeading(new Pose2d(40, -46, Math.toRadians(-45)), Math.toRadians(-45))
                                        .build()
                                ),
                                new SetClaw(bot.intake, false),
                                new PivotTo(bot.arm, 3),
                                new SetIntakeYaw(bot.intake, -0.7),
                                new DriveTrajectorySequence(bot.drive, builder -> builder
                                        .setTangent(Math.toRadians(50))
                                        .splineToLinearHeading(new Pose2d(47, -40, Math.toRadians(34)), Math.toRadians(50))
                                        .build()
                                ),
                                new PivotTo(bot.arm, -3).withTimeout(700),
                                new SetClaw(bot.intake, true),
                                new WaitCommand(600),
                                new PivotTo(bot.arm, 3),
                                new DriveTrajectorySequence(bot.drive, builder -> builder
                                        .setTangent(Math.toRadians(-90))
                                        .splineToLinearHeading(new Pose2d(47, -46, Math.toRadians(-45)), Math.toRadians(-90))
                                        .build()
                                ),
                                new SetClaw(bot.intake, false),
                                new PivotTo(bot.arm, 3),
                                new SetIntakeYaw(bot.intake, -0.7),
                                new DriveTrajectorySequence(bot.drive, builder -> builder
                                        .setTangent(Math.toRadians(50))
                                        .splineToLinearHeading(new Pose2d(56, -40, Math.toRadians(35)), Math.toRadians(50))
                                        .build()
                                ),
                                new PivotTo(bot.arm, -3).withTimeout(700),
                                new SetClaw(bot.intake, true),
                                new WaitCommand(300),
                                new PivotTo(bot.arm, 3),
                                new DriveTrajectorySequence(bot.drive, builder -> builder
                                        .setTangent(Math.toRadians(-150))
                                        .splineToLinearHeading(new Pose2d(40, -46, Math.toRadians(-45)), Math.toRadians(-150))
                                        .build()
                                ),
                                new SetClaw(bot.intake, false),
                                new ParallelCommandGroup(
                                        new DriveTrajectorySequence(bot.drive, builder -> builder
                                                .setTangent(Math.toRadians(-180))
                                                .splineToLinearHeading(new Pose2d(42, -46, Math.toRadians(-90)), Math.toRadians(-180))
                                                .build()
                                        ),
                                        new ExtendAndPivotTo(bot.arm, 0.1, 11),
                                        new SetIntakeDropdownPosition(bot.intake, 0.4)
                                ),
                                new WaitCommand(400),
                                new ExtendAndPivotTo(bot.arm, 0.44, 11),
                                new SetClaw(bot.intake, true),
                                new WaitCommand(300),
                                new SetIntakeDropdownPosition(bot.intake, 1),
                                new ParallelCommandGroup(
                                        new SequentialCommandGroup(
                                                new ExtendAndPivotTo(bot.arm, 0.1, 36),
                                                new SetIntakeDropdownPosition(bot.intake, 0.73),
                                                new ExtendAndPivotTo(bot.arm, 0.4, 36)
                                        ),
                                        new DriveTrajectorySequence(bot.drive, builder -> builder
                                                .splineToLinearHeading(new Pose2d(10, -40, Math.toRadians(-270)), Math.toRadians(90))
                                                .build()
                                        )
                                ),
                                new ExtendAndPivotTo(bot.arm, 0.6, 34),
                                new WaitCommand(600),
                                new SetClaw(bot.intake, false),
                                new RetractSlideToZero(bot.arm).withTimeout(700),
                                new SetIntakeDropdownPosition(bot.intake, 1),
                                new DriveTrajectorySequence(bot.drive, builder -> builder
                                        .setReversed(true)
                                        .splineTo(new Vector2d(48, -55), 0)
                                        .build()
                                )
                        )
                )
        );
        group.schedule();

        waitForStart();

        bot.drive.setPosition();

        while(opModeIsActive() && !isStopRequested()){
            CommandScheduler.getInstance().run();
        }

        CommandScheduler.getInstance().reset();
    }
}
