package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class InOutMotors {

    public final DcMotor Intake;
    public final DcMotor Outtake1;
    public final DcMotor Outtake2;

    public InOutMotors(HardwareMap.DeviceMapping<DcMotor> dcMotors ){
        Intake = dcMotors.get("Intake");
        Outtake1 = dcMotors.get("Outtake1150");
        Outtake2 = dcMotors.get("Outtake6000");
    }
}
