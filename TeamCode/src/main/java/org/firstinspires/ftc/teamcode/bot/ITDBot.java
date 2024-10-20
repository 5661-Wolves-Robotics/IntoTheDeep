package org.firstinspires.ftc.teamcode.bot;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.Robot;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.bot.subsystems.ITDLimelight;
import org.firstinspires.ftc.teamcode.bot.subsystems.Intake;
import org.firstinspires.ftc.teamcode.bot.subsystems.PivotSlide;

@Config
public class ITDBot extends Robot {

    public final MecanumDrive drive;
    public final Intake intake;
    public final PivotSlide arm;
    public final ITDLimelight limelight;

    public ITDBot(HardwareMap hardwareMap, Telemetry telemetry){
        drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));

        arm = new PivotSlide(hardwareMap, "pivot1", "pivot2", "extension1", "extension2");
        intake = new Intake(hardwareMap, "yaw", "dropdown", "left", "right");
        limelight = new ITDLimelight(hardwareMap, telemetry);

        register(arm, drive, intake);
    }

}
