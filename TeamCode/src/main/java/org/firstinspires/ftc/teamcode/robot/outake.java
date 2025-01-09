package org.firstinspires.ftc.teamcode.robot;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Outake {
    private Servo claw, arm;

    public Outake(HardwareMap hardwareMap) {
        claw = hardwareMap.get(Servo.class, "outClaw");
        arm = hardwareMap.get(Servo.class, "outArm");
    }

    // .58 open for hangClaw
    // .75 close for hangClaw
    public class CloseClaw implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            claw.setPosition(0.94);
            packet.put("Out Claw Closed", "0.94");
            return false;
        }
    }
    public Action closeClaw() {
        return new CloseClaw();
    }

    public class OpenClaw implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            claw.setPosition(0.8);
            packet.put("Out Claw Open", "0.8");
            return false;
        }
    }
    public Action openClaw() {
        return new OpenClaw();
    }

    public class armlower implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            arm.setPosition(1);
            packet.put("Out Arm lower", "1");
            return false;
        }
    }
    public Action armlower() {
        return new armlower();
    }

    public class armraise implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            arm.setPosition(0.23);
            packet.put("Out Arm raise", "0.23");
            return false;
        }
    }
    public Action armraise() {
        return new armraise();
    }
}

