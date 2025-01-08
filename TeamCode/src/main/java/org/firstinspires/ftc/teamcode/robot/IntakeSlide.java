package org.firstinspires.ftc.teamcode.robot;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class IntakeSlide {
    private Servo left, right;

    public IntakeSlide(HardwareMap hardwareMap) {
        left = hardwareMap.get(Servo.class, "intL");
        right = hardwareMap.get(Servo.class, "intR");
        right.setDirection(Servo.Direction.REVERSE);
    }

    // .58 open for hangClaw
    // .75 close for hangClaw
    public class CloseSlide implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            left.setPosition(0);
            right.setPosition(0);
            packet.put("Int Slide Closed", "0");
            return false;
        }
    }
    public Action closeSlide() {
        return new CloseSlide();
    }

    public class FullyExpandSlide implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            left.setPosition(0.55);
            right.setPosition(0.55);
            packet.put("Int Slide Expanded", "0.55");
            return false;
        }
    }
    public Action fullyExpandSlide() {
        return new FullyExpandSlide();
    }

    public class ExpandSlow implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            left.setPosition(left.getPosition()+0.1);
            right.setPosition(right.getPosition()+0.1);
            packet.put("InArm Expand Slow", left.getPosition()+0.1);
            return false;
        }
    }
    public Action expandSlow() {
        return new ExpandSlow();
    }

    public class CloseSlow implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            left.setPosition(left.getPosition()-0.1);
            right.setPosition(right.getPosition()-0.1);
            packet.put("InArm Slow Close", left.getPosition()-0.1);
            return false;
        }
    }
    public Action closeSlow() {
        return new CloseSlow();
    }

    public class SetSlidePosition implements Action {

        private double slidePosition;
        public SetSlidePosition(double position){
            slidePosition = position;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            left.setPosition(slidePosition);
            right.setPosition(slidePosition);
            packet.put("InArm Slow Close", slidePosition);
            return false;
        }
    }
    public Action setSlidePosition(double position) {
        return new SetSlidePosition(position);
    }
}

