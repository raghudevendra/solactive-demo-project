package com.solactive.demo.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import com.solactive.demo.application.exception.ResourceNotFoundException;
import com.solactive.demo.application.model.IndexInformation;
import com.solactive.demo.application.model.StatisticsData;
import com.solactive.demo.application.serviceimpl.FinancialServiceImpl;
import com.solactive.demo.application.util.Constants;

//@WebMvcTest(FinancialService.class)

public class FinancialServiceTest {

	@Autowired
	private FinancialServiceImpl service;

	@Mock
	private StatisticsData statisticsData;

	@Mock
	private static ConcurrentHashMap<String, ArrayList<IndexInformation>> mockIndexInfoMap;

	private IndexInformation indexInfo;

	private static ConcurrentHashMap<String, ArrayList<IndexInformation>> indexInfoMap;

	@BeforeEach
	void init() {
		indexInfoMap = new ConcurrentHashMap<String, ArrayList<IndexInformation>>();
		indexInfo = new IndexInformation();
		indexInfo.setInstrument("IBM");
		indexInfo.setPrice(100.4);
		indexInfo.setTimestamp(new Date().getTime());

	}

	@Test
	public void setIndexInformation() {
		service = new FinancialServiceImpl();
		service.setIndexInformation(indexInfo);
	}

	/*
	 * @Test public void getIndexInformationTestThrowException() { service = new
	 * FinancialServiceImpl(); ResourceNotFoundException exception =
	 * assertThrows(ResourceNotFoundException.class, () -> {
	 * service.getIndexInformation(); }); assertEquals(Constants.NO_DATA_FOUND,
	 * exception.getMessage());
	 * 
	 * }
	 * 
	 * @Test public void getIndexInformationTest() {
	 * 
	 * service = new FinancialServiceImpl(); StatisticsData statisticsData =
	 * Mockito.mock(StatisticsData.class);
	 * 
	 * doNothing().when(statisticsData).setAvgerage(11111L);
	 * doNothing().when(statisticsData).setCount(1);
	 * 
	 * service.getIndexInformation();
	 * 
	 * }
	 */

}