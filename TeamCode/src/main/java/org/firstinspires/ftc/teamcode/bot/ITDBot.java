package org.firstinspires.ftc.teamcode.bot;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.Robot;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.bot.subsystems.HangingHooks;
import org.firstinspires.ftc.teamcode.bot.subsystems.ITDLimelight;
import org.firstinspires.ftc.teamcode.bot.subsystems.Intake;
import org.firstinspires.ftc.teamcode.bot.subsystems.PivotSlide;

@Config
public class ITDBot extends Robot {

    public final MecanumDrive drive;
    public final Intake intake;
    public final PivotSlide arm;
    public final ITDLimelight limelight;
    public final HangingHooks hooks;

    public ITDBot(HardwareMap hardwareMap, Telemetry telemetry, Pose2d startPose){
        drive = new MecanumDrive(hardwareMap, startPose);

        arm = new PivotSlide(hardwareMap, telemetry);
        intake = new Intake(hardwareMap, "yaw", "dropdown", "claw");
        limelight = new ITDLimelight(hardwareMap, telemetry);
        hooks = new HangingHooks(hardwareMap);

        register(arm, drive, intake, hooks);
    }

}
