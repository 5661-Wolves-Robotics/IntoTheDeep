package org.firstinspires.ftc.teamcode.util;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class ArmTest extends LinearOpMode {

    GamepadEx driver;

    @Override
    public void runOpMode() {
        driver = new GamepadEx(gamepad1);

        DcMotor extension = hardwareMap.get(DcMotor.class, "extension1");

        extension.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extension.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("pitch", extension::getCurrentPosition);

        waitForStart();

        while(opModeIsActive() && !isStopRequested()) {
            telemetry.update();
            CommandScheduler.getInstance().run();
        }
    }

}
