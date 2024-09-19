package org.firstinspires.ftc.teamcode.util;

import com.arcrobotics.ftclib.command.Robot;
import com.arcrobotics.ftclib.gamepad.GamepadEx;

public class DualInputStateMachine <T extends Robot, B extends State> extends StateMachine<T, B>{

    public final GamepadEx driver1;
    public final GamepadEx driver2;
    public DualInputStateMachine(T bot, GamepadEx driver1, GamepadEx driver2) {
        super(bot);
        this.driver1 = driver1;
        this.driver2 = driver2;
    }
}
