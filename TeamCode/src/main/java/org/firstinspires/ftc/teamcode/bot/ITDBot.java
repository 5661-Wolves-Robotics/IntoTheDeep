package org.firstinspires.ftc.teamcode.bot;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.Robot;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.bot.subsystems.Intake;
import org.firstinspires.ftc.teamcode.bot.subsystems.PivotSlide;

@Config
public class ITDBot extends Robot {

    public final MecanumDrive drive;
    public final Intake intake;
    public final PivotSlide arm;

    public ITDBot(HardwareMap hardwareMap){
        drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));

        arm = new PivotSlide(hardwareMap, "pivot", "extension");
        intake = new Intake(hardwareMap, "intake", "dropdown");

        register(arm, drive, intake);
    }

}
