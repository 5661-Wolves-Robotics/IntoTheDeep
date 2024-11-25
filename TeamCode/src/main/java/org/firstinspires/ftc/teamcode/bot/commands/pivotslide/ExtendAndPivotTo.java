package org.firstinspires.ftc.teamcode.bot.commands.pivotslide;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.util.Timing;

import org.firstinspires.ftc.teamcode.bot.subsystems.PivotSlide;
import org.firstinspires.ftc.teamcode.util.motionprofiles.TrapezoidalMotionProfile;

import java.util.concurrent.TimeUnit;

public class ExtendAndPivotTo extends CommandBase {

    private final double angle, extension;
    private final PivotSlide arm;

    public ExtendAndPivotTo(PivotSlide arm, double extension, double angle) {
        this.angle = angle;
        this.arm = arm;
        this.extension = extension;

        addRequirements(arm);
    }

    @Override
    public void initialize() {
        arm.setPivotAngle(angle);
        arm.setExtensionTarget(extension);
    }

    @Override
    public boolean isFinished() {
        double currExt = arm.getExtension();
        double currAngle = arm.getEndpointAngle();
        return (currAngle <= angle + PivotSlide.PIVOT_TOLERANCE && currAngle >= angle - PivotSlide.PIVOT_TOLERANCE) && (currExt <= extension + PivotSlide.EXT_TOLERANCE && currExt >= extension - PivotSlide.EXT_TOLERANCE);
    }
}
