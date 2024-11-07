package org.firstinspires.ftc.teamcode.bot.subsystems;

import com.acmerobotics.roadrunner.Vector2d;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;
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

                List<Vector2d> corners = getCorners(colResult);
                int cornerNum = corners.size();

                for(int i = 0; i < cornerNum; i++) {
                    Vector2d corner = corners.get(i);
                    Vector2d prevCorner = corners.get(i - 1 < 0 ? cornerNum - 1 : i - 1);
                    Vector2d nextCorner = corners.get((i + 1) % cornerNum);

                    Vector2d currToPrev = prevCorner.minus(corner);
                    Vector2d currToNext = nextCorner.minus(corner);

                    telemetry.addData("corner " + i, currToPrev.dot(currToNext));
                }
            }
        }
    }

    private List<Vector2d> getCorners(LLResultTypes.ColorResult colRes) {
        List<Vector2d> vecCorners = new ArrayList<>();
        for(List<Double> corner : colRes.getTargetCorners()) {
            if(corner.size() != 2) continue;
            vecCorners.add(new Vector2d(corner.get(0), corner.get(1)));
        }
        return vecCorners;
    }
}
