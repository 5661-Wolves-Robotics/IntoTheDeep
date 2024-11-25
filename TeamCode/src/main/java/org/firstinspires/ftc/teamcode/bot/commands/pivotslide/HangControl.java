package org.firstinspires.ftc.teamcode.bot.commands.pivotslide;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.bot.subsystems.PivotSlide;

import java.util.function.DoubleSupplier;

@Config
public class HangControl extends CommandBase {

    private final PivotSlide arm;
    private final DoubleSupplier pow;
    private final DoubleSupplier angleInput;

    double extension = 0.5;
    double targetAngle = 60;

    public HangControl(PivotSlide arm, DoubleSupplier pow, DoubleSupplier angle) {
        this.arm = arm;
        this.pow = pow;
        this.angleInput = angle;

        addRequirements(arm);
    }

    public HangControl(PivotSlide arm, DoubleSupplier pow, DoubleSupplier angle, double ext, double startAngle) {
        this.arm = arm;
        this.pow = pow;
        this.angleInput = angle;
        this.targetAngle = startAngle;
        this.extension = ext;

        addRequirements(arm);
    }

    @Override
    public void execute() {
        extension = Range.clip(extension + ((pow.getAsDouble()) / 10.0), 0, 0.65);
        targetAngle += angleInput.getAsDouble() * 4.0;
        if(targetAngle > 99) targetAngle = 99;
        arm.setPivotAngle(targetAngle);
        arm.setExtensionTarget(extension);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
