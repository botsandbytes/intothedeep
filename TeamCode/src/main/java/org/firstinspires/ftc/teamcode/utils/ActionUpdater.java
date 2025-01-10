package org.firstinspires.ftc.teamcode.utils;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;

import java.util.ArrayList;
import java.util.List;

public class ActionUpdater {
    public static List<Action> updateRunningActions(List<Action> runningActions, TelemetryPacket packet) {
        List<Action> newActions = new ArrayList<>();
        for (Action action : runningActions) {
            action.preview(packet.fieldOverlay());
            if (action.run(packet)) {
                newActions.add(action);
            }
        }
        return newActions;
    }
}
