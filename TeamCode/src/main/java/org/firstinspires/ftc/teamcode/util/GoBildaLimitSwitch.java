package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.hardware.ControlSystem;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DigitalChannelController;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.configuration.annotations.DeviceProperties;
import com.qualcomm.robotcore.hardware.configuration.annotations.DigitalIoDeviceType;


@DigitalIoDeviceType
@DeviceProperties(name = "goBilda® Limit Switch",
        xmlTag = "goBildaLimitSwitch",
        description = "@string/rev_touch_sensor_description"
)
public class GoBildaLimitSwitch implements TouchSensor {

    private final DigitalChannelController digitalChannelController;
    private final int physicalPort;

    public GoBildaLimitSwitch(final DigitalChannelController digitalChannelController,
                          final int physicalPort) {
        this.digitalChannelController = digitalChannelController;
        this.physicalPort = physicalPort;
    }

    @Override
    public double getValue() {
        return isPressed() ? 1 : 0;
    }

    @Override
    public boolean isPressed() {
        return !digitalChannelController.getDigitalChannelState(physicalPort);
    }

    @Override
    public Manufacturer getManufacturer() {
        return Manufacturer.ModernRobotics;
    }

    @Override
    public String getDeviceName() {
        return "goBilda® Limit Switch";
    }

    @Override
    public String getConnectionInfo() {
        return digitalChannelController.getConnectionInfo() + "; digital channel " + physicalPort;
    }

    @Override
    public int getVersion() {
        return 1;
    }

    @Override
    public void resetDeviceConfigurationForOpMode() {
        digitalChannelController.setDigitalChannelMode(physicalPort, DigitalChannel.Mode.INPUT);
    }

    @Override
    public void close() {
        // no-op
    }
}
