package org.firstinspires.ftc.teamcode.bot.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.util.Timing;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.bot.subsystems.PivotSlide;
import org.firstinspires.ftc.teamcode.util.motionprofiles.TrapezoidalMotionProfile;

import java.util.concurrent.TimeUnit;

public class PivotTo extends CommandBase {

    private final double angle;
    private final PivotSlide arm;

    private TrapezoidalMotionProfile motionProfile;
    private Timing.Timer timer;

    public PivotTo(PivotSlide arm, double angle) {
        this.angle = angle;
        this.arm = arm;

        addRequirements(arm);
    }

    @Override
    public void initialize() {
        arm.setPivotAngle(angle);
    }

    @Override
    public boolean isFinished() {
        double curr = arm.getAngle();
        return curr <= angle + PivotSlide.PIVOT_TOLERANCE && curr >= angle - PivotSlide.PIVOT_TOLERANCE;
    }
}
