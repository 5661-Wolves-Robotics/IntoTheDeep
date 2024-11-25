package org.firstinspires.ftc.teamcode.auto.commands;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.ScheduleCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.bot.ITDBot;
import org.firstinspires.ftc.teamcode.bot.commands.drive.DriveTrajectorySequence;
import org.firstinspires.ftc.teamcode.bot.commands.intake.SetClaw;
import org.firstinspires.ftc.teamcode.bot.commands.intake.SetIntakeDropdownPosition;
import org.firstinspires.ftc.teamcode.bot.commands.intake.SetIntakeYaw;
import org.firstinspires.ftc.teamcode.bot.commands.pivotslide.ExtendTo;
import org.firstinspires.ftc.teamcode.bot.commands.pivotslide.PivotTo;
import org.firstinspires.ftc.teamcode.bot.commands.pivotslide.RetractSlideToZero;
import org.firstinspires.ftc.teamcode.bot.commands.pivotslide.SetEndpointPosition;

public class PickUpSampleForBucket extends SequentialCommandGroup {

    public PickUpSampleForBucket(ITDBot bot, Vector2d pos, double heading, Vector2d endpointPos, double yaw) {
        SetEndpointPosition setEndpointPosition = new SetEndpointPosition(bot.drive, bot.arm, endpointPos);
        addCommands(
                new ParallelCommandGroup(
                        new SequentialCommandGroup(
                                new WaitCommand(100),
                                new RetractSlideToZero(bot.arm).withTimeout(1500),
                                new PivotTo(bot.arm, 9).withTimeout(600),
                                new WaitCommand(100),
                                new ScheduleCommand(setEndpointPosition)
                        ),
                        new DriveTrajectorySequence(bot.drive, builder -> builder
                                .strafeToLinearHeading(pos, Math.toRadians(heading))
                                .build()
                        ).withTimeout(2000),
                        new SetIntakeYaw(bot.intake, yaw)
                ),
                new WaitUntilCommand(setEndpointPosition::done),
                new SequentialCommandGroup(
                        new PivotTo(bot.arm, 0).withTimeout(100),
                        new SetClaw(bot.intake, true),
                        new WaitCommand(100),
                        new InstantCommand(setEndpointPosition::cancel)
                )
        );
    }

}
