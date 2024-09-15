package org.firstinspires.ftc.teamcode.bot.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.bot.subsystems.PivotSlide;

public class ExtendTo extends CommandBase {

    private final PivotSlide arm;
    private final double target;
    private final double TOLERANCE = 0.02;

    public ExtendTo(PivotSlide arm, double target){
        this.arm = arm;
        this.target = target;
    }

    @Override
    public void initialize() {
        arm.setExtensionTarget(target);
    }

    @Override
    public boolean isFinished() {
        double curr = arm.getExtension();
        return curr <= target + TOLERANCE && curr >= target - TOLERANCE;
    }
}
