package org.firstinspires.ftc.teamcode.bot.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.bot.subsystems.Intake;

public class SetIntakeDropdown extends CommandBase {

    private final Intake intake;
    private final boolean dropped;

    public SetIntakeDropdown(Intake intake, boolean dropped) {
        this.intake = intake;
        this.dropped = dropped;
    }

    @Override
    public void initialize() {
        intake.setDropdown(dropped);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
