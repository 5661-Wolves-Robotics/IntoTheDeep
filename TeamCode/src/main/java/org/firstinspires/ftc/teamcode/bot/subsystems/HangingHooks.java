package org.firstinspires.ftc.teamcode.bot.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class HangingHooks extends SubsystemBase {

    private final Servo servoL, servoR;
    public static final double DEPLOYED_ANGLE = 100.0, STORED_ANGLE = 0.0;

    public HangingHooks(HardwareMap hm) {
        servoL = hm.get(Servo.class, "hookLeft");
        servoR = hm.get(Servo.class, "hookRight");

        servoR.setDirection(Servo.Direction.REVERSE);
    }

    private void setPositions(double pos) {
        servoL.setPosition(pos);
        servoR.setPosition(pos);
    }

    public void setAngle(double angle) {
        setPositions(angle / 300.0);
    }

}
