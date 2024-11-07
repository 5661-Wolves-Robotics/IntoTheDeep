package org.firstinspires.ftc.teamcode.bot.commands.pivotslide;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.util.Timing;

import org.firstinspires.ftc.teamcode.bot.subsystems.PivotSlide;
import org.firstinspires.ftc.teamcode.util.motionprofiles.TrapezoidalMotionProfile;

import java.util.concurrent.TimeUnit;

public class ExtendAndPivotTo extends CommandBase {

    private final double angle, extension;
    private final PivotSlide arm;

    private TrapezoidalMotionProfile motionProfile;
    private Timing.Timer timer;
    public static double pos = 0;

    public ExtendAndPivotTo(PivotSlide arm, double extension, double angle) {
        this.angle = angle;
        this.arm = arm;
        this.extension = extension;

        addRequirements(arm);
    }

    @Override
    public void initialize() {
        motionProfile = arm.getMotionProfile(angle);
        timer = new Timing.Timer(10000, TimeUnit.MILLISECONDS);
        timer.start();

        arm.setExtensionTarget(extension);
    }

    @Override
    public void execute() {
        arm.setPivotAngle(motionProfile.get(timer.elapsedTime() / 1000.0));
        pos = motionProfile.get(timer.elapsedTime() / 1000.0);
    }

    @Override
    public boolean isFinished() {
        double currExt = arm.getExtensionWithOffset();
        double currAngle = arm.getPivotAngle();
        return (currAngle <= angle + PivotSlide.PIVOT_TOLERANCE && currAngle >= angle - PivotSlide.PIVOT_TOLERANCE) && (currExt <= extension + PivotSlide.EXT_TOLERANCE && currExt >= extension - PivotSlide.EXT_TOLERANCE);
    }
}
