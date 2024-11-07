package org.firstinspires.ftc.teamcode.bot.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake extends SubsystemBase {

    private final Servo m_yawServo;
    private final Servo m_dropdownServo;
    private final Servo m_clawServo;

    private final double NEUTRAL_POS = 0.6;
    private boolean closed = false;

    public Intake(HardwareMap hardwareMap, String yawServoName, String dropdownServoName, String clawServoName){
        m_yawServo = hardwareMap.get(Servo.class, yawServoName);
        m_dropdownServo = hardwareMap.get(Servo.class, dropdownServoName);
        m_clawServo = hardwareMap.get(Servo.class, clawServoName);

        m_yawServo.setDirection(Servo.Direction.REVERSE);
    }

    public void setDropdown(double pos) {
        m_yawServo.setPosition(NEUTRAL_POS - (pos * NEUTRAL_POS));
        m_dropdownServo.setPosition(NEUTRAL_POS - (pos * NEUTRAL_POS));
    }

    public void setYaw(double pos) {
        double DIFF = 1 - NEUTRAL_POS - 0.2;
        m_yawServo.setPosition(NEUTRAL_POS - (pos * DIFF));
        m_dropdownServo.setPosition(NEUTRAL_POS + (pos * DIFF));
    }

    public void setClawPos(boolean newPos) {
        closed = newPos;
        m_clawServo.setPosition(newPos ? 0.43: 0.04);
    }

    public boolean getClawPos() {
        return closed;
    }

}
