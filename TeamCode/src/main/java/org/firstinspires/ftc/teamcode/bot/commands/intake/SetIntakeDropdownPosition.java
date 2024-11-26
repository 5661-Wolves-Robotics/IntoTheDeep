package org.firstinspires.ftc.teamcode.bot.commands.intake;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.bot.subsystems.Intake;

public class SetIntakeDropdownPosition extends CommandBase {

    private final Intake intake;
    private final double pos;

    public SetIntakeDropdownPosition(Intake intake, double pos) {
        this.intake = intake;
        this.pos = pos;

        addRequirements(intake);
    }

    @Override
    public void initialize() {
        intake.setDropdown(pos);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
