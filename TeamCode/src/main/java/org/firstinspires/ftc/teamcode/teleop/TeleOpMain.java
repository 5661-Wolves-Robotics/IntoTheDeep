package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.bot.ITDBot;
import org.firstinspires.ftc.teamcode.bot.states.BaseState;
import org.firstinspires.ftc.teamcode.bot.states.DepositState;
import org.firstinspires.ftc.teamcode.util.DualInputStateMachine;

import java.util.concurrent.TimeUnit;

@TeleOp
public class TeleOpMain extends LinearOpMode {

    GamepadEx driver, placer;
    ITDBot bot;

    DualInputStateMachine<ITDBot, BaseState> stateMachine;

    ElapsedTime timer = new ElapsedTime();

    @Override
    public void runOpMode() {
        //telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        bot     = new ITDBot(hardwareMap, telemetry);

        driver  = new GamepadEx(gamepad1);
        placer  = new GamepadEx(gamepad2);

        stateMachine    = new DualInputStateMachine<>(bot, driver, placer, telemetry);
        BaseState baseState         = new BaseState(stateMachine);
        DepositState depositState   = new DepositState(stateMachine);
        stateMachine.set(baseState).schedule();

        driver.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                .whenPressed(stateMachine.set(depositState));
        driver.getGamepadButton(GamepadKeys.Button.A)
                .whenPressed(stateMachine.set(baseState));

        telemetry.addData("state", ()->stateMachine.getState().getName());
        telemetry.addData("angle", bot.arm::getAngle);

        waitForStart();

        timer.reset();

        while(opModeIsActive() && !isStopRequested()) {
            stateMachine.run();
            CommandScheduler.getInstance().run();
            telemetry.update();
        }

        CommandScheduler.getInstance().reset();
    }

}
