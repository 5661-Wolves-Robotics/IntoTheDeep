package org.firstinspires.ftc.teamcode.bot.commands.pivotslide;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.bot.subsystems.PivotSlide;
import org.firstinspires.ftc.teamcode.util.motionprofiles.TrapezoidalMotionProfile;

public class PivotTo extends CommandBase {

    private final double angle;
    private final PivotSlide arm;
    private TrapezoidalMotionProfile motionProfile;
    private ElapsedTime time;

    public PivotTo(PivotSlide arm, double angle) {
        this.angle = angle;
        this.arm = arm;
        time = new ElapsedTime();

        addRequirements(arm);
    }

    @Override
    public void initialize() {
        arm.setPivotAngle(angle);
    }

    @Override
    public boolean isFinished() {
        double curr = arm.getEndpointAngle();
        return curr <= angle + PivotSlide.PIVOT_TOLERANCE && curr >= angle - PivotSlide.PIVOT_TOLERANCE;
    }
}
