package com.msansar.ReportingAPI.dto.converter;

import com.msansar.ReportingAPI.dto.dto.transaction.AcquirerInfo;
import com.msansar.ReportingAPI.model.Acquirer;

public class AcquirerConverter {
    public static AcquirerInfo convert(Acquirer acquirer, String type) {
        return new AcquirerInfo(acquirer.getId(), acquirer.getName(), acquirer.getCode(), type);
    }
}
