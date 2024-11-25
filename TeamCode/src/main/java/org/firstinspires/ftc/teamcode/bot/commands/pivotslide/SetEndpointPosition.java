package org.firstinspires.ftc.teamcode.bot.commands.pivotslide;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.bot.subsystems.PivotSlide;

public class SetEndpointPosition extends CommandBase {

    private final Vector2d point;
    private final PivotSlide arm;
    private final MecanumDrive drive;

    private final double PULLEY_CIRCUMFERENCE = 4.9473900069; //in
    private final double GB_435_PPR = 384.5; //PPR

    public SetEndpointPosition(MecanumDrive drive, PivotSlide arm, Vector2d point) {
        this.arm = arm;
        this.point = point;
        this.drive = drive;
    }

    Vector2d diff;
    Pose2d pose;

    @Override
    public void execute() {
        pose = drive.pose;
        diff = point.minus(pose.position);
        double extLength = (((diff.norm() - 9.0) / PULLEY_CIRCUMFERENCE) * GB_435_PPR) / 2300.0;

        arm.setExtensionTarget(extLength);
    }

    public boolean done() {
        double curr = arm.getExtension();
        double target = arm.getExtensionTarget();

        return Math.abs(target - curr) < 0.015;
    }
}
