package org.firstinspires.ftc.teamcode.robot;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake {
    private Servo arm, claw;

    public Intake(HardwareMap hardwareMap) {
        arm = hardwareMap.get(Servo.class, "intArm");
        claw = hardwareMap.get(Servo.class, "intClaw");
    }



    public class CloseClaw implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            claw.setPosition(0.7);
            packet.put("Closed Claw", "0.7");
            return false;
        }
    }
    public Action closeClaw() {
        return new CloseClaw();
    }

    public class OpenClaw implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            claw.setPosition(0.4);
            packet.put("Open Claw", "0.4");
            return false;
        }
    }
    public Action openClaw() {
        return new OpenClaw();
    }

    public class lowerarm implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            arm.setPosition(1);
            packet.put("Lowered arm", "1");
            return false;
        }
    }
    public Action lowerarm() {
        return new lowerarm();
    }

    public class raisearm implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            arm.setPosition(0);
            packet.put("Raised arm", "0");
            return false;
        }
    }
    public Action raisearm() {
        return new raisearm();
    }
}

