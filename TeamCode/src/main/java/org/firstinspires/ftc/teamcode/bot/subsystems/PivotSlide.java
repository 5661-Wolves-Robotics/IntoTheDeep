package org.firstinspires.ftc.teamcode.bot.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

import java.util.function.DoubleSupplier;

@Config
public class PivotSlide extends SubsystemBase {

    private final DcMotorEx m_pivot, m_extension;
    private final double offset = 0.1;

    private final int MAX_EXTENSION = 3500;
    public double extensionTarget = 0;

    public double pp = 0.04, pi = 0.002, pd = 0.005, pf = 0.01;
    public double pivotTarget = 5;
    private final PIDController pivotController = new PIDController(pp, pi, pd);

    public PivotSlide(HardwareMap hm, String pivot, String extension) {
        m_pivot = hm.get(DcMotorEx.class, pivot);
        m_extension = hm.get(DcMotorEx.class, extension);

        m_pivot.setDirection(DcMotorSimple.Direction.REVERSE);

        m_extension.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        m_extension.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        m_pivot.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        m_extension.setTargetPosition(0);
        m_extension.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        m_extension.setPower(1.0);

        m_pivot.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public double getAngle() {
        return (m_pivot.getCurrentPosition() / (1425.1 * 5)) * 360 - offset;
    }

    public double getExtension() {
        return m_extension.getCurrentPosition() / (double)MAX_EXTENSION;
    }

    public double getCurrent() {
        return m_pivot.getCurrent(CurrentUnit.AMPS);
    }

    public double getExtensionCurrent() {
        return m_extension.getCurrent(CurrentUnit.AMPS);
    }

    public void setPivotPow(double power) {
        m_pivot.setPower(power);
    }

    public void setExtensionPow(double power) {
        m_extension.setPower(power);
    }

    public void setPivotAngle(double angle) {
        pivotTarget = angle;
    }

    public void setExtensionTarget(double target){
        extensionTarget = target;
    }

    @Override
    public void periodic() {
        double angle = getAngle();

        pivotController.setPID(pp, pi, pd);
        double pivotPow = pivotController.calculate(angle, pivotTarget) + (Math.cos(Math.toRadians(angle)) * pf);

        setPivotPow(pivotPow);

        m_extension.setTargetPosition((int)(Range.clip(extensionTarget, 0.0, 1.0) * MAX_EXTENSION));
    }
}
