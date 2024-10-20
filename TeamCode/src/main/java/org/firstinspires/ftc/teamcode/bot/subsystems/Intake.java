package org.firstinspires.ftc.teamcode.bot.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake extends SubsystemBase {

    private final Servo m_yawServo;
    private final Servo m_dropdownServo;
    private final Servo m_leftServo;
    private final Servo m_rightServo;

    public boolean dropped = true;

    public Intake(HardwareMap hardwareMap, String yawServoName, String dropdownServoName, String leftServoName, String rightServoName){
        m_yawServo = hardwareMap.get(Servo.class, yawServoName);
        m_dropdownServo = hardwareMap.get(Servo.class, dropdownServoName);
        m_leftServo = hardwareMap.get(Servo.class, leftServoName);
        m_rightServo = hardwareMap.get(Servo.class, rightServoName);

        m_dropdownServo.setDirection(Servo.Direction.REVERSE);
        m_yawServo.setDirection(Servo.Direction.REVERSE);
        m_rightServo.setDirection(Servo.Direction.REVERSE);
    }

    public void setYaw(double pos) {
        m_yawServo.setPosition(pos);
    }

    public void setDropdown(boolean pos) {
        dropped = pos;
        setDropdownPosition();
    }

    public void setDropdownPosition() {
        m_dropdownServo.setPosition(dropped ? 0.6 : 0);
    }

    public void setPower(double power) {
        m_rightServo.setPosition(power);
        m_leftServo.setPosition(power);
    }

}
