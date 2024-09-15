package org.firstinspires.ftc.teamcode.bot.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.bot.subsystems.Intake;

import java.util.function.DoubleSupplier;

public class PowerIntake extends CommandBase {

    private final Intake intake;
    private final DoubleSupplier power;

    public PowerIntake(Intake intake, DoubleSupplier power) {
        this.intake = intake;
        this.power = power;

        addRequirements(intake);
    }

    @Override
    public void execute() {
        intake.setPower(power.getAsDouble());
    }
}
