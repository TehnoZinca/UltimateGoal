package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
@Disabled
@TeleOp (name="IntakeTest   ", group="Linear Opmode")
public class IntakeTest extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    DcMotor Intake = null;
    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        Intake = hardwareMap.get(DcMotor.class, "Intake");

        Intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();
        runtime.reset();
        int ok=0, ok1 = 0;
        while (opModeIsActive()) {
            if (gamepad1.right_bumper) {
                if (ok == 0) {
                    while (gamepad1.right_bumper) {
                        Intake.setPower(0.5);
                        if (!gamepad1.right_bumper) {
                            Intake.setPower(0);
                            break;
                        }
                    }
                  ok=1;
                }

            } else {
                Intake.setPower(0);
                ok = 0;
            }
            if (gamepad1.left_bumper) {
                if (ok1 == 0) {
                    while (gamepad1.left_bumper) {
                        Intake.setPower(0.5);
                        if (!gamepad1.right_bumper) {
                            Intake.setPower(0);
                            break;
                        }
                    }
                        ok1 = 1;
                } else {
                    Intake.setPower(0);
                    ok1 = 0;
                }
            }
        }
    }
}

