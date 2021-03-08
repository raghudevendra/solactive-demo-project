package com.solactive.demo.application.serviceimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solactive.demo.application.exception.ResourceNotFoundException;
import com.solactive.demo.application.model.IndexInformation;
import com.solactive.demo.application.model.StatisticsData;
import com.solactive.demo.application.service.FinancialService;
import com.solactive.demo.application.util.Constants;

@Service
public class FinancialServiceImpl implements FinancialService {

	private static double totalPriceValue = 0;

	private static long count = 0;

	@Autowired
	private StatisticsData overAllStats;

	// Thread safe HashMap
	private static ConcurrentHashMap<String, ArrayList<IndexInformation>> indexInfoMap = new ConcurrentHashMap<String, ArrayList<IndexInformation>>();

	/**
	 * This method used to store the index information for particular Instrument.
	 * which has Instrument name Price and Timestamp
	 * 
	 * @param IndexInformation.
	 * @return Nothing.
	 */
	public void setIndexInformation(IndexInformation indexInformation) {

		if (indexInfoMap.containsKey(indexInformation.getInstrument())) {
			ArrayList<IndexInformation> tmp = indexInfoMap.get(indexInformation.getInstrument());
			tmp.add(indexInformation);
			indexInfoMap.put(indexInformation.getInstrument(), tmp);
		} else {
			ArrayList<IndexInformation> al = new ArrayList<>();
			al.add(indexInformation);
			indexInfoMap.put(indexInformation.getInstrument(), al);
		}
		if (count == 0) {
			overAllStats = new StatisticsData(indexInformation.getPrice(), indexInformation.getPrice(),
					indexInformation.getPrice());

		} else if (overAllStats.getMaximum() < indexInformation.getPrice()) {
			overAllStats.setMaximum(indexInformation.getPrice());
		} else if (overAllStats.getMinimum() > indexInformation.getPrice()) {
			overAllStats.setMinimum(indexInformation.getPrice());
		}
		totalPriceValue = totalPriceValue + indexInformation.getPrice();
		count++;
	}

	/**
	 * This method used to retrieve statistics data for a particular Instrument
	 * 
	 * @param IndexInformation.
	 * @return StatisticsData.
	 */
	public StatisticsData getIndexInformation(String instrumentName) throws ResourceNotFoundException {
		StatisticsData statistics = new StatisticsData();
		ArrayList<IndexInformation> listOfIndexInfo = indexInfoMap.get(instrumentName);

		if (count == 0 || listOfIndexInfo == null) {
			throw new ResourceNotFoundException(Constants.NO_DATA_FOUND_INPUT);
		}
		Iterator<IndexInformation> iteratorOnIndexInfo = listOfIndexInfo.iterator();
		long currentTime = new Date().getTime();
		while (iteratorOnIndexInfo.hasNext()) {
			IndexInformation itrData = (IndexInformation) iteratorOnIndexInfo.next();
			long diffSeconds = currentTime - itrData.getTimestamp();
			if (diffSeconds > Constants.TIME_LIMIT)
				iteratorOnIndexInfo.remove();
		}

		statistics.setCount(listOfIndexInfo.size());
		double maximuForInstrument = listOfIndexInfo.get(0).getPrice();
		double mininimuForInstrument = listOfIndexInfo.get(0).getPrice();
		double totalForInstrument = 0;
		for (IndexInformation indexForEachInstru : listOfIndexInfo) {
			if (indexForEachInstru.getPrice() > maximuForInstrument) {
				maximuForInstrument = indexForEachInstru.getPrice();
			}
			if (indexForEachInstru.getPrice() < mininimuForInstrument) {
				mininimuForInstrument = indexForEachInstru.getPrice();
			}
			totalForInstrument = totalForInstrument + indexForEachInstru.getPrice();
		}
		statistics.setAvgerage(totalForInstrument / listOfIndexInfo.size());
		statistics.setMaximum(maximuForInstrument);
		statistics.setMinimum(mininimuForInstrument);
		return statistics;
	}

	/**
	 * This method used to retrieve aggregated statistics data across all the
	 * Instrument
	 * 
	 * @param no @param.
	 * @return StatisticsData.
	 */
	public StatisticsData getIndexInformation() throws ResourceNotFoundException {
		if (count == 0) {
			throw new ResourceNotFoundException(Constants.NO_DATA_FOUND);
		}
 		overAllStats.setAvgerage(totalPriceValue / count);
		overAllStats.setCount(count);
		return overAllStats;
	}

	public static double getTotalPriceValue() {
		return totalPriceValue;
	}

	public static void setTotalPriceValue(double total) {
		totalPriceValue = total;
	}

	public static long getCount() {
		return count;
	}

	public static void setCount(long countLocal) {
		count = countLocal;
	}

	public static ConcurrentHashMap<String, ArrayList<IndexInformation>> getIndexInfoMap() {
		return indexInfoMap;
	}

	public static void setIndexInfoMap(ConcurrentHashMap<String, ArrayList<IndexInformation>> mapLocal) {
		indexInfoMap = mapLocal;
	}

	public StatisticsData getOverAllStats() {
		return overAllStats;
	}

	public void setOverAllStats(StatisticsData overAllStats) {
		this.overAllStats = overAllStats;
	}

}
