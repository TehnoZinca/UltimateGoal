package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Final", group = "Good")
public class FinalTeleOp extends OpMode {

    private static final double PRECISION_MODE_MULTIPLIER = 0.45;
    private static final double MOTOR_SPEED_MULTIPLIER = 0.8;
    private static final double MOTOR_SPEED_STRAFE = 0.6;


    private Servo BratWobble = null;
    private Servo GrabWobble = null;
    private Servo ServoStack = null;
    private Servo ServoIntake = null;

    private InOutMotors inoutMotors = null;

    private WheelMotors wheelMotors = null;

    @Override
    public void init() {
        wheelMotors = new WheelMotors(hardwareMap.dcMotor);
        wheelMotors.setModeAll(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        inoutMotors = new InOutMotors(hardwareMap.dcMotor);

        inoutMotors.Intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }

    @Override
    public void loop() {
        setPowerOuttake(0.75);
        Movement();
        Strafe();

        telemetry.update();
    }

    private void Movement() {
        final double leftX = gamepad1.left_stick_x;
        final double leftY = -gamepad1.left_stick_y;
        final boolean leftb = gamepad2.left_bumper;
        final boolean rightb = gamepad2.right_bumper;


        double pbl = leftY + leftX;
        double ptl = leftY + leftX;
        double ptr = -leftY + leftX;
        double pbr = -leftY + leftX;

        double max = ptl;
        if (max < pbl) max = pbl;
        else if (max < ptr) max = ptr;
        else if (max < pbr) max = pbr;

        if (max > 1) {
            ptl /= max;
            ptr /= max;
            pbl /= max;
            pbr /= max;
        }

        final boolean precisionModeOn = gamepad1.a;

        if (precisionModeOn) {
            ptr *= PRECISION_MODE_MULTIPLIER;
            ptl *= PRECISION_MODE_MULTIPLIER;
            pbr *= PRECISION_MODE_MULTIPLIER;
            pbl *= PRECISION_MODE_MULTIPLIER;
        }

        wheelMotors.TR.setPower(ptr * MOTOR_SPEED_MULTIPLIER);
        wheelMotors.TL.setPower(ptl * MOTOR_SPEED_MULTIPLIER);
        wheelMotors.BR.setPower(pbr * MOTOR_SPEED_MULTIPLIER);
        wheelMotors.BL.setPower(pbl * MOTOR_SPEED_MULTIPLIER);

        while (leftb==true){
            inoutMotors.Intake.setPower(0.7);
            wheelMotors.TR.setPower(ptr * MOTOR_SPEED_MULTIPLIER);
            wheelMotors.TL.setPower(ptl * MOTOR_SPEED_MULTIPLIER);
            wheelMotors.BR.setPower(pbr * MOTOR_SPEED_MULTIPLIER);
            wheelMotors.BL.setPower(pbl * MOTOR_SPEED_MULTIPLIER);
            if (leftb==false) {
                inoutMotors.Intake.setPower(0);
                break;
            }
        }

        while (rightb==true){
            inoutMotors.Intake.setPower(-0.7);
            wheelMotors.TR.setPower(ptr * MOTOR_SPEED_MULTIPLIER);
            wheelMotors.TL.setPower(ptl * MOTOR_SPEED_MULTIPLIER);
            wheelMotors.BR.setPower(pbr * MOTOR_SPEED_MULTIPLIER);
            wheelMotors.BL.setPower(pbl * MOTOR_SPEED_MULTIPLIER);
            if (rightb==false) {
                inoutMotors.Intake.setPower(0);
                break;
            }
        }

        telemetry.addData("Precision Mode On", "%b\n", precisionModeOn);
    }

    private void Strafe() {
        /*final double rightX = gamepad1.right_stick_x;
        final double rightY = -gamepad1.right_stick_y;
        final double wheelsSpeed = Math.hypot(rightY, rightX);
        final double direction = Math.atan2(rightX, rightY) - WheelMotors.PI_4;
        double speed1 = wheelsSpeed * Math.cos(direction) * MOTOR_SPEED_MULTIPLIER;
        double speed2 = wheelsSpeed * Math.sin(direction) * MOTOR_SPEED_MULTIPLIER;*/

        // Strafe Right
        while (gamepad1.right_stick_x > 0) {
            wheelMotors.TR.setPower(MOTOR_SPEED_STRAFE);
            wheelMotors.TL.setPower(MOTOR_SPEED_STRAFE);
            wheelMotors.BR.setPower(-MOTOR_SPEED_STRAFE);
            wheelMotors.BL.setPower(-MOTOR_SPEED_STRAFE);
        }

        // Strafe Left
        while (gamepad1.right_stick_x < 0) {
            wheelMotors.TR.setPower(-MOTOR_SPEED_STRAFE);
            wheelMotors.TL.setPower(-MOTOR_SPEED_STRAFE);
            wheelMotors.BR.setPower(MOTOR_SPEED_STRAFE);
            wheelMotors.BL.setPower(MOTOR_SPEED_STRAFE);
        }
    }

    public void setPowerOuttake(double power){
        inoutMotors.Outtake1.setPower(power);
        inoutMotors.Outtake2.setPower(-power);
    }

}