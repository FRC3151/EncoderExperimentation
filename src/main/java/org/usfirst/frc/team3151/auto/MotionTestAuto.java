package org.usfirst.frc.team3151.auto;

import com.kauailabs.navx.frc.AHRS;

import org.usfirst.frc.team3151.command.FollowMotionProfileCommand;
import org.usfirst.frc.team3151.command.ResetAhrsCommand;
import org.usfirst.frc.team3151.subsystem.DriveTrain;
import org.usfirst.frc.team3151.util.WaypointUtil;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.CommandGroup;
import jaci.pathfinder.Waypoint;

public final class MotionTestAuto extends CommandGroup {

    public MotionTestAuto(AHRS ahrs, DriveTrain driveTrain) {
        String sequence = Preferences.getInstance().getString("MotionTest", "");

        String[] rawWaypoints = sequence.split(";");
        Waypoint[] parsedWaypoints = new Waypoint[rawWaypoints.length];

        for (int i = 0; i < rawWaypoints.length; i++) {
            String raw = rawWaypoints[i];
            String[] split = raw.split(",");
            double x = Double.parseDouble(split[0]);
            double y = Double.parseDouble(split[1]);
            double angle = 0;

            if (split.length > 2) {
                angle = Double.parseDouble(split[2]);
            }

            parsedWaypoints[i] = WaypointUtil.unitAdjustedWaypoint(x, y, angle);
        }

        addSequential(new ResetAhrsCommand(ahrs));
        addSequential(new FollowMotionProfileCommand(ahrs, driveTrain, parsedWaypoints));
    }

}