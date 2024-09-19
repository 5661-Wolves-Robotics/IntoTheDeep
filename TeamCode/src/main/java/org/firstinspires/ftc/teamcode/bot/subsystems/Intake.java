package org.firstinspires.ftc.teamcode.bot.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake extends SubsystemBase {

    private final Servo m_servo;
    private final Servo m_dropdownServo;
    public static final double INTAKING = 0.4;
    public static final double STORED = 0.0;
    public static final double DEPOSITING = 0.7;

    public Intake(HardwareMap hardwareMap, String servoName, String dropdownServoName){
        m_servo = hardwareMap.get(Servo.class, servoName);
        m_dropdownServo = hardwareMap.get(Servo.class, dropdownServoName);

        m_servo.setDirection(Servo.Direction.REVERSE);
    }

    public void setPower(double power) {
        m_servo.setPosition(power);
    }

    public void setPos(double pos) {
        m_dropdownServo.setPosition(pos);
    }

}
