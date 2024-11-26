package org.firstinspires.ftc.teamcode.bot.commands.pivotslide;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.bot.subsystems.PivotSlide;
import org.firstinspires.ftc.teamcode.util.motionprofiles.TrapezoidalMotionProfile;

public class MPPivotTo extends CommandBase {

    private final double angle;
    private final PivotSlide arm;
    private TrapezoidalMotionProfile motionProfile;
    private ElapsedTime time;

    public MPPivotTo(PivotSlide arm, double angle) {
        this.angle = angle;
        this.arm = arm;
        time = new ElapsedTime();

        addRequirements(arm);
    }

    @Override
    public void initialize() {
        time.reset();
        motionProfile = arm.getMotionProfile(angle);
    }

    @Override
    public void execute() {
        arm.setPivotAngle(motionProfile.get(time.seconds()));
    }

    @Override
    public boolean isFinished() {
        double curr = arm.getEndpointAngle();
        return curr <= angle + PivotSlide.PIVOT_TOLERANCE && curr >= angle - PivotSlide.PIVOT_TOLERANCE;
    }
}
