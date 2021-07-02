package com.example.vo;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class DcmChgProvVO {

    //INPUT
    private String transId;
    private String opType;
    private String vin;
    private String oldImei;
    private String newImei;
    private String euiccid;
    //RSLT
    private String status;
    private String rawData;
    private String rsltFlag;
    private String rsltCode;
    private String rsltDesc;
    private String rsltDt;
    //dsRes
    private String entrNo;
    private String prodNo;
    //membId
    private String membId;
    private String membNo;
    private String membCtn;
    //SEND IMEI
    private String sendImei;
    private String oldIccid;

}
