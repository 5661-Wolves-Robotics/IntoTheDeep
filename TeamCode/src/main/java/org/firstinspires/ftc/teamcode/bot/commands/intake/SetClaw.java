package org.firstinspires.ftc.teamcode.bot.commands.intake;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.bot.subsystems.Intake;

public class SetClaw extends CommandBase {

    private final Intake intake;
    private final boolean closed;

    public SetClaw(Intake intake, boolean closed) {
        this.intake = intake;
        this.closed = closed;

        addRequirements(intake);
    }

    @Override
    public void initialize() {
        intake.setClawPos(closed);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
