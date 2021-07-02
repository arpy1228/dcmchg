package com.example.file;

import com.example.common.CCSSConst;
import com.example.util.DigitChecker;
import com.example.util.LocalDateTimeUtil;
import com.example.vo.DcmChgProvVO;
import com.example.vo.DcmProvVO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class FileApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(FileApplication.class, args);
    }

    /**
     * trId | I | VIN | oldImei | newImei | newEid (EUICCID) | sendDt
     * 2233330101|I|JTJDJRDZ5M2159616|356086700001059|356086700000812|89033023311240000000012633390682|202106301030
     * */
    @Override
    public void run(String... args) throws Exception {

        String[] valOutKListArr = {
                CCSSConst.DCM_FILED_TRANS_ID,
                CCSSConst.DCM_FILED_OP_TYPE,
                CCSSConst.DCM_FILED_VIN,
                CCSSConst.DCM_FILED_OLD_IMEI,
                CCSSConst.DCM_FILED_NEW_IMEI,
                CCSSConst.DCM_FILED_EUICCID,
                CCSSConst.DCM_FILED_REG_DT
        };

        String filePath = "D:\\2.커넥티드카\\toyota_1\\TOYOTA_DCMCHG_2106301030.cvs";

        List<DcmChgProvVO> dcmChgProvList = new ArrayList<>();

        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
            String fileContent = "";
            while((fileContent = br.readLine()) != null) {

                System.out.println("fileContent : " + fileContent);

                //1. 파일 규격 확인
                if(fileContent == null || fileContent.equals("")) {
                    continue;
                }
                //2. Old esim-dcm 정보 조회 (Query 확인)
                String[] contentSplit = fileContent.split(CCSSConst.SPLIT_PIPE);
                DcmChgProvVO dcmChgProvVO;

                if(contentSplit != null && valOutKListArr.length == contentSplit.length) {

                    dcmChgProvVO = DcmChgProvVO.builder()
                                    .transId(contentSplit[CCSSConst.DCMCHG_TRID_POS])
                                    .opType(contentSplit[CCSSConst.DCMCHG_OPTYPE_POS])
                                    .vin(contentSplit[CCSSConst.DCMCHG_VIN_POS])
                                    .oldImei(contentSplit[CCSSConst.DCMCHG_OLDIMEI_POS])
                                    .newImei(contentSplit[CCSSConst.DCMCHG_NEWIMEI_POS])
                                    .euiccid(contentSplit[CCSSConst.DCMCHG_EUICCID_POS])
                                    .sendImei(DigitChecker.checkImeiDigit(contentSplit[CCSSConst.DCMCHG_NEWIMEI_POS]))
                                    .rsltDt(LocalDateTimeUtil.getDateTimeNow("yyyyMMddHHmmss"))
                                    .build();

                } else {

                    dcmChgProvVO = DcmChgProvVO.builder()
                                    .rawData(fileContent)
                                    .rsltCode(CCSSConst.RSLT_FLAG_FAIL)
                                    .rsltDt(LocalDateTimeUtil.getDateTimeNow("yyyyMMddHHmmss"))
                                    .build();

                }

                dcmChgProvList.add(dcmChgProvVO);

                fileWrite(dcmChgProvList);

                //3. Old - New Vin 정보 확인

                //4. Old - memb 확인

                //5. DCM 등록 요청

                //6. DCMCHG 요청

                //7. 파일 이동

            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("File Not Found");
        } finally {
            System.out.println("finally");
        }

    }

    public static void fileWrite(List<DcmChgProvVO> list) {

        String filePath = "D:\\2.커넥티드카\\toyota_1\\rslt.cvs";

        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(filePath))) {

            StringBuffer sb = new StringBuffer();
            for(DcmChgProvVO dcmChgProvVO : list) {
                sb.append(dcmChgProvVO.getTransId()).append("|");
                sb.append(dcmChgProvVO.getRsltDt()).append("|");
                sb.append("\r\n");
            }

            bw.write(sb.toString());
            //bw.flush();

        } catch (Exception e) {

        }

    }

}
