package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import java.util.ArrayList;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

@Disabled
public class HwUtil {

    HardwareMap customMap;

    BNO055IMU imu;
    Orientation angle;



    public final String[] motorNames = {"Tleft_drive","Tright_drive","Bright_drive","Bleft_drive","Intake"};
    //public static final String[] servoNames = {"first_servo","something_that_opens","uhm...servo??"};
    public ArrayList<DcMotor> motor;
//    public ArrayList<Servo> serv;



    public void _init_(HardwareMap referenceToHwMap){
        customMap = referenceToHwMap;
//        setupImu();
        setupMotors();
        //setupServos();
    }
    public void setupMotors(){
        int len = motorNames.length;
//        Add the motors to the List
        for(int i = 0;i < len-1;i++){
//            telemetry.addData("motorName: ",motorNames[0]);
//            motor.add(customMap.dcMotor.get(motorNames[i]));
        }
//        Reset encoders and set zpb
        for (DcMotor current:motor){
            current.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            current.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            current.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
    }

   /* public void setupServos(){
        int len = servoNames.length;
//        Add the servos to the List
        for(int i = 0;i < len;i++){
            serv.add(customMap.servo.get(servoNames[i]));
        }
    }
    */

//    public void setupImu(){
//        BNO055IMU.Parameters params = new BNO055IMU.Parameters();
//        params.angleUnit = BNO055IMU.AngleUnit.DEGREES;
//        params.calibrationDataFile = "BNO055IMUCalibration.json";
//        imu = customMap.get(BNO055IMU.class,"imu");
//        imu.initialize(params);
//
//    }




}