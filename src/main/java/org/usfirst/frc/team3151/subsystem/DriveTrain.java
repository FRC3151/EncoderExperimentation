package org.usfirst.frc.team3151.subsystem;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.CANSpeedController;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public final class DriveTrain {

    private final CANTalon leftMaster;
    private final CANTalon rightMaster;

    public DriveTrain(CANTalon leftMaster, CANTalon[] leftSlaves, CANTalon rightMaster, CANTalon[] rightSlaves) {
        this.leftMaster = leftMaster;
        this.rightMaster = rightMaster;

        configureSide("Left", leftMaster, leftSlaves);
        configureSide("Right", rightMaster, rightSlaves);
    }

    private void configureSide(String sideName, CANTalon master, CANTalon[] slaves) {
        master.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Relative);
        master.changeControlMode(CANTalon.TalonControlMode.Voltage);
        LiveWindow.addActuator("Drive", sideName + " Master (" + master.getDeviceID() + ")", master);

        for (CANTalon slave : slaves) {
            slave.changeControlMode(CANTalon.TalonControlMode.Follower);
            slave.set(master.getDeviceID());
            LiveWindow.addActuator("Drive", sideName + " Slave (" + slave.getDeviceID() + ")", slave);
        }
    }

    public void tankDrive(double left, double right) {
        // multiply by 12 (we run in voltage mode and use a 12v battery) and invert right side
        leftMaster.set(left * 12.0);
        rightMaster.set(right * -12.0);
    }

    public CANTalon getLeftMaster() {
        return leftMaster;
    }

    public CANTalon getRightMaster() {
        return rightMaster;
    }

}