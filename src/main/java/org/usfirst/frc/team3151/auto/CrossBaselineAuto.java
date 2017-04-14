package org.usfirst.frc.team3151.auto;

import com.kauailabs.navx.frc.AHRS;

import org.usfirst.frc.team3151.command.FollowMotionProfileCommand;
import org.usfirst.frc.team3151.command.ResetAhrsCommand;
import org.usfirst.frc.team3151.subsystem.DriveTrain;
import org.usfirst.frc.team3151.util.WaypointUtil;

import edu.wpi.first.wpilibj.command.CommandGroup;

public final class CrossBaselineAuto extends CommandGroup {

    public CrossBaselineAuto(AHRS ahrs, DriveTrain driveTrain) {
        addSequential(new ResetAhrsCommand(ahrs));
        addSequential(new FollowMotionProfileCommand(ahrs, driveTrain,
            WaypointUtil.unitAdjustedWaypoint(0, 200, 0)
        ));
    }

}