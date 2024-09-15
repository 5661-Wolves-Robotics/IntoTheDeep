package org.firstinspires.ftc.teamcode.bot.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.bot.subsystems.PivotSlide;

public class PivotTo extends CommandBase {

    private final double angle;
    private final PivotSlide arm;

    private final double TOLERANCE = 5;

    public PivotTo(PivotSlide arm, double angle) {
        this.angle = angle;
        this.arm = arm;
    }

    @Override
    public void initialize() {
        arm.setPivotAngle(angle);
    }

    @Override
    public boolean isFinished() {
        double curr = arm.getAngle();
        return curr <= angle + TOLERANCE && curr >= angle - TOLERANCE;
    }
}
