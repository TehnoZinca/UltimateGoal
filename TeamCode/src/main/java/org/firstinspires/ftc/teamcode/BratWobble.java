package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
@Disabled
public class BratWobble extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    DcMotor Brat = null;
    Servo ServoWobble = null;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        Brat = hardwareMap.get(DcMotor.class, "Brat");
        ServoWobble = hardwareMap.servo.get("ServoWobble");

        Brat.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();
        runtime.reset();
        int ok = 0, ok1 = 0;
        while (opModeIsActive()) {
            if (gamepad2.dpad_down) {
                if (ok == 0) {
                    while (gamepad2.dpad_down) {
                        Brat.setPower(0.5);
                        if (!gamepad2.dpad_down) {
                            Brat.setPower(0);
                            break;
                        }
                    }
                    ok = 1;
                }

            } else {
                Brat.setPower(0);
                ok = 0;
            }
            if (gamepad2.dpad_up) {
                if (ok1 == 0) {
                    while (gamepad2.dpad_up) {
                        Brat.setPower(-0.5);
                        if (!gamepad2.dpad_up) {
                            Brat.setPower(0);
                            break;
                        }
                    }
                    ok1 = 1;
                } else {
                    Brat.setPower(0);
                    ok1 = 0;
                }
            }
            if(gamepad2.y){
                ServoWobble.setPosition(1);
            }
            if(gamepad2.x){
                ServoWobble.setPosition(0);
            }
        }
        }
    }
