package org.firstinspires.ftc.teamcode.bot.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.util.Timing;

import org.firstinspires.ftc.teamcode.bot.subsystems.PivotSlide;

import java.util.concurrent.TimeUnit;

public class SinusoidalRaise extends CommandBase {

    private final PivotSlide arm;
    private Timing.Timer timer;
    private final long TIME;
    private final double HEIGHT;
    private final double OFFSET;

    public SinusoidalRaise(PivotSlide arm, long mill, double height, double offset) {
        this.arm = arm;
        TIME = mill;
        HEIGHT = height;
        OFFSET = offset;
    }

    @Override
    public void initialize() {
        timer = new Timing.Timer(TIME, TimeUnit.MILLISECONDS);
        timer.start();
    }

    @Override
    public void execute() {
        double progress = (double)timer.elapsedTime() / TIME;
        arm.setPivotAngle(Math.sin(progress * Math.PI) * HEIGHT + OFFSET);
    }

    @Override
    public boolean isFinished() {
        return timer.done();
    }
}
