package org.firstinspires.ftc.teamcode.bot.commands;

import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.PathSupplier;

public class DriveTrajectorySequence extends CommandBase {

    private final MecanumDrive m_drive;
    private final PathSupplier m_path;
    private boolean finished = false;

    public DriveTrajectorySequence(MecanumDrive drive, PathSupplier trajectory){
        m_drive = drive;
        m_path = trajectory;

        addRequirements(drive);
    }

    @Override
    public void execute() {
        Actions.runBlocking(m_path.getPath(m_drive.actionBuilder(m_drive.pose)));
        finished = true;
    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}
