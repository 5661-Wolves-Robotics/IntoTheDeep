package org.firstinspires.ftc.teamcode.bot.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.bot.subsystems.Intake;

public class SetIntakePos extends CommandBase {

    private final Intake intake;
    private final double pos;

    public SetIntakePos(Intake intake, double pos) {
        this.intake = intake;
        this.pos = pos;
    }

    @Override
    public void initialize() {
        intake.setPos(pos);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
