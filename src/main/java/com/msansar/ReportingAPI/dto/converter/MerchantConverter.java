package com.msansar.ReportingAPI.dto.converter;

import com.msansar.ReportingAPI.dto.dto.transaction.MerchantInfo;
import com.msansar.ReportingAPI.model.Merchant;

public class MerchantConverter {
    public static MerchantInfo convert(Merchant merchant) {
        return new MerchantInfo(merchant.getId(), merchant.getName());
    }
}
