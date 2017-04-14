package org.usfirst.frc.team3151.auto;

import com.kauailabs.navx.frc.AHRS;

import org.usfirst.frc.team3151.command.FollowMotionProfileCommand;
import org.usfirst.frc.team3151.command.ResetAhrsCommand;
import org.usfirst.frc.team3151.subsystem.DriveTrain;
import org.usfirst.frc.team3151.util.WaypointUtil;

import edu.wpi.first.wpilibj.command.CommandGroup;
import jaci.pathfinder.Waypoint;

public final class PlaceGearAuto extends CommandGroup {

    private static final Waypoint[][] PATHS = new Waypoint[][] {
        // Right
        new Waypoint[] {
            WaypointUtil.unitAdjustedWaypoint(10, 120, 60)
        },
        // Center
        new Waypoint[] {
            WaypointUtil.unitAdjustedWaypoint(0, 110, 0)
        },
        // Right
        new Waypoint[] {
            WaypointUtil.unitAdjustedWaypoint(-10, 120, -60)
        }
    };

    public PlaceGearAuto(AHRS ahrs, DriveTrain driveTrain, int station) {
        addSequential(new ResetAhrsCommand(ahrs));
        addSequential(new FollowMotionProfileCommand(ahrs, driveTrain, PATHS[station]));
    }

}