package org.firstinspires.ftc.teamcode.teleop;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.bot.ITDBot;
import org.firstinspires.ftc.teamcode.bot.states.BaseState;
import org.firstinspires.ftc.teamcode.bot.states.DepositState;
import org.firstinspires.ftc.teamcode.bot.states.IntakeState;
import org.firstinspires.ftc.teamcode.bot.subsystems.Intake;
import org.firstinspires.ftc.teamcode.bot.subsystems.PivotSlide;
import org.firstinspires.ftc.teamcode.util.DualInputStateMachine;
import org.firstinspires.ftc.teamcode.util.StateMachine;

@TeleOp
public class TeleOpMain extends LinearOpMode {

    GamepadEx driver, placer;
    ITDBot bot;

    DualInputStateMachine<ITDBot, BaseState> stateMachine;
    BaseState baseState;
    IntakeState intakeState;
    DepositState depositState;

    @Override
    public void runOpMode() {
        bot     = new ITDBot(hardwareMap);

        driver  = new GamepadEx(gamepad1);
        placer  = new GamepadEx(gamepad2);

        stateMachine    = new DualInputStateMachine<>(bot, driver, placer);

        baseState       = new BaseState(stateMachine);
        intakeState     = new IntakeState(stateMachine);
        depositState    = new DepositState(stateMachine);
        stateMachine.set(baseState).schedule();

        driver.getGamepadButton(GamepadKeys.Button.A)
                .whenPressed(new ConditionalCommand(
                        stateMachine.set(baseState),
                        stateMachine.set(intakeState),
                        intakeState::isActive
                ));
        driver.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                .whenPressed(new ConditionalCommand(
                        stateMachine.set(baseState),
                        stateMachine.set(depositState),
                        depositState::isActive
                ));

        telemetry.addData("angle", bot.arm::getAngle);
        telemetry.addData("extension", bot.arm::getExtension);
        telemetry.addData("pivotCurrent", bot.arm::getCurrent);
        telemetry.addData("extensionCurrent", bot.arm::getExtensionCurrent);
        telemetry.addData("currentState", stateMachine::getState);

        waitForStart();

        while(opModeIsActive() && !isStopRequested()) {
            stateMachine.run();
            CommandScheduler.getInstance().run();
            telemetry.update();
        }
    }

}
