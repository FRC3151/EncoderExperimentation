package org.usfirst.frc.team3151.subsystem;

import org.usfirst.frc.team3151.util.DeadbandUtil;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;

public final class Driver {

    private final XboxController controller;

    public Driver(XboxController controller) {
        this.controller = controller;
    }

    // Xbox controllers invert Y before giving it to us, so we just flip it again
    // to get 1 = up and -1 = down
    public double driveLeft() {
        return -DeadbandUtil.scaleDeadband(controller.getY(GenericHID.Hand.kLeft));
    }

    public double driveRight() {
        return -DeadbandUtil.scaleDeadband(controller.getY(GenericHID.Hand.kRight));
    }

}