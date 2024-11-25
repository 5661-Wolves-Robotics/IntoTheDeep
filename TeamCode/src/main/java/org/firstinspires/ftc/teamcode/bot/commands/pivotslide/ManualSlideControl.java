package org.firstinspires.ftc.teamcode.bot.commands.pivotslide;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.bot.subsystems.PivotSlide;

import java.util.function.DoubleSupplier;

@Config
public class ManualSlideControl extends CommandBase {

    private final PivotSlide arm;
    private final DoubleSupplier pow;
    private final DoubleSupplier angleInput;

    public ManualSlideControl(PivotSlide arm, DoubleSupplier pow, DoubleSupplier angle) {
        this.arm = arm;
        this.pow = pow;
        this.angleInput = angle;

        addRequirements(arm);
    }

    @Override
    public void execute() {
        double extension = Range.clip(arm.getExtensionTarget() + ((pow.getAsDouble()) / 10.0), 0, 0.7);
        double targetAngle = arm.getPivotTarget() + angleInput.getAsDouble() * 4.0;
        if(targetAngle > 103) targetAngle = 103;
        arm.setPivotAngle(targetAngle);
        //arm.setExtensionTarget(extension);
    }
}
