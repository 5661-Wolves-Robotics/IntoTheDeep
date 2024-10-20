package org.firstinspires.ftc.teamcode.util;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
@Config
public class ServoTester extends LinearOpMode {

    GamepadEx driver;
    public static double dropdown_pos = 0;
    public static double yaw_pos = 0;

    @Override
    public void runOpMode() {
        driver = new GamepadEx(gamepad1);

        Servo yaw = hardwareMap.get(Servo.class, "yaw");
        Servo dropdown = hardwareMap.get(Servo.class, "dropdown");

        waitForStart();

        while(opModeIsActive() && !isStopRequested()) {
            dropdown.setPosition(dropdown_pos);
            yaw.setPosition(yaw_pos);
            CommandScheduler.getInstance().run();
        }
    }

}
