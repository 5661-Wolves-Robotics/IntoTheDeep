package org.firstinspires.ftc.teamcode.util;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class ServoTester extends LinearOpMode {

    GamepadEx driver;
    boolean lowered = false;

    @Override
    public void runOpMode() {
        driver = new GamepadEx(gamepad1);

        Servo servoL = hardwareMap.get(Servo.class, "diffL");
        Servo servoR = hardwareMap.get(Servo.class, "diffR");

        servoL.setDirection(Servo.Direction.REVERSE);
        servoL.setPosition(1);
        servoR.setPosition(1);

        driver.getGamepadButton(GamepadKeys.Button.A)
                        .whenPressed(() -> lowered = !lowered);

        waitForStart();

        while(opModeIsActive() && !isStopRequested()) {
            if(lowered) {
                servoL.setPosition((driver.getRightX() + 1) / 2.0 - 0.03);
                servoR.setPosition((-driver.getRightX() + 1) / 2.0 - 0.03);
            } else {
                servoL.setPosition(1);
                servoR.setPosition(1);
            }
            CommandScheduler.getInstance().run();
        }
    }

}
