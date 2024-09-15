package org.firstinspires.ftc.teamcode.bot.states;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.gamepad.GamepadEx;

import org.firstinspires.ftc.teamcode.bot.ITDBot;
import org.firstinspires.ftc.teamcode.bot.subsystems.Intake;
import org.firstinspires.ftc.teamcode.bot.subsystems.PivotSlide;
import org.firstinspires.ftc.teamcode.util.State;
import org.firstinspires.ftc.teamcode.util.StateMachine;

public class BaseState extends State {

    protected final Intake intake;
    protected final PivotSlide arm;

    public BaseState(StateMachine<ITDBot, BaseState> machine, String name) {
        super(machine, name);
        ITDBot bot = machine.bot;
        intake = bot.intake;
        arm = bot.arm;
    }
}
