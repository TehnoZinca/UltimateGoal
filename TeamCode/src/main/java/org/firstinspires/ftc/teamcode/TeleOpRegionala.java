package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;


/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="TELEOP REGIO", group="Linear Opmode")

public class TeleOpRegionala extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor TleftDrive = null;
    private DcMotor BleftDrive = null;
    private DcMotor TrightDrive = null;
    private DcMotor BrightDrive = null;
    private DcMotor Intake = null;
    private DcMotor Outtake1 = null;
    private DcMotor Outtake2 = null;
    private Servo BratWobble = null;
    private Servo GrabWobble = null;
    private Servo ServoStack = null;
    private Servo ServoIntake = null;
    double  power   = 1;
    double p=1;
    double powerLat = 0.8;
    BNO055IMU imu;
    Orientation angles;
    @Override
    public void runOpMode() {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        TleftDrive = hardwareMap.get(DcMotor.class, "Tleft_drive");
        TrightDrive = hardwareMap.get(DcMotor.class, "Tright_drive");
        BleftDrive = hardwareMap.get(DcMotor.class, "Bleft_drive");
        BrightDrive = hardwareMap.get(DcMotor.class, "Bright_drive");
        Intake = hardwareMap.get(DcMotor.class, "Intake");
        Outtake1 = hardwareMap.get(DcMotor.class, "Outtake1150");
        Outtake2 = hardwareMap.get(DcMotor.class, "Outtake6000");
        BratWobble = hardwareMap.get(Servo.class, "BratWobble");
        GrabWobble = hardwareMap.get(Servo.class, "GrabWobble");
        ServoStack = hardwareMap.get(Servo.class, "ServoStack");
        ServoIntake = hardwareMap.get(Servo.class, "ServoIntake");



        TleftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        TrightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BrightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BleftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        TleftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        BleftDrive.setDirection(DcMotorSimple.Direction.REVERSE);

        // Wait for the game to start (driver presses PLAY)
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            if(p==1)
            {GrabWobble.setPosition(1);p=2;}
            Outtake1.setPower(0.75);
            Outtake2.setPower(-0.75);

            while (gamepad1.dpad_up) {
                TleftDrive.setPower(power);
                BleftDrive.setPower(power);
                TrightDrive.setPower(power);
                BrightDrive.setPower(power);
                if (gamepad1.left_trigger != 0)
                    power = 0.5;
                if (gamepad1.left_trigger == 0)
                    power = 1;
                if (!gamepad1.dpad_up) {
                    TleftDrive.setPower(0);
                    BleftDrive.setPower(0);
                    TrightDrive.setPower(0);
                    BrightDrive.setPower(0);
                    power = 1;
                    break;
                }
            }


            while (gamepad1.dpad_down) {
                TleftDrive.setPower(-power);
                BleftDrive.setPower(-power);
                TrightDrive.setPower(-power);
                BrightDrive.setPower(-power);
                if (gamepad1.left_trigger != 0)
                    power = 0.5;
                if (gamepad1.left_trigger == 0)
                    power = 1;
                if (!gamepad1.dpad_down) {
                    TleftDrive.setPower(0);
                    BleftDrive.setPower(0);
                    TrightDrive.setPower(0);
                    BrightDrive.setPower(0);
                    power = 1;
                    break;
                }
            }

            while (gamepad1.dpad_left) {
                TleftDrive.setPower(-power);
                BleftDrive.setPower(power);
                TrightDrive.setPower(power);
                BrightDrive.setPower(-power);
                if (gamepad1.left_trigger != 0)
                    power = 0.5;
                if (gamepad1.left_trigger == 0)
                    power = 1;
                if (!gamepad1.dpad_left) {
                    TleftDrive.setPower(0);
                    BleftDrive.setPower(0);
                    TrightDrive.setPower(0);
                    BrightDrive.setPower(0);
                    power = 1;
                    break;
                }
            }

            while (gamepad1.dpad_right) {
                TleftDrive.setPower(power);
                BleftDrive.setPower(-power);
                TrightDrive.setPower(-power);
                BrightDrive.setPower(power);
                if (gamepad1.left_trigger != 0)
                    power = 0.3;
                if (gamepad1.left_trigger == 0)
                    power = 1;
                if (!gamepad1.dpad_right) {
                    TleftDrive.setPower(0);
                    BleftDrive.setPower(0);
                    TrightDrive.setPower(0);
                    BrightDrive.setPower(0);
                    power = 1;
                    break;
                }
            }

            while (gamepad1.y) {
                TleftDrive.setPower(1);
                BrightDrive.setPower(1);
                if (!(gamepad1.y)) {
                    TleftDrive.setPower(0);
                    BrightDrive.setPower(0);
                    break;
                }
            }
            while (gamepad1.a) {
                BleftDrive.setPower(1);
                TrightDrive.setPower(1);
                if (!(gamepad1.a)) {
                    BleftDrive.setPower(0);
                    TrightDrive.setPower(0);
                    break;
                }
            }
            while (gamepad1.x) {
                TleftDrive.setPower(-1);
                BrightDrive.setPower(-1);
                if (!(gamepad1.x)) {
                    TleftDrive.setPower(0);
                    BrightDrive.setPower(0);
                    break;
                }
            }
            while (gamepad1.b) {
                BleftDrive.setPower(-1);
                TrightDrive.setPower(-1);
                if (!(gamepad1.b)) {
                    BleftDrive.setPower(0);
                    TrightDrive.setPower(0);
                    break;
                }
            }
            while (gamepad1.right_bumper) {
                if (gamepad1.left_trigger != 0)
                    power = 0.5;
                if (gamepad1.left_trigger == 0)
                    power = 1;
                TleftDrive.setPower(-powerLat);
                BleftDrive.setPower(-powerLat);
                TrightDrive.setPower(powerLat);
                BrightDrive.setPower(powerLat);
                if (!gamepad1.right_bumper) {
                    TleftDrive.setPower(0);
                    BleftDrive.setPower(0);
                    TrightDrive.setPower(0);
                    BrightDrive.setPower(0);
                    break;
                }
            }
            while (gamepad1.left_bumper) {
                if (gamepad1.left_trigger != 0)
                    power = 0.5;
                if (gamepad1.left_trigger == 0)
                    power = 1;
                TleftDrive.setPower(powerLat);
                BleftDrive.setPower(powerLat);
                TrightDrive.setPower(-powerLat);
                BrightDrive.setPower(-powerLat);
                if (!gamepad1.left_bumper) {
                    TleftDrive.setPower(0);
                    BleftDrive.setPower(0);
                    TrightDrive.setPower(0);
                    BrightDrive.setPower(0);
                    break;
                }
            }

            while (gamepad2.right_bumper) {
                Intake.setPower(-0.7);///5
                if (gamepad1.left_trigger != 0)
                    power = 0.5;
                if (gamepad1.left_trigger == 0)
                    power = 1;
                while (gamepad1.dpad_up) {
                    TleftDrive.setPower(power);
                    BleftDrive.setPower(power);
                    TrightDrive.setPower(power);
                    BrightDrive.setPower(power);
                    if (gamepad1.left_trigger != 0)
                        power = 0.5;
                    if (gamepad1.left_trigger == 0)
                        power = 1;
                    if (!gamepad1.dpad_up) {
                        TleftDrive.setPower(0);
                        BleftDrive.setPower(0);
                        TrightDrive.setPower(0);
                        BrightDrive.setPower(0);
                        power = 1;
                        break;
                    }
                }


                while (gamepad1.dpad_down) {
                    TleftDrive.setPower(-power);
                    BleftDrive.setPower(-power);
                    TrightDrive.setPower(-power);
                    BrightDrive.setPower(-power);
                    if (gamepad1.left_trigger != 0)
                        power = 0.5;
                    if (gamepad1.left_trigger == 0)
                        power = 1;
                    if (!gamepad1.dpad_down) {
                        TleftDrive.setPower(0);
                        BleftDrive.setPower(0);
                        TrightDrive.setPower(0);
                        BrightDrive.setPower(0);
                        power = 1;
                        break;
                    }
                }

                while (gamepad1.dpad_left) {
                    TleftDrive.setPower(-power);
                    BleftDrive.setPower(power);
                    TrightDrive.setPower(power);
                    BrightDrive.setPower(-power);
                    if (gamepad1.left_trigger != 0)
                        power = 0.5;
                    if (gamepad1.left_trigger == 0)
                        power = 1;
                    if (!gamepad1.dpad_left) {
                        TleftDrive.setPower(0);
                        BleftDrive.setPower(0);
                        TrightDrive.setPower(0);
                        BrightDrive.setPower(0);
                        power = 1;
                        break;
                    }
                }

                while (gamepad1.dpad_right) {
                    TleftDrive.setPower(power);
                    BleftDrive.setPower(-power);
                    TrightDrive.setPower(-power);
                    BrightDrive.setPower(power);
                    if (gamepad1.left_trigger != 0)
                        power = 0.3;
                    if (gamepad1.left_trigger == 0)
                        power = 1;
                    if (!gamepad1.dpad_right) {
                        TleftDrive.setPower(0);
                        BleftDrive.setPower(0);
                        TrightDrive.setPower(0);
                        BrightDrive.setPower(0);
                        power = 1;
                        break;
                    }
                }

                while (gamepad1.y) {
                    TleftDrive.setPower(1);
                    BrightDrive.setPower(1);
                    if (!(gamepad1.y)) {
                        TleftDrive.setPower(0);
                        BrightDrive.setPower(0);
                        break;
                    }
                }
                while (gamepad1.a) {
                    BleftDrive.setPower(1);
                    TrightDrive.setPower(1);
                    if (!(gamepad1.a)) {
                        BleftDrive.setPower(0);
                        TrightDrive.setPower(0);
                        break;
                    }
                }
                while (gamepad1.x) {
                    TleftDrive.setPower(-1);
                    BrightDrive.setPower(-1);
                    if (!(gamepad1.x)) {
                        TleftDrive.setPower(0);
                        BrightDrive.setPower(0);
                        break;
                    }
                }
                while (gamepad1.b) {
                    BleftDrive.setPower(-1);
                    TrightDrive.setPower(-1);
                    if (!(gamepad1.b)) {
                        BleftDrive.setPower(0);
                        TrightDrive.setPower(0);
                        break;
                    }
                }
                while (gamepad1.right_bumper) {
                    if (gamepad1.left_trigger != 0)
                        power = 0.5;
                    if (gamepad1.left_trigger == 0)
                        power = 1;
                    TleftDrive.setPower(-power);
                    BleftDrive.setPower(-power);
                    TrightDrive.setPower(power);
                    BrightDrive.setPower(power);
                    if (!gamepad1.right_bumper) {
                        TleftDrive.setPower(0);
                        BleftDrive.setPower(0);
                        TrightDrive.setPower(0);
                        BrightDrive.setPower(0);
                        break;
                    }
                }
                while (gamepad1.left_bumper) {
                    if (gamepad1.left_trigger != 0)
                        power = 0.5;
                    if (gamepad1.left_trigger == 0)
                        power = 1;
                    TleftDrive.setPower(power);
                    BleftDrive.setPower(power);
                    TrightDrive.setPower(-power);
                    BrightDrive.setPower(-power);
                    if (!gamepad1.left_bumper) {
                        TleftDrive.setPower(0);
                        BleftDrive.setPower(0);
                        TrightDrive.setPower(0);
                        BrightDrive.setPower(0);
                        break;
                    }
                }
                if (!gamepad2.left_bumper) {
                    Intake.setPower(0);
                    break;
                }
            }
            while (gamepad2.left_bumper) {
                Intake.setPower(0.65);
                if (gamepad1.left_trigger != 0)
                    power = 0.5;
                if (gamepad1.left_trigger == 0)
                    power = 1;
                while (gamepad1.dpad_up) {
                    TleftDrive.setPower(power);
                    BleftDrive.setPower(power);
                    TrightDrive.setPower(power);
                    BrightDrive.setPower(power);
                    if (gamepad1.left_trigger != 0)
                        power = 0.5;
                    if (gamepad1.left_trigger == 0)
                        power = 1;
                    if (!gamepad1.dpad_up) {
                        TleftDrive.setPower(0);
                        BleftDrive.setPower(0);
                        TrightDrive.setPower(0);
                        BrightDrive.setPower(0);
                        power = 1;
                        break;
                    }
                }


                while (gamepad1.dpad_down) {
                    TleftDrive.setPower(-power);
                    BleftDrive.setPower(-power);
                    TrightDrive.setPower(-power);
                    BrightDrive.setPower(-power);
                    if (gamepad1.left_trigger != 0)
                        power = 0.5;
                    if (gamepad1.left_trigger == 0)
                        power = 1;
                    if (!gamepad1.dpad_down) {
                        TleftDrive.setPower(0);
                        BleftDrive.setPower(0);
                        TrightDrive.setPower(0);
                        BrightDrive.setPower(0);
                        power = 1;
                        break;
                    }
                }

                while (gamepad1.dpad_left) {
                    TleftDrive.setPower(-power);
                    BleftDrive.setPower(power);
                    TrightDrive.setPower(power);
                    BrightDrive.setPower(-power);
                    if (gamepad1.left_trigger != 0)
                        power = 0.5;
                    if (gamepad1.left_trigger == 0)
                        power = 1;
                    if (!gamepad1.dpad_left) {
                        TleftDrive.setPower(0);
                        BleftDrive.setPower(0);
                        TrightDrive.setPower(0);
                        BrightDrive.setPower(0);
                        power = 1;
                        break;
                    }
                }

                while (gamepad1.dpad_right) {
                    TleftDrive.setPower(power);
                    BleftDrive.setPower(-power);
                    TrightDrive.setPower(-power);
                    BrightDrive.setPower(power);
                    if (gamepad1.left_trigger != 0)
                        power = 0.3;
                    if (gamepad1.left_trigger == 0)
                        power = 1;
                    if (!gamepad1.dpad_right) {
                        TleftDrive.setPower(0);
                        BleftDrive.setPower(0);
                        TrightDrive.setPower(0);
                        BrightDrive.setPower(0);
                        power = 1;
                        break;
                    }
                }

                while (gamepad1.y) {
                    TleftDrive.setPower(1);
                    BrightDrive.setPower(1);
                    if (!(gamepad1.y)) {
                        TleftDrive.setPower(0);
                        BrightDrive.setPower(0);
                        break;
                    }
                }
                while (gamepad1.a) {
                    BleftDrive.setPower(1);
                    TrightDrive.setPower(1);
                    if (!(gamepad1.a)) {
                        BleftDrive.setPower(0);
                        TrightDrive.setPower(0);
                        break;
                    }
                }
                while (gamepad1.x) {
                    TleftDrive.setPower(-1);
                    BrightDrive.setPower(-1);
                    if (!(gamepad1.x)) {
                        TleftDrive.setPower(0);
                        BrightDrive.setPower(0);
                        break;
                    }
                }
                while (gamepad1.b) {
                    BleftDrive.setPower(-1);
                    TrightDrive.setPower(-1);
                    if (!(gamepad1.b)) {
                        BleftDrive.setPower(0);
                        TrightDrive.setPower(0);
                        break;
                    }
                }
                while (gamepad1.right_bumper) {
                    if (gamepad1.left_trigger != 0)
                        power = 0.5;
                    if (gamepad1.left_trigger == 0)
                        power = 1;
                    TleftDrive.setPower(-powerLat);
                    BleftDrive.setPower(-powerLat);
                    TrightDrive.setPower(powerLat);
                    BrightDrive.setPower(powerLat);
                    if (!gamepad1.right_bumper) {
                        TleftDrive.setPower(0);
                        BleftDrive.setPower(0);
                        TrightDrive.setPower(0);
                        BrightDrive.setPower(0);
                        break;
                    }
                }
                while (gamepad1.left_bumper) {
                    if (gamepad1.left_trigger != 0)
                        power = 0.5;
                    if (gamepad1.left_trigger == 0)
                        power = 1;
                    TleftDrive.setPower(powerLat);
                    BleftDrive.setPower(powerLat);
                    TrightDrive.setPower(-powerLat);
                    BrightDrive.setPower(-powerLat);
                    if (!gamepad1.left_bumper) {
                        TleftDrive.setPower(0);
                        BleftDrive.setPower(0);
                        TrightDrive.setPower(0);
                        BrightDrive.setPower(0);
                        break;
                    }
                }
                if (!gamepad2.left_bumper) {
                    Intake.setPower(0);
                    break;
                }
            }

            if (gamepad2.a) {
                ServoStack.setPosition(0.45); //0.7
            }
            if (gamepad2.b) {
                ServoStack.setPosition(-0.2);
            }
            if (gamepad2.x) {
                ServoIntake.setPosition(-1);
            }
            if (gamepad2.y) {
                ServoIntake.setPosition(1);
            }

            if (gamepad2.dpad_right) {
                BratWobble.setPosition(0.7);///coborare
            }

            if (gamepad2.dpad_left) {
                BratWobble.setPosition(-1);///ridicare
            }

            if (gamepad2.dpad_up) {
                GrabWobble.setPosition(1);//strangere
            }

            if (gamepad2.dpad_down) {
                GrabWobble.setPosition(-1);///desfacere
            }
            if(gamepad2.back) //mita gamepad1.back
            {
                if(gamepad2.start)
                    break;
                BleftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                BrightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                TrightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                TleftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

                if(gamepad2.start)
                    break;
                Laterala(0.9,1200);
                //trebuie deplasat 74 de cm din est catre vest
                while (opModeIsActive() && !isStopRequested() && TleftDrive.isBusy() && BleftDrive.isBusy() && TrightDrive.isBusy() && BrightDrive.isBusy()) {
                    angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                    telemetry.addData("Heading: ", angles.firstAngle);
                    telemetry.addData("Roll: ", angles.secondAngle);
                    telemetry.addData("Pitch", angles.thirdAngle);
                    telemetry.update();
                }
                TleftDrive.setPower(0);
                BleftDrive.setPower(0);
                TrightDrive.setPower(0);
                BrightDrive.setPower(0);
                sleep(600);

                if(gamepad2.start)
                    break;
                ServoStack.setPosition(0.7);
                sleep(500);
                ServoStack.setPosition(-0.7);
                sleep(500);
                if(gamepad2.start)
                    break;
                Laterala(0.9,600);
                //trebuie deplasat 74 de cm din est catre vest
                while (opModeIsActive() && !isStopRequested() && TleftDrive.isBusy() && BleftDrive.isBusy() && TrightDrive.isBusy() && BrightDrive.isBusy()) {
                    angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                    telemetry.addData("Heading: ", angles.firstAngle);
                    telemetry.addData("Roll: ", angles.secondAngle);
                    telemetry.addData("Pitch", angles.thirdAngle);
                    telemetry.update();
                }
                TleftDrive.setPower(0);
                BleftDrive.setPower(0);
                TrightDrive.setPower(0);
                BrightDrive.setPower(0);
                sleep(600);

                ServoStack.setPosition(0.7);
                sleep(500);
                ServoStack.setPosition(-0.7);
                sleep(500);

                if(gamepad2.start)
                    break;
                Laterala(0.9,600);
                //trebuie deplasat 74 de cm din est catre vest
                while (opModeIsActive() && !isStopRequested() && TleftDrive.isBusy() && BleftDrive.isBusy() && TrightDrive.isBusy() && BrightDrive.isBusy()) {
                    angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                    telemetry.addData("Heading: ", angles.firstAngle);
                    telemetry.addData("Roll: ", angles.secondAngle);
                    telemetry.addData("Pitch", angles.thirdAngle);
                    telemetry.update();
                }
                TleftDrive.setPower(0);
                BleftDrive.setPower(0);
                TrightDrive.setPower(0);
                BrightDrive.setPower(0);
                sleep(200);

                ServoStack.setPosition(0.7);
                sleep(500);
                ServoStack.setPosition(-0.7);
                sleep(500);

                Fata(0.9,3000);
                //trebuie deplasat 74 de cm din est catre vest
                while (opModeIsActive() && !isStopRequested() && TleftDrive.isBusy() && BleftDrive.isBusy() && TrightDrive.isBusy() && BrightDrive.isBusy()) {
                    angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                    telemetry.addData("Heading: ", angles.firstAngle);
                    telemetry.addData("Roll: ", angles.secondAngle);
                    telemetry.addData("Pitch", angles.thirdAngle);
                    telemetry.update();
                }
                TleftDrive.setPower(0);
                BleftDrive.setPower(0);
                TrightDrive.setPower(0);
                BrightDrive.setPower(0);
                sleep(200);
                if(gamepad2.start)
                    break;

                Rotire(1,1300);
                //trebuie deplasat 74 de cm din est catre vest
                while (opModeIsActive() && !isStopRequested() && TleftDrive.isBusy() && BleftDrive.isBusy() && TrightDrive.isBusy() && BrightDrive.isBusy()) {
                    angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                    telemetry.addData("Heading: ", angles.firstAngle);
                    telemetry.addData("Roll: ", angles.secondAngle);
                    telemetry.addData("Pitch", angles.thirdAngle);
                    telemetry.update();
                }
                TleftDrive.setPower(0);
                BleftDrive.setPower(0);
                TrightDrive.setPower(0);
                BrightDrive.setPower(0);
                sleep(200);

                if(gamepad2.start)
                    break;
                Laterala(1,1000);
                //trebuie deplasat 74 de cm din est catre vest
                while (opModeIsActive() && !isStopRequested() && TleftDrive.isBusy() && BleftDrive.isBusy() && TrightDrive.isBusy() && BrightDrive.isBusy()) {
                    angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                    telemetry.addData("Heading: ", angles.firstAngle);
                    telemetry.addData("Roll: ", angles.secondAngle);
                    telemetry.addData("Pitch", angles.thirdAngle);
                    telemetry.update();
                }
                TleftDrive.setPower(0);
                BleftDrive.setPower(0);
                TrightDrive.setPower(0);
                BrightDrive.setPower(0);
                sleep(200);
                if(gamepad2.start)
                    break;
                BratWobble.setPosition(-1);
                sleep(500);
                GrabWobble.setPosition(0.5);
                sleep(500);
                BratWobble.setPosition(0.7);
                sleep(500);

                Laterala(1,-300);
                //trebuie deplasat 74 de cm din est catre vest
                while (opModeIsActive() && !isStopRequested() && TleftDrive.isBusy() && BleftDrive.isBusy() && TrightDrive.isBusy() && BrightDrive.isBusy()) {
                    angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                    telemetry.addData("Heading: ", angles.firstAngle);
                    telemetry.addData("Roll: ", angles.secondAngle);
                    telemetry.addData("Pitch", angles.thirdAngle);
                    telemetry.update();
                }
                TleftDrive.setPower(0);
                BleftDrive.setPower(0);
                TrightDrive.setPower(0);
                BrightDrive.setPower(0);
                sleep(200);


                BleftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                BrightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                TrightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                TleftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            }

            telemetry.update();
        }

    }
    public void Laterala ( double p, int t){
        TleftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BleftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        TrightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BrightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        TleftDrive.setTargetPosition(-t);
        BleftDrive.setTargetPosition(t);
        TrightDrive.setTargetPosition(t);
        BrightDrive.setTargetPosition(-t);

        TleftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BleftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        TrightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BrightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        TleftDrive.setPower(p);
        BleftDrive.setPower(p);
        TrightDrive.setPower(p);
        BrightDrive.setPower(p);
    }
    public void Fata ( double p, int t){
        TleftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BleftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        TrightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BrightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        TleftDrive.setTargetPosition(t);
        BleftDrive.setTargetPosition(t);
        TrightDrive.setTargetPosition(t);
        BrightDrive.setTargetPosition(t);

        TleftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BleftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        TrightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BrightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        TleftDrive.setPower(p);
        BleftDrive.setPower(p);
        TrightDrive.setPower(p);
        BrightDrive.setPower(p);

    }
    public void Rotire ( double p, int t){
        TleftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BleftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        TrightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BrightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        TleftDrive.setTargetPosition(-t);
        BleftDrive.setTargetPosition(-t);
        TrightDrive.setTargetPosition(t);
        BrightDrive.setTargetPosition(t);

        TleftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BleftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        TrightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BrightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        TleftDrive.setPower(p);
        BleftDrive.setPower(p);
        TrightDrive.setPower(p);
        BrightDrive.setPower(p);
    }
}