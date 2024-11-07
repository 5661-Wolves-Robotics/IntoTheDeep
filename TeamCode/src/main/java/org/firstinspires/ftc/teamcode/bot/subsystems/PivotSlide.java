package org.firstinspires.ftc.teamcode.bot.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.ftc.RawEncoder;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsTouchSensor;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.motionprofiles.TrapezoidalMotionProfile;

@Config
public class PivotSlide extends SubsystemBase {

    private final DcMotorEx pivot, pivot2, extension, extension2;
    private final RawEncoder extensionEncoder;
    private final AnalogInput pitchEncoder;
    private final ModernRoboticsTouchSensor bottomLimit;
    private final Telemetry telem;

    private final int MAX_EXTENSION = 2300;
    public double MAX_VEL = 200, MAX_ACC = 10000;
    public static double PIVOT_TOLERANCE = 3, EXT_TOLERANCE = 0.05;


    public static double kp = 0.04, ki = 0.0005, kd = 0.002, kf = 0.05, kf_ex = 0.4;
    public static double ex_kp = 0.04, ex_ki = 0.0005, ex_kd = 0.002;
    final double PITCH_GEAR_RATIO = 10.0 / 14.0;
    private final PIDController pivotController = new PIDController(kp, ki, kd),
                                extensionController = new PIDController(ex_kp, ex_ki, ex_kd);
    private final double offset = -2.0;
    private double extOffset = 0.0;

    public double pivotTarget = 0;
    public double extensionTarget = 0.05;

    public PivotSlide(HardwareMap hm, Telemetry telemetry) {
        pivot = hm.get(DcMotorEx.class, "pivot1");
        pivot2 = hm.get(DcMotorEx.class, "pivot2");
        pitchEncoder = hm.get(AnalogInput.class, "pitch");

        setPivotDirection(DcMotorSimple.Direction.REVERSE);
        setPivotMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        extension = hm.get(DcMotorEx.class, "extension1");
        extension2 = hm.get(DcMotorEx.class, "extension2");
        extensionEncoder = new RawEncoder(hm.get(DcMotorEx.class, "extension1"));
        bottomLimit = hm.get(ModernRoboticsTouchSensor.class, "bottomLimit");

        setExtensionMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setExtensionTarget(0.05);
        setExtensionMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        setExtensionPow(0.0);

        telem = telemetry;
    }

//SLIDE PIVOT
    private void setPivotPow(double power) {
        pivot.setPower(power);
        pivot2.setPower(power);
    }

    public void setPivotAngle(double angle) {
        pivotTarget = angle;
    }

    public void setPivotDirection(DcMotorSimple.Direction direction) {
        pivot.setDirection(direction);
        pivot2.setDirection(direction);
    }

    public void setPivotMode(DcMotor.RunMode mode) {
        pivot.setMode(mode);
        pivot2.setMode(mode);
    }

    public double getPivotTarget() {
        return pivotTarget;
    }

    public double getPivotAngle() {
        double pos = (((pitchEncoder.getVoltage() / 3.2 * 360) + offset) % 360) * PITCH_GEAR_RATIO;
        if(pos >= 180) pos = pos - (360 * PITCH_GEAR_RATIO);

        return pos;
    }
//=============

//SLIDE EXTENSION
    public void setExtensionMode(DcMotor.RunMode mode) {
        extension.setMode(mode);
        extension2.setMode(mode);
    }

    public void setExtensionTarget(double target){
        extensionTarget = target + extOffset;
    }

    public void setExtensionPow(double power) {
        extension.setPower(power);
        extension2.setPower(power);
    }

    public double getExtensionTarget() {
        return extensionTarget;
    }

    public double getExtension() {
        return extension2.getCurrentPosition() / (double)MAX_EXTENSION;
    }

    public double getExtensionWithOffset() {
        return getExtension() - extOffset;
    }

    public void resetExtension() {
        extOffset = getExtension();
    }
//=================

    @Override
    public void periodic() {
        double angle = getPivotAngle();

        pivotController.setPID(kp, ki, kd);
        double pivotPow =
                pivotController.calculate(angle, pivotTarget) +
                Math.cos(Math.toRadians(getPivotAngle())) * kf;

        setPivotPow(pivotPow);

        if(hasSlideReachedLimit()) resetExtension();
    }

    public boolean hasSlideReachedLimit() {
        return bottomLimit.isPressed();
    }

    public TrapezoidalMotionProfile getMotionProfile(double target) {
        return new TrapezoidalMotionProfile(MAX_VEL, MAX_ACC, getPivotAngle(), target);
    }
}
