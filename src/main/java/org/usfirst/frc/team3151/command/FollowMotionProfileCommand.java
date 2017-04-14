package org.usfirst.frc.team3151.command;

import com.kauailabs.navx.frc.AHRS;

import org.usfirst.frc.team3151.RobotConstants;
import org.usfirst.frc.team3151.subsystem.DriveTrain;
import org.usfirst.frc.team3151.util.WaypointUtil;

import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

// https://github.com/JacisNonsense/Pathfinder/wiki/Pathfinder-for-FRC---Java is a HUGE help here
public final class FollowMotionProfileCommand extends Command {

    private final AHRS ahrs;
    private final DriveTrain driveTrain;
    private final EncoderFollower left;
    private final EncoderFollower right;

    public FollowMotionProfileCommand(AHRS ahrs, DriveTrain driveTrain, Waypoint... waypoints) {
        Trajectory trajectory = Pathfinder.generate(waypoints, WaypointUtil.TRAJECTORY_CONFIG);
        TankModifier tank = new TankModifier(trajectory).modify(RobotConstants.WHEELBASE_WIDTH);

        this.ahrs = ahrs;
        this.driveTrain = driveTrain;
        this.left = new EncoderFollower(tank.getLeftTrajectory());
        this.right = new EncoderFollower(tank.getRightTrajectory());
    }

    @Override
    public void initialize() {
        left.configureEncoder(driveTrain.getLeftMaster().getEncPosition(), RobotConstants.ENCODER_TICKS_PER_REVOLUTION, RobotConstants.WHEEL_DIAMETER);
        left.configurePIDVA(1, 0, 0, 1 / RobotConstants.MOTION_PROFILE_MAX_VELOCITY, 0);

        right.configureEncoder(driveTrain.getRightMaster().getEncPosition(), RobotConstants.ENCODER_TICKS_PER_REVOLUTION, RobotConstants.WHEEL_DIAMETER);
        right.configurePIDVA(1, 0, 0, 1 / RobotConstants.MOTION_PROFILE_MAX_VELOCITY, 0);
    }

    @Override
    public void execute() {
        double leftOut = left.calculate(driveTrain.getLeftMaster().getEncPosition());
        double rightOut = right.calculate(driveTrain.getRightMaster().getEncPosition());

        double gyroError = ahrs.getAngle() - Math.toDegrees(left.getHeading());
        double correction = 0.03 * Pathfinder.boundHalfDegrees(gyroError);

        driveTrain.tankDrive(leftOut + correction, rightOut - correction);
    }

    @Override
    public void end() {
        driveTrain.tankDrive(0, 0);
    }

    @Override
    public boolean isFinished() {
        return left.isFinished(); // left/right doesn't matter, they have the same number of steps
    }

}