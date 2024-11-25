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
import org.firstinspires.ftc.teamcode.bot.commands.pivotslide.PivotTo;
import org.firstinspires.ftc.teamcode.bot.commands.pivotslide.RetractSlideToZero;

public class PlaceHighSpecimen extends SequentialCommandGroup {

    final double EXT_INC = 0.02, ANGLE_INC = -25;

    public PlaceHighSpecimen(ITDBot bot, double angle, double extension, DriveTrajectorySequence drive) {
        addCommands(
                new ParallelCommandGroup(
                    new SequentialCommandGroup(
                            new WaitCommand(50),
                            new SetClaw(bot.intake, true),
                            new SetIntakeDropdownPosition(bot.intake, 0.55)
                    ),
                    new ExtendAndPivotTo(bot.arm, extension, angle)
                ).alongWith(drive),
                new ParallelCommandGroup(
                        new ExtendAndPivotTo(bot.arm, extension + EXT_INC, angle + ANGLE_INC).withTimeout(400),
                        new SetIntakeDropdownPosition(bot.intake, 0.45)
                ),
                new ParallelCommandGroup(
                        new SetClaw(bot.intake, false),
                        new PivotTo(bot.arm, 45).withTimeout(400)
                )
        );
    }

}
