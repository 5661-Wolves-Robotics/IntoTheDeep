package org.firstinspires.ftc.teamcode.util;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.Robot;
import com.arcrobotics.ftclib.gamepad.GamepadEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class DualInputStateMachine <T extends Robot, B extends State> extends StateMachine<T, B>{

    public final GamepadEx driver1;
    public final GamepadEx driver2;
    public final Telemetry telemetry;
    public DualInputStateMachine(T bot, GamepadEx driver1, GamepadEx driver2, Telemetry telemetry) {
        super(bot);
        this.driver1 = driver1;
        this.driver2 = driver2;
        this.telemetry = telemetry;
    }
}
