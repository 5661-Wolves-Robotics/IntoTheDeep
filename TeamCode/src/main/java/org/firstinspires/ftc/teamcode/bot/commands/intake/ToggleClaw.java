package org.firstinspires.ftc.teamcode.bot.commands.intake;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.bot.subsystems.Intake;

public class ToggleClaw extends CommandBase {

    private final Intake intake;

    public ToggleClaw(Intake intake) {
        this.intake = intake;
    }

    @Override
    public void initialize() {
        intake.setClawPos(!intake.getClawPos());
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
