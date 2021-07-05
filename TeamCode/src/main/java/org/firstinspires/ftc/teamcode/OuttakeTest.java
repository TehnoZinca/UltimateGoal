package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
@Disabled
@TeleOp(name="OuttakeTest", group="Linear Opmode")
public class OuttakeTest extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    DcMotor Outtake6000 = null;
    DcMotor Outtake1150 = null;
    Servo ServoStack = null;
    DcMotor Intake = null;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        ServoStack = hardwareMap.get(Servo.class, "ServoStack");
        Outtake6000 = hardwareMap.get(DcMotor.class, "Outtake6000");
        Outtake1150 = hardwareMap.get(DcMotor.class, "Outtake1150");
        Intake = hardwareMap.get(DcMotor.class, "Intake");

        Outtake6000.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Outtake1150.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        Outtake6000.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();
        runtime.reset();
        int ok=0, ok1=0, ok2=0, ok3=0;
        while (opModeIsActive()) {
            if (gamepad1.right_bumper) {
            if (ok2 == 0) {
                while (gamepad1.right_bumper) {
                    Intake.setPower(0.5);
                    if (!gamepad1.right_bumper) {
                        Intake.setPower(0);
                        break;
                    }
                }
                ok2=1;
            }

        } else {
            Intake.setPower(0);
            ok2 = 0;
        }
            if (gamepad1.left_bumper) {
                if (ok3 == 0) {
                    while (gamepad1.left_bumper) {
                        Intake.setPower(0.5);
                        if (!gamepad1.right_bumper) {
                            Intake.setPower(0);
                            break;
                        }
                    }
                    ok3 = 1;
                } else {
                    Intake.setPower(0);
                    ok3 = 0;
                }
            }
            Outtake6000.setPower(1);
            Outtake1150.setPower(1);
            if (gamepad1.a) {
                if (ok1 == 0) {
                    if (gamepad1.a) {
                        ServoStack.setPosition(1);
                        ok1 = 1;
                    }

                } else {
                    ServoStack.setPosition(0);
                    ok1 = 0;
                }
            }
        }
    }
}
