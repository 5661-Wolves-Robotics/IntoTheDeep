package org.firstinspires.ftc.teamcode.bot.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.bot.subsystems.Intake;

public class IntakeDropdownToggle extends CommandBase {

    private final Intake intake;

    public IntakeDropdownToggle(Intake intake) {
        this.intake = intake;
    }

    @Override
    public void initialize() {
        intake.setDropdown(!intake.dropped);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
