package org.firstinspires.ftc.teamcode.bot.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake extends SubsystemBase {

    private final Servo m_servo;

    public Intake(HardwareMap hardwareMap, String servoName){
        m_servo = hardwareMap.get(Servo.class, servoName);

        m_servo.setDirection(Servo.Direction.REVERSE);
    }

    public void setPower(double power) {
        m_servo.setPosition(power);
    }

}
