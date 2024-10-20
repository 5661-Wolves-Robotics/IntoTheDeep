package org.firstinspires.ftc.teamcode.bot.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.bot.subsystems.Intake;

import java.util.function.DoubleSupplier;

public class IntakeControl extends CommandBase {

    private final Intake intake;
    private final DoubleSupplier yaw;
    private final DoubleSupplier speed;

    public IntakeControl(Intake intake, DoubleSupplier yaw, DoubleSupplier speed) {
        this.intake = intake;
        this.yaw = yaw;
        this.speed = speed;

        addRequirements(intake);
    }

    @Override
    public void execute() {
        intake.setYaw(yaw.getAsDouble());
        intake.setPower(speed.getAsDouble());
    }
}
