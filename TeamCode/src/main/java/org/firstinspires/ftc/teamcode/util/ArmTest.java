package org.firstinspires.ftc.teamcode.util;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.bot.subsystems.PivotSlide;

@TeleOp
public class ArmTest extends LinearOpMode {

    GamepadEx driver;
    PivotSlide arm;

    @Override
    public void runOpMode() {
        driver = new GamepadEx(gamepad1);

        arm = new PivotSlide(hardwareMap, "pivot", "extension");

        CommandScheduler.getInstance().registerSubsystem(arm);

        waitForStart();

        while(opModeIsActive() && !isStopRequested()) {
            telemetry.addData("angle", arm.getAngle());
            telemetry.addData("extension", arm.getExtension());
            telemetry.addData("pivotCurrent", arm.getCurrent());
            telemetry.addData("extensionCurrent", arm.getExtensionCurrent());
            telemetry.update();
            CommandScheduler.getInstance().run();
        }
    }

}
