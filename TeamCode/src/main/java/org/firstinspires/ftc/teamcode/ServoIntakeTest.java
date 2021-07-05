package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
@Disabled
@TeleOp(name="ServoIntakeTest", group="LinearOpMode")
public class ServoIntakeTest extends LinearOpMode {
    Servo ServoIntake= null;
    @Override
    public void runOpMode() throws InterruptedException {
        ServoIntake = hardwareMap.servo.get("ServoIntake");
        waitForStart();
        while(opModeIsActive()){
            if(gamepad1.a){
                ServoIntake.setPosition(1);
            }
            if(gamepad1.b){
                ServoIntake.setPosition(0);
            }
        }
    }
}
