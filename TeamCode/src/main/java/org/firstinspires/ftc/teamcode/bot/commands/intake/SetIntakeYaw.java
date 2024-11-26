package org.firstinspires.ftc.teamcode.bot.commands.intake;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.bot.subsystems.Intake;

import java.util.function.DoubleSupplier;

public class SetIntakeYaw extends CommandBase {

    private final Intake intake;
    private final double yaw;

    public SetIntakeYaw(Intake intake, double yaw) {
        this.intake = intake;
        this.yaw = yaw;

        addRequirements(intake);
    }

    @Override
    public void initialize() {
        intake.setYaw(yaw);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
