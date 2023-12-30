package com.driver.trasformer;

import com.driver.EntryDto.WebSeriesEntryDto;
import com.driver.model.WebSeries;

public class WebSeriesTransformer {

    public static WebSeries convertDTOToEntity(WebSeriesEntryDto webSeriesEntryDto){
        WebSeries webSeries = new WebSeries();
        webSeries.setSeriesName(webSeriesEntryDto.getSeriesName());
        webSeries.setAgeLimit(webSeriesEntryDto.getAgeLimit());
        webSeries.setRating(webSeriesEntryDto.getRating());
        webSeries.setSubscriptionType(webSeriesEntryDto.getSubscriptionType());

        return webSeries;
    }
}
