package org.firstinspires.ftc.teamcode.bot.commands.pivotslide;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.bot.subsystems.PivotSlide;

public class RetractSlideToZero extends CommandBase {

    private final PivotSlide arm;

    public RetractSlideToZero(PivotSlide arm){
        this.arm = arm;

        addRequirements(arm);
    }

    @Override
    public void initialize() {
        arm.setExtensionTarget(-1.0);
    }

    @Override
    public boolean isFinished() {
        boolean finished = arm.hasSlideReachedLimit();
        if(finished){
            arm.resetExtension();
            arm.setExtensionTarget(0.02);
        }
        return finished;
    }
}
