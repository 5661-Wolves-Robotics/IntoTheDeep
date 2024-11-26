package org.firstinspires.ftc.teamcode.bot.commands.intake;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.bot.subsystems.Intake;

import java.util.function.DoubleSupplier;

public class IntakeControl extends CommandBase {

    private final Intake intake;
    private final DoubleSupplier yaw;

    public IntakeControl(Intake intake, DoubleSupplier yaw) {
        this.intake = intake;
        this.yaw = yaw;

        addRequirements(intake);
    }

    @Override
    public void execute() {
        intake.setYaw(yaw.getAsDouble());
    }
}
