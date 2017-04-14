package org.usfirst.frc.team3151.util;

import org.usfirst.frc.team3151.RobotConstants;

import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;

// https://github.com/JacisNonsense/Pathfinder/wiki/Pathfinder-for-FRC---Java is a HUGE help here
public final class WaypointUtil {

    public static final Trajectory.Config TRAJECTORY_CONFIG = new Trajectory.Config(
        Trajectory.FitMethod.HERMITE_CUBIC, // defines "how" we will try to generate our path. Just leave it as cubic.
        Trajectory.Config.SAMPLES_HIGH, // determines how many points we'll create. higher = more precision but longer generation time
        0.05, // 50ms (0.05s) tick rate (this is how often the roboRIO processes packets)
        RobotConstants.MOTION_PROFILE_MAX_VELOCITY, // unit is m/s, the fastest we want to travel
        RobotConstants.MOTION_PROFILE_MAX_ACCELERATION, // unit is m/s/s, the fastest we want to accelerate
        60.0 // unit is m/s/s/s, basically how fast acceleration changes. Just leave it at 60.
    );

    // all measurements are from the center of rotation
    public static Waypoint unitAdjustedWaypoint(double xInches, double yInches, double rotation) {
        return new Waypoint(xInches * RobotConstants.INCHES_PER_METER, yInches * RobotConstants.INCHES_PER_METER, Math.toRadians(rotation));
    }

}