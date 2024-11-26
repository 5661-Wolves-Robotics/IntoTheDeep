package org.firstinspires.ftc.teamcode.auto.commands;

import com.acmerobotics.roadrunner.Vector2d;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.bot.ITDBot;
import org.firstinspires.ftc.teamcode.bot.commands.drive.DriveTrajectorySequence;
import org.firstinspires.ftc.teamcode.bot.commands.intake.SetClaw;
import org.firstinspires.ftc.teamcode.bot.commands.intake.SetIntakeDropdownPosition;
import org.firstinspires.ftc.teamcode.bot.commands.pivotslide.ExtendAndPivotTo;
import org.firstinspires.ftc.teamcode.bot.commands.pivotslide.ExtendTo;
import org.firstinspires.ftc.teamcode.bot.commands.pivotslide.RetractSlideToZero;

public class GetSpecimenFromPlayer extends SequentialCommandGroup {

    public GetSpecimenFromPlayer(ITDBot bot, double extension) {
        addCommands(
                new ParallelCommandGroup(
                        new DriveTrajectorySequence(bot.drive, builder -> builder
                                .strafeToLinearHeading(new Vector2d(35, -53), Math.toRadians(-90))
                                .build()
                        ),
                        new SetIntakeDropdownPosition(bot.intake, 0.5),
                        new ExtendAndPivotTo(bot.arm, extension, 21).withTimeout(500)
                ),
                new ExtendTo(bot.arm, extension + 0.09),
                new SetClaw(bot.intake, true),
                new WaitCommand(150),
                new SetIntakeDropdownPosition(bot.intake, 1.0),
                new WaitCommand(100)
        );
    }

}
