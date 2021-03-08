package com.solactive.demo.application.service;

import org.springframework.stereotype.Service;

import com.solactive.demo.application.model.IndexInformation;
import com.solactive.demo.application.model.StatisticsData;

@Service
public interface FinancialService {

	public void setIndexInformation(IndexInformation indexInformation);
	
	public StatisticsData getIndexInformation(String instrumentName);
	
	public StatisticsData getIndexInformation();

	
}
