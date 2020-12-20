package edu.bpm.carbon.service.certification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CertificationService {

    /**
     * 单位是 厘/千克 == 元/吨
     */
    public double CarbonUnitPrice = 40;

    public double getCarbonUnitPrice() {
        return CarbonUnitPrice;
    }
}
