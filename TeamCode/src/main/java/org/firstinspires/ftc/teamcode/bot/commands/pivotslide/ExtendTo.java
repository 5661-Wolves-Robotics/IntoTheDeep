package org.firstinspires.ftc.teamcode.bot.commands.pivotslide;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.bot.subsystems.PivotSlide;

public class ExtendTo extends CommandBase {

    private final PivotSlide arm;
    private final double target;
    private boolean continuous = false;

    public ExtendTo(PivotSlide arm, double target){
        this.arm = arm;
        this.target = target;

        addRequirements(arm);
    }

    public ExtendTo(PivotSlide arm, double target, boolean continuous) {
        this(arm, target);
        this.continuous = continuous;
    }

    @Override
    public void initialize() {
        arm.setExtensionTarget(target);
        arm.setExtensionPow(1.0);
    }

    @Override
    public boolean isFinished() {
        double curr = arm.getExtension();
        return !continuous && (curr <= target + PivotSlide.EXT_TOLERANCE && curr >= target - PivotSlide.EXT_TOLERANCE);
    }
}
