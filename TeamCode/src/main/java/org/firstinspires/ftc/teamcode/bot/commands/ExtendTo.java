package org.firstinspires.ftc.teamcode.bot.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.bot.subsystems.PivotSlide;

public class ExtendTo extends CommandBase {

    private final PivotSlide arm;
    private final double target;

    public ExtendTo(PivotSlide arm, double target){
        this.arm = arm;
        this.target = target;

        addRequirements(arm);
    }

    @Override
    public void initialize() {
        arm.setExtensionTarget(target);
    }

    @Override
    public boolean isFinished() {
        double curr = arm.getExtensionWithOffset();
        return curr <= target + PivotSlide.EXT_TOLERANCE && curr >= target - PivotSlide.EXT_TOLERANCE;
    }
}
