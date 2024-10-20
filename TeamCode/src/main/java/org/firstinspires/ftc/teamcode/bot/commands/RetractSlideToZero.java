package org.firstinspires.ftc.teamcode.bot.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.hardware.DcMotor;

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
        return arm.hasReachedLimit();
    }
}
