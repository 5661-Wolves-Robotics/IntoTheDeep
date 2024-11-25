package org.firstinspires.ftc.teamcode.auto.commands;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.ScheduleCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.bot.ITDBot;
import org.firstinspires.ftc.teamcode.bot.commands.drive.DriveTrajectorySequence;
import org.firstinspires.ftc.teamcode.bot.commands.intake.SetClaw;
import org.firstinspires.ftc.teamcode.bot.commands.intake.SetIntakeDropdownPosition;
import org.firstinspires.ftc.teamcode.bot.commands.pivotslide.ExtendTo;
import org.firstinspires.ftc.teamcode.bot.commands.pivotslide.PivotTo;
import org.firstinspires.ftc.teamcode.bot.commands.pivotslide.RetractSlideToZero;

public class DepositSampleInHighBucket extends SequentialCommandGroup {

    public DepositSampleInHighBucket(ITDBot bot) {
        addCommands(
                new ParallelCommandGroup(
                        new DriveTrajectorySequence(bot.drive, builder -> builder
                                .strafeToLinearHeading(new Vector2d(-58, -58), Math.toRadians(45))
                                .build()
                        ).withTimeout(2000),
                        new SequentialCommandGroup(
                                new SetIntakeDropdownPosition(bot.intake, 0.8),
                                new RetractSlideToZero(bot.arm).withTimeout(3000),
                                new SetIntakeDropdownPosition(bot.intake, 0.5),
                                new PivotTo(bot.arm, 99).withTimeout(700),
                                new ExtendTo(bot.arm, 1.0).withTimeout(2000)
                        )
                ),
                new SetIntakeDropdownPosition(bot.intake, 0.9),
                new WaitCommand(100),
                new DriveTrajectorySequence(bot.drive, builder -> builder
                        .strafeToLinearHeading(new Vector2d(-61, -61), Math.toRadians(45))
                        .build()
                ).withTimeout(500),
                new SetClaw(bot.intake, false),
                new WaitCommand(100),
                new SetIntakeDropdownPosition(bot.intake, 0.3),
                new WaitCommand(200)

        );
    }

}
