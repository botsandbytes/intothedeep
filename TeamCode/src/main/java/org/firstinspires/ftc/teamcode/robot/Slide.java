package org.firstinspires.ftc.teamcode.robot;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Slide {
    private final DcMotorEx motor;

    public Slide(HardwareMap hardwareMap) {
        motor = hardwareMap.get(DcMotorEx.class, "slideL");
    }

    public class SpinUp implements Action {
        private boolean initialized = false;

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                motor.setPower(0.8);
                initialized = true;
            }

            double vel = motor.getVelocity();
            packet.put("shooterVelocity", vel);
            return vel < 9_000_000.0;
        }
    }

    public Action spinUp() {
        return new SpinUp();
    }
}


