package org.firstinspires.ftc.teamcode.bot.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.util.GoBildaLimitSwitch;
import org.firstinspires.ftc.teamcode.util.motionprofiles.TrapezoidalMotionProfile;

import java.util.function.DoubleSupplier;

@Config
public class PivotSlide extends SubsystemBase {

    private final DcMotorEx m_pivot, m_pivot_2, m_extension, m_extension_2;
    private final AnalogInput m_pitch;
    private final TouchSensor m_bottomLimit;
    private final double offset = -3.0;
    private double extOffset = 0.0;

    private final int MAX_EXTENSION = 2300;
    public double extensionTarget = 0;

    public double pp = 0.08, pi = 0.001, pd = 0.001, pf = 0.001, pex = 0.0;
    public double pivotTarget = 2;
    private final PIDController pivotController = new PIDController(pp, pi, pd);

    public double MAX_VEL = 200, MAX_ACC = 200;

    public static double PIVOT_TOLERANCE = 4, EXT_TOLERANCE = 0.05;

    public PivotSlide(HardwareMap hm, String pivot, String pivot_2, String extension, String extension_2) {
        m_pivot = hm.get(DcMotorEx.class, pivot);
        m_pivot_2 = hm.get(DcMotorEx.class, pivot_2);
        m_extension = hm.get(DcMotorEx.class, extension);
        m_extension_2 = hm.get(DcMotorEx.class, extension_2);

        m_pivot.setDirection(DcMotorSimple.Direction.REVERSE);
        m_pivot_2.setDirection(DcMotorSimple.Direction.REVERSE);

        m_extension.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        m_extension_2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        setExtensionMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        m_extension.setTargetPosition(0);
        m_extension.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        m_extension.setPower(1.0);

        m_extension_2.setTargetPosition(0);
        m_extension_2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        m_extension_2.setPower(1.0);

        m_pitch = hm.get(AnalogInput.class, "pitch");

        m_bottomLimit = hm.get(TouchSensor.class, "bottomLimit");
    }

    final double RATIO = 10.0 / 14.0;

    public double getAngle() {
        double pos = (((m_pitch.getVoltage() / 3.2 * 360) + offset) % 360) * RATIO;
        if(pos >= 180) pos = pos - (360 * RATIO);

        return pos;
    }

    public void resetExtension() {
        extOffset = getExtension();
    }

    public double getExtension() {
        return m_extension.getCurrentPosition() / (double)MAX_EXTENSION;
    }

    public double getExtensionWithOffset() {
        return getExtension() - extOffset;
    }

    public void setExtensionMode(DcMotor.RunMode mode) {
        m_extension.setMode(mode);
        m_extension_2.setMode(mode);
    }

    public double getCurrent() {
        return m_pivot.getCurrent(CurrentUnit.AMPS);
    }

    public double getExtensionCurrent() {
        return m_extension.getCurrent(CurrentUnit.AMPS);
    }

    public void setPivotPow(double power) {
        m_pivot.setPower(power);
        m_pivot_2.setPower(power);
    }

    public void setExtensionPow(double power) {
        m_extension.setPower(power);
        m_extension_2.setPower(power);
    }

    public double getExtOffset() {
        return extOffset;
    }

    public void setPivotAngle(double angle) {
        pivotTarget = angle;
    }

    public void setExtensionTarget(double target){
        extensionTarget = target + extOffset;
    }

    public double getExtensionTarget() {
        return extensionTarget;
    }

    @Override
    public void periodic() {
        double angle = getAngle();

        pivotController.setPID(pp, pi, pd);
        double pivotPow = pivotController.calculate(angle, pivotTarget) + (Math.cos(Math.toRadians(angle)) * pf) * ((1 + getExtension()) * pex);

        setPivotPow(pivotPow);

        m_extension.setTargetPosition((int)(Range.clip(extensionTarget, -1.0, 1.0) * MAX_EXTENSION));
        m_extension_2.setTargetPosition((int)(Range.clip(extensionTarget, -1.0, 1.0) * MAX_EXTENSION));

        boolean currReached = hasReachedLimit();

        if(currReached) {
            resetExtension();
        }
    }

    public boolean hasReachedLimit() {
        return !m_bottomLimit.isPressed();
    }

    public TrapezoidalMotionProfile getMotionProfile(double target) {
        return new TrapezoidalMotionProfile(MAX_VEL, MAX_ACC, getAngle(), target);
    }
}
