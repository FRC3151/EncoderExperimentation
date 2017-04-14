package org.usfirst.frc.team3151.command;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.command.InstantCommand;

public final class ResetAhrsCommand extends InstantCommand {

    private final AHRS ahrs;

    public ResetAhrsCommand(AHRS ahrs) {
        setRunWhenDisabled(true);
        this.ahrs = ahrs;
    }

    @Override
    public void execute() {
        ahrs.reset();
    }

}