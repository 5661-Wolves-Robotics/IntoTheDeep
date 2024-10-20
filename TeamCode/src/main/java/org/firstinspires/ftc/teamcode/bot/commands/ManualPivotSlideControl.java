package org.firstinspires.ftc.teamcode.bot.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.bot.subsystems.Intake;
import org.firstinspires.ftc.teamcode.bot.subsystems.PivotSlide;

import java.util.function.DoubleSupplier;

public class ManualPivotSlideControl extends CommandBase {

    private final PivotSlide arm;
    private final DoubleSupplier pow;
    private double angle;

    public ManualPivotSlideControl(PivotSlide arm, DoubleSupplier pow, DoubleSupplier angle) {
        this.arm = arm;
        this.pow = pow;

        addRequirements(arm);
    }

    @Override
    public void execute() {
        arm.setExtensionTarget(arm.getExtension() + (pow.getAsDouble() / 10.0));
    }
}
