package org.usfirst.frc.team3151.util;

import org.usfirst.frc.team3151.RobotConstants;

public final class DeadbandUtil {

    // Joysticks (because they exist in the physical world) are
    // imperfect, and, with no input, output "almost 0", not 0.
    // in order to get a more accurate reading, we introduce a deadband,
    // which basically means we ignore input less than X (in this case 0.1, or 10%)
    // then, we rescale the output - otherwise we'd lose our first 10% of control,
    // which often is needed when trying to do precise driving.
    public static double scaleDeadband(double in) {
        if (Math.abs(in) < RobotConstants.JOYSTICK_DEADBAND) {
            return 0;
        } else {
            if (in > 0) {
                in = in - RobotConstants.JOYSTICK_DEADBAND;
            } else {
                in = in + RobotConstants.JOYSTICK_DEADBAND;
            }

            return in / (1 - RobotConstants.JOYSTICK_DEADBAND);
        }
    }

}