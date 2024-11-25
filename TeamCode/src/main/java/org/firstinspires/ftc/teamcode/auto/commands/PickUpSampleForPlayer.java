package org.firstinspires.ftc.teamcode.auto.commands;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.bot.ITDBot;
import org.firstinspires.ftc.teamcode.bot.commands.drive.DriveTrajectorySequence;
import org.firstinspires.ftc.teamcode.bot.commands.intake.SetClaw;
import org.firstinspires.ftc.teamcode.bot.commands.intake.SetIntakeDropdownPosition;
import org.firstinspires.ftc.teamcode.bot.commands.pivotslide.ExtendTo;
import org.firstinspires.ftc.teamcode.bot.commands.pivotslide.PivotTo;

public class PickUpSampleForPlayer extends SequentialCommandGroup {

    public PickUpSampleForPlayer(ITDBot bot, Vector2d pos) {
        addCommands(
                new PivotTo(bot.arm, 0).withTimeout(150),
                new SetClaw(bot.intake, true),
                new WaitCommand(200),
                new ParallelCommandGroup(
                        new DriveTrajectorySequence(bot.drive, builder -> builder
                                .strafeToLinearHeading(pos, Math.toRadians(-30))
                                .build()
                        ).withTimeout(1000)
                ),
                new SetClaw(bot.intake, false),
                new PivotTo(bot.arm, 9).withTimeout(1)
        );
    }

}
