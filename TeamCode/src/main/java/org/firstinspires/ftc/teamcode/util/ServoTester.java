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

        Servo intake = hardwareMap.get(Servo.class, "intake");
        Servo dropdown = hardwareMap.get(Servo.class, "dropdown");

        dropdown.setDirection(Servo.Direction.REVERSE);
        intake.setPosition(1);
        dropdown.setPosition(0);

        driver.getGamepadButton(GamepadKeys.Button.A)
                        .whenPressed(() -> lowered = !lowered);

        waitForStart();

        while(opModeIsActive() && !isStopRequested()) {
            if(lowered) {
                dropdown.setPosition(0.6);
                intake.setPosition(1);
            } else {
                dropdown.setPosition(0);
                intake.setPosition(0.5);
            }
            CommandScheduler.getInstance().run();
        }
    }

}
