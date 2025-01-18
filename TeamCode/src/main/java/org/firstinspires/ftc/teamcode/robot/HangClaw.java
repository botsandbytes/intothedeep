package org.firstinspires.ftc.teamcode.robot;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class HangClaw {
    private Servo claw;

    public HangClaw(HardwareMap hardwareMap) {
        claw = hardwareMap.get(Servo.class, "hangClaw");
    }



    // .58 open for hangClaw
    // .75 close for hangClaw
    public class CloseClaw implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            claw.setPosition(0.6);
            packet.put("Closed Claw", "0.6");
            return claw.getPosition() != 0.6;
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
}

