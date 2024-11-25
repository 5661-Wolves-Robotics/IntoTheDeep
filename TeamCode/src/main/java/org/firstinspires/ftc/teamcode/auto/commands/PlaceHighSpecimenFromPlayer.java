package org.firstinspires.ftc.teamcode.auto.commands;

import com.acmerobotics.roadrunner.Vector2d;
import com.arcrobotics.ftclib.command.CommandGroupBase;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.bot.ITDBot;
import org.firstinspires.ftc.teamcode.bot.commands.drive.DriveTrajectorySequence;
import org.firstinspires.ftc.teamcode.bot.commands.intake.SetClaw;
import org.firstinspires.ftc.teamcode.bot.commands.intake.SetIntakeDropdownPosition;
import org.firstinspires.ftc.teamcode.bot.commands.pivotslide.ExtendAndPivotTo;
import org.firstinspires.ftc.teamcode.bot.commands.pivotslide.ExtendTo;
import org.firstinspires.ftc.teamcode.bot.commands.pivotslide.PivotTo;
import org.firstinspires.ftc.teamcode.bot.commands.pivotslide.RetractSlideToZero;

public class PlaceHighSpecimenFromPlayer extends SequentialCommandGroup {

    final double EXT_INC = 0.1, ANGLE_INC = -20;

    public PlaceHighSpecimenFromPlayer(ITDBot bot, double angle, double extension, DriveTrajectorySequence drive) {
        addCommands(
                new ParallelCommandGroup(
                        new SetIntakeDropdownPosition(bot.intake, 0.8),
                        new ExtendAndPivotTo(bot.arm, extension, angle).withTimeout(500)
                ).alongWith(drive),
                new WaitCommand(200),
                new SequentialCommandGroup(
                        new SetIntakeDropdownPosition(bot.intake, 0.5),
                        new ExtendTo(bot.arm, extension + EXT_INC).withTimeout(600),
                        new WaitCommand(100),
                        new PivotTo(bot.arm, angle + ANGLE_INC).withTimeout(400)
                ),
                new ParallelCommandGroup(
                        new SetClaw(bot.intake, false),
                        new PivotTo(bot.arm, 45).withTimeout(300)
                )
        );
    }

}
