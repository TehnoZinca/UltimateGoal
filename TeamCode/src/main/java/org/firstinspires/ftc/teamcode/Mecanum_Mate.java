package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


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

@TeleOp(name="Mecanum_Mate", group="Linear Opmode")
@Disabled
public class Mecanum_Mate extends LinearOpMode {


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
    //private Servo GrabWobble = null;
    private Servo ServoStack = null;
    double  power   = 1;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        TleftDrive  = hardwareMap.get(DcMotor.class, "Tleft_drive");
        TrightDrive = hardwareMap.get(DcMotor.class, "Tright_drive");
        BleftDrive  = hardwareMap.get(DcMotor.class, "Bleft_drive");
        BrightDrive = hardwareMap.get(DcMotor.class, "Bright_drive");
        Intake = hardwareMap.get(DcMotor.class,"Intake");
        Outtake1 = hardwareMap.get(DcMotor.class,"Outtake1150");
        Outtake2 = hardwareMap.get(DcMotor.class,"Outtake6000");
        BratWobble = hardwareMap.get(Servo.class, "BratWobble");
      //  GrabWobble = hardwareMap.get(Servo.class, "GrabWobble");
        ServoStack = hardwareMap.get(Servo.class,"ServoStack");

        TleftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        TrightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BrightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BleftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        TleftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        BleftDrive.setDirection(DcMotorSimple.Direction.REVERSE);


        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            Outtake1.setPower(0.6);
            Outtake2.setPower(-0.6);

            while (gamepad1.dpad_up) {
                TleftDrive.setPower(power);
                BleftDrive.setPower(power);
                TrightDrive.setPower(power);
                BrightDrive.setPower(power);
                if (gamepad1.left_trigger!=0)
                    power = 0.5;
                if (gamepad1.left_trigger==0)
                    power=1;
                if (!gamepad1.dpad_up) {
                    TleftDrive.setPower(0);
                    BleftDrive.setPower(0);
                    TrightDrive.setPower(0);
                    BrightDrive.setPower(0);
                    power=1;
                    break;
                }
            }


            while (gamepad1.dpad_down) {
                TleftDrive.setPower(-power);
                BleftDrive.setPower(-power);
                TrightDrive.setPower(-power);
                BrightDrive.setPower(-power);
                if (gamepad1.left_trigger!=0)
                    power = 0.5;
                if (gamepad1.left_trigger==0)
                    power=1;
                if (!gamepad1.dpad_down) {
                    TleftDrive.setPower(0);
                    BleftDrive.setPower(0);
                    TrightDrive.setPower(0);
                    BrightDrive.setPower(0);
                    power=1;
                    break;
                }
            }

            while (gamepad1.dpad_left) {
                TleftDrive.setPower(-power);
                BleftDrive.setPower(power);
                TrightDrive.setPower(power);
                BrightDrive.setPower(-power);
                if (gamepad1.left_trigger!=0)
                    power = 0.5;
                if (gamepad1.left_trigger==0)
                    power=1;
                if (!gamepad1.dpad_left) {
                    TleftDrive.setPower(0);
                    BleftDrive.setPower(0);
                    TrightDrive.setPower(0);
                    BrightDrive.setPower(0);
                    power=1;
                    break;
                }
            }

            while (gamepad1.dpad_right) {
                TleftDrive.setPower(power);
                BleftDrive.setPower(-power);
                TrightDrive.setPower(-power);
                BrightDrive.setPower(power);
                if (gamepad1.left_trigger!=0)
                    power = 0.3;
                if (gamepad1.left_trigger==0)
                    power=1;
                if (!gamepad1.dpad_right) {
                    TleftDrive.setPower(0);
                    BleftDrive.setPower(0);
                    TrightDrive.setPower(0);
                    BrightDrive.setPower(0);
                    power=1;
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
                TleftDrive.setPower(-1);
                BleftDrive.setPower(-1);
                TrightDrive.setPower(1);
                BrightDrive.setPower(1);
                if (!gamepad1.right_bumper) {
                    TleftDrive.setPower(0);
                    BleftDrive.setPower(0);
                    TrightDrive.setPower(0);
                    BrightDrive.setPower(0);
                    break;
                }
            }
            while (gamepad1.left_bumper) {
                TleftDrive.setPower(1);
                BleftDrive.setPower(1);
                TrightDrive.setPower(-1);
                BrightDrive.setPower(-1);
                if (!gamepad1.left_bumper) {
                    TleftDrive.setPower(0);
                    BleftDrive.setPower(0);
                    TrightDrive.setPower(0);
                    BrightDrive.setPower(0);
                    break;
                }
            }

            while(gamepad2.right_bumper){
                Intake.setPower(-0.4);
                while (gamepad1.dpad_up) {
                    TleftDrive.setPower(power);
                    BleftDrive.setPower(power);
                    TrightDrive.setPower(power);
                    BrightDrive.setPower(power);
                    if (gamepad1.left_trigger!=0)
                        power = 0.5;
                    if (gamepad1.left_trigger==0)
                        power=1;
                    if (!gamepad1.dpad_up) {
                        TleftDrive.setPower(0);
                        BleftDrive.setPower(0);
                        TrightDrive.setPower(0);
                        BrightDrive.setPower(0);
                        power=1;
                        break;
                    }
                }


                while (gamepad1.dpad_down) {
                    TleftDrive.setPower(-power);
                    BleftDrive.setPower(-power);
                    TrightDrive.setPower(-power);
                    BrightDrive.setPower(-power);
                    if (gamepad1.left_trigger!=0)
                        power = 0.5;
                    if (gamepad1.left_trigger==0)
                        power=1;
                    if (!gamepad1.dpad_down) {
                        TleftDrive.setPower(0);
                        BleftDrive.setPower(0);
                        TrightDrive.setPower(0);
                        BrightDrive.setPower(0);
                        power=1;
                        break;
                    }
                }

                while (gamepad1.dpad_left) {
                    TleftDrive.setPower(-power);
                    BleftDrive.setPower(power);
                    TrightDrive.setPower(power);
                    BrightDrive.setPower(-power);
                    if (gamepad1.left_trigger!=0)
                        power = 0.5;
                    if (gamepad1.left_trigger==0)
                        power=1;
                    if (!gamepad1.dpad_left) {
                        TleftDrive.setPower(0);
                        BleftDrive.setPower(0);
                        TrightDrive.setPower(0);
                        BrightDrive.setPower(0);
                        power=1;
                        break;
                    }
                }

                while (gamepad1.dpad_right) {
                    TleftDrive.setPower(power);
                    BleftDrive.setPower(-power);
                    TrightDrive.setPower(-power);
                    BrightDrive.setPower(power);
                    if (gamepad1.left_trigger!=0)
                        power = 0.3;
                    if (gamepad1.left_trigger==0)
                        power=1;
                    if (!gamepad1.dpad_right) {
                        TleftDrive.setPower(0);
                        BleftDrive.setPower(0);
                        TrightDrive.setPower(0);
                        BrightDrive.setPower(0);
                        power=1;
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
                    TleftDrive.setPower(-0.8);
                    BleftDrive.setPower(-0.8);
                    TrightDrive.setPower(0.8);
                    BrightDrive.setPower(0.8);
                    if (!gamepad1.right_bumper) {
                        TleftDrive.setPower(0);
                        BleftDrive.setPower(0);
                        TrightDrive.setPower(0);
                        BrightDrive.setPower(0);
                        break;
                    }
                }
                while (gamepad1.left_bumper) {
                    TleftDrive.setPower(0.8);
                    BleftDrive.setPower(0.8);
                    TrightDrive.setPower(-0.8);
                    BrightDrive.setPower(-0.8);
                    if (!gamepad1.left_bumper) {
                        TleftDrive.setPower(0);
                        BleftDrive.setPower(0);
                        TrightDrive.setPower(0);
                        BrightDrive.setPower(0);
                        break;
                    }
                }
                if(!gamepad2.left_bumper){
                    Intake.setPower(0);
                    break;
                }
            }
            while(gamepad2.left_bumper){
                Intake.setPower(0.4);
                while (gamepad1.dpad_up) {
                    TleftDrive.setPower(power);
                    BleftDrive.setPower(power);
                    TrightDrive.setPower(power);
                    BrightDrive.setPower(power);
                    if (gamepad1.left_trigger!=0)
                        power = 0.5;
                    if (gamepad1.left_trigger==0)
                        power=1;
                    if (!gamepad1.dpad_up) {
                        TleftDrive.setPower(0);
                        BleftDrive.setPower(0);
                        TrightDrive.setPower(0);
                        BrightDrive.setPower(0);
                        power=1;
                        break;
                    }
                }


                while (gamepad1.dpad_down) {
                    TleftDrive.setPower(-power);
                    BleftDrive.setPower(-power);
                    TrightDrive.setPower(-power);
                    BrightDrive.setPower(-power);
                    if (gamepad1.left_trigger!=0)
                        power = 0.5;
                    if (gamepad1.left_trigger==0)
                        power=1;
                    if (!gamepad1.dpad_down) {
                        TleftDrive.setPower(0);
                        BleftDrive.setPower(0);
                        TrightDrive.setPower(0);
                        BrightDrive.setPower(0);
                        power=1;
                        break;
                    }
                }

                while (gamepad1.dpad_left) {
                    TleftDrive.setPower(-power);
                    BleftDrive.setPower(power);
                    TrightDrive.setPower(power);
                    BrightDrive.setPower(-power);
                    if (gamepad1.left_trigger!=0)
                        power = 0.5;
                    if (gamepad1.left_trigger==0)
                        power=1;
                    if (!gamepad1.dpad_left) {
                        TleftDrive.setPower(0);
                        BleftDrive.setPower(0);
                        TrightDrive.setPower(0);
                        BrightDrive.setPower(0);
                        power=1;
                        break;
                    }
                }

                while (gamepad1.dpad_right) {
                    TleftDrive.setPower(power);
                    BleftDrive.setPower(-power);
                    TrightDrive.setPower(-power);
                    BrightDrive.setPower(power);
                    if (gamepad1.left_trigger!=0)
                        power = 0.3;
                    if (gamepad1.left_trigger==0)
                        power=1;
                    if (!gamepad1.dpad_right) {
                        TleftDrive.setPower(0);
                        BleftDrive.setPower(0);
                        TrightDrive.setPower(0);
                        BrightDrive.setPower(0);
                        power=1;
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
                    TleftDrive.setPower(-1);
                    BleftDrive.setPower(-1);
                    TrightDrive.setPower(1);
                    BrightDrive.setPower(1);
                    if (!gamepad1.right_bumper) {
                        TleftDrive.setPower(0);
                        BleftDrive.setPower(0);
                        TrightDrive.setPower(0);
                        BrightDrive.setPower(0);
                        break;
                    }
                }
                while (gamepad1.left_bumper) {
                    TleftDrive.setPower(1);
                    BleftDrive.setPower(1);
                    TrightDrive.setPower(-1);
                    BrightDrive.setPower(-1);
                    if (!gamepad1.left_bumper) {
                        TleftDrive.setPower(0);
                        BleftDrive.setPower(0);
                        TrightDrive.setPower(0);
                        BrightDrive.setPower(0);
                        break;
                    }
                }
                if(!gamepad2.left_bumper){
                    Intake.setPower(0);
                    break;
                }
            }

            if(gamepad2.a){
                ServoStack.setPosition(0.7);
            }
            if(gamepad2.b){
                ServoStack.setPosition(-0.7);
            }

            if(gamepad2.dpad_right){
                BratWobble.setPosition(0.7);
            }

            if(gamepad2.dpad_left){
                BratWobble.setPosition(-1);
            }

            /*if(gamepad2.dpad_up){
                GrabWobble.setPosition(0.5);
            }

            if(gamepad2.dpad_down){
                GrabWobble.setPosition(-0.5);
            }
            */
            telemetry.update();
        }

    }

}