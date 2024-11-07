package org.firstinspires.ftc.teamcode.bot.commands.pivotslide;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.bot.subsystems.PivotSlide;

import java.util.function.DoubleSupplier;

@Config
public class ManualPivotSlideControl extends CommandBase {

    private final PivotSlide arm;
    private final DoubleSupplier pow;
    private final DoubleSupplier angleInput;
    public double targetAngle = 0.0;

    public double pf = 0.0;

    public ManualPivotSlideControl(PivotSlide arm, DoubleSupplier pow, DoubleSupplier angle) {
        this.arm = arm;
        this.pow = pow;
        this.angleInput = angle;

        addRequirements(arm);
    }

    @Override
    public void execute() {
        double extension = Range.clip(arm.getExtensionTarget() + ((Math.abs(pow.getAsDouble()) > 0.4 ? pow.getAsDouble() : 0) / 15.0), 0, 2500);
        arm.setPivotAngle(arm.getPivotTarget() + angleInput.getAsDouble());
        //arm.setExtensionTarget(extension);
    }
}
