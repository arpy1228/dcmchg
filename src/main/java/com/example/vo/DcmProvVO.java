package com.example.vo;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class DcmProvVO {

    private String id;
    private String sn;
    private String imei;
    private String vin;
    private String carBrand;
    private String carModel;
    private String iccid;
    private String status;
    private String regId;
    private String regDt;
    private String updId;
    private String updDt;
    private String useYn;

}
