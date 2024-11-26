package org.firstinspires.ftc.teamcode.bot.commands.drive;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.PathSupplier;

public class DriveTrajectorySequence extends CommandBase {

    private final MecanumDrive m_drive;
    private final PathSupplier m_path;
    private boolean finished = false;
    private Action action;

    public DriveTrajectorySequence(MecanumDrive drive, PathSupplier trajectory){
        m_drive = drive;
        m_path = trajectory;
    }

    @Override
    public void initialize() {
        action = m_path.getPath(m_drive.actionBuilder(m_drive.pose));
    }

    @Override
    public void execute() {
        TelemetryPacket packet = new TelemetryPacket();
        action.preview(packet.fieldOverlay());
        finished = !action.run(packet);
        FtcDashboard.getInstance().sendTelemetryPacket(packet);
    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}
