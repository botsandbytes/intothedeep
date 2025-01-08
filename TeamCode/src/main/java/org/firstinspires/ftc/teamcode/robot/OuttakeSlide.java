package org.firstinspires.ftc.teamcode.robot;

import static java.lang.Boolean.FALSE;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class OuttakeSlide {
    private final DcMotorEx motor;

    public OuttakeSlide(HardwareMap hardwareMap) {

        motor = hardwareMap.get(DcMotorEx.class, "slideL");
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public class SpinUp implements Action {
        private boolean initialized = false;

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                motor.setTargetPosition(1400);
                motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                motor.setPower(0.8);
                initialized = true;
            }

            double vel = motor.getPower();
            packet.put("Current power in Spin up", vel);
            return motor.isBusy();
        }
    }

    public class Hang implements Action {
        private boolean initialized = false;

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
//            if (!initialized) {
                motor.setTargetPosition(900);
                motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                motor.setPower(0.8);
//                initialized = true;
//            }

            double vel = motor.getVelocity();
            packet.put("Current Velocity in Hang", vel);
            return motor.isBusy();
        }
    }

    public class drop implements Action {
//        private boolean initialized = false;

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
//            if (!initialized) {
            motor.setTargetPosition(2120);
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motor.setPower(0.8);
//                initialized = true;
//            }

            double vel = motor.getVelocity();
            packet.put("Current Velocity in Hang", vel);
            return motor.isBusy();
        }
    }

    public class SpinDown implements Action {
        private boolean initialized = true;

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {

            motor.setTargetPosition(0);
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motor.setPower(0.8);

//            if (initialized && motor.isBusy() == FALSE) {
//                motor.setPower(0);
//                initialized = false;
//            }

            double power = motor.getPower();
            packet.put("Curent Power in Spin Down", power);
            return  motor.isBusy();
        }
    }

    public class PowerDown implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            motor.setPower(0);
            packet.put("Poert Down", "slide");
            return  FALSE;
        }
    }

    public Action powerDown() {
        return new PowerDown();
    }

    public Action spinUp() {
        return new SpinUp();
    }

    public Action spinDown() {
        return new SpinDown();
    }

    public Action hang() {
        return new Hang();
    }

    public Action drop() {
        return new drop();
    }
}


