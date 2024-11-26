package org.firstinspires.ftc.teamcode.bot.commands.drive;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.MecanumDrive;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class LocalDrive extends CommandBase {

    private final MecanumDrive m_drive;
    private final DoubleSupplier m_x;
    private final DoubleSupplier m_y;
    private final DoubleSupplier m_rot;

    private final ElapsedTime time;

    public LocalDrive(MecanumDrive drive, DoubleSupplier x, DoubleSupplier y, DoubleSupplier rot){
        m_drive = drive;
        m_x = x;
        m_y = y;
        m_rot = rot;

        time = new ElapsedTime();

        addRequirements(drive);
    }

    double lastTime = 0;
    double deltaTime = 0;

    @Override
    public void execute() {

        m_drive.setDrivePowers(new PoseVelocity2d(
                new Vector2d(
                    m_y.getAsDouble(),
                    -m_x.getAsDouble()
                ),
                -m_rot.getAsDouble()
        ));

        m_drive.updatePoseEstimate();

    }
}
