package org.usfirst.frc.team3151;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;

import org.usfirst.frc.team3151.auto.CrossBaselineAuto;
import org.usfirst.frc.team3151.auto.IdleAuto;
import org.usfirst.frc.team3151.auto.MotionTestAuto;
import org.usfirst.frc.team3151.auto.PlaceGearAuto;
import org.usfirst.frc.team3151.subsystem.DriveTrain;
import org.usfirst.frc.team3151.subsystem.Driver;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public final class EncoderExperimentation extends IterativeRobot {

    private AHRS ahrs;
    private Driver driver;
    private DriveTrain driveTrain;
    private Command autoCommand;

    @Override
    public void robotInit() {
        ahrs = new AHRS(SPI.Port.kMXP, (byte) 100); // default is 60Hz, we want 100
        driver = new Driver(new XboxController(0));
        driveTrain = new DriveTrain(
            new CANTalon(1), // left master
            new CANTalon[] { new CANTalon(2) }, // left slaves
            new CANTalon(3), // right master
            new CANTalon[] { new CANTalon(4) } // right slaves
        );

        // normally all these methods are static but for some reason
        // String arrays specifically are not static. :(
        new SmartDashboard().putStringArray("Auto List", new String[] {
            "Idle",
            "Cross Baseline",
            "Motion Test",
            "Place Gear Left",
            "Place Gear Center",
            "Place Gear Right"
        });
    }

    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        driveTrain.tankDrive(driver.driveLeft(), driver.driveRight());
    }

    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void testPeriodic() {
        LiveWindow.run();
    }

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
        if (autoCommand != null) {
            autoCommand.cancel();
        }
    }

    @Override
    public void autonomousInit() {
        autoCommand = createAutoMode(SmartDashboard.getString("Auto Selector", "Idle"));
        autoCommand.start();
    }

    private Command createAutoMode(String selection) {
        switch (selection) {
            case "Cross Baseline": return new CrossBaselineAuto(ahrs, driveTrain);
            case "Motion Test": return new MotionTestAuto(ahrs, driveTrain);
            case "Place Gear Left": return new PlaceGearAuto(ahrs, driveTrain, 1);
            case "Place Gear Center": return new PlaceGearAuto(ahrs, driveTrain, 2);
            case "Place Gear Right": return new PlaceGearAuto(ahrs, driveTrain, 3);
            default: return new IdleAuto();
        }
    }

}