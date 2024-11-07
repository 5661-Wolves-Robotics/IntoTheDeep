package org.firstinspires.ftc.teamcode.bot.commands.pivotslide;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.bot.subsystems.PivotSlide;

import java.util.function.DoubleSupplier;

@Config
public class HangSlide extends CommandBase {

    private final PivotSlide arm;
    private final DoubleSupplier pow;
    private final DoubleSupplier angleInput;
    public static double targetAngle = 40;

    public static double pf = 0.0;

    private double extension = 0.4;

    public HangSlide(PivotSlide arm, DoubleSupplier pow, DoubleSupplier angle) {
        this.arm = arm;
        this.pow = pow;
        this.angleInput = angle;

        addRequirements(arm);
    }

    @Override
    public void execute() {
        extension = Range.clip(extension + ((pow.getAsDouble()) / 15.0), 0, 2500);
        targetAngle += angleInput.getAsDouble();
        arm.setPivotAngle(targetAngle);
        arm.setExtensionTarget(extension);
    }
}
