package org.firstinspires.ftc.teamcode.bot.commands.hanghooks;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.bot.subsystems.HangingHooks;

public class SetHookAngle extends CommandBase {

    private final HangingHooks hooks;
    private final double angle;

    public SetHookAngle(HangingHooks hooks, double angle) {
        this.hooks = hooks;
        this.angle = angle;

        addRequirements(hooks);
    }

    @Override
    public void initialize() {
        hooks.setAngle(angle);
    }
}
