package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
@Disabled
@TeleOp(name="ServoTest", group="Linear Opmode")
public class ServoTest extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    Servo ServoStack = null;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        ServoStack = hardwareMap.get(Servo.class, "ServoStack");

        waitForStart();
        runtime.reset();
        int ok = 0, ok1 = 0;
        while (opModeIsActive()) {
            if(gamepad1.a){
                ServoStack.setPosition(1);
            }
            if(gamepad1.b){
                ServoStack.setPosition(0);
            }
            }
        }
    }
