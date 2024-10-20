package org.firstinspires.ftc.teamcode.bot.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.List;

public class ITDLimelight extends SubsystemBase {

    private final Limelight3A limelight;
    private final Telemetry telemetry;
    private double angle;

    public ITDLimelight(HardwareMap hm, Telemetry telemetry) {
        limelight = hm.get(Limelight3A.class, "limelight");
        this.telemetry = telemetry;

        limelight.pipelineSwitch(0);
        limelight.start();
    }

    public LLStatus getStatus() {
        return limelight.getStatus();
    }

    @Override
    public void periodic() {
        LLResult res = limelight.getLatestResult();
        if(res != null && res.isValid()) {
            List<LLResultTypes.ColorResult> colResults = res.getColorResults();
            for(LLResultTypes.ColorResult colResult : colResults) {
                telemetry.addData("colResult Angle", "X: %.2f Y: %.2f", colResult.getTargetXDegrees(), colResult.getTargetYDegrees());
            }
        }
    }
}
