package org.firstinspires.ftc.teamcode.bot.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.bot.subsystems.DualLinearSlide;

public class StopSlide extends CommandBase {

    private final DualLinearSlide m_slide;

    public StopSlide(DualLinearSlide slide){
        m_slide = slide;
    }

    @Override
    public void initialize() {
        m_slide.setTargetPosition(m_slide.getPosition());
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
