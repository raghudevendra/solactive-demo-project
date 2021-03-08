package com.solactive.demo.application.controller;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solactive.demo.application.exception.ResourceNotFoundException;
import com.solactive.demo.application.model.IndexInformation;
import com.solactive.demo.application.model.StatisticsData;
import com.solactive.demo.application.service.FinancialService;
import com.solactive.demo.application.util.Constants;

@RestController
@RequestMapping("/api")
public class FinancialController {

	@Autowired
	FinancialService service;

	@PostMapping("/ticks")
	/*
	 * @ApiOperation(value = "Returns HTTP Status Code")
	 * 
	 * @ApiResponses(value = {
	 * 
	 * @ApiResponse(code = 201, message = "Return 201 if the response is success"),
	 * 
	 * @ApiResponse(code = 204, message =
	 * "Return 204 if tick is older than 60 seconds") })
	 */
	public ResponseEntity<?> createIndexInformation(@Valid @RequestBody IndexInformation indexInformation) {
		long currentTimeinMilli = new Date().getTime();
		long calculatedTime = currentTimeinMilli - indexInformation.getTimestamp();

		if (calculatedTime < Constants.TIME_LIMIT) {
			service.setIndexInformation(indexInformation);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);

	}

	@GetMapping("/statistics")
	/* @ApiOperation(value = "Returns Aggregated Statistics Data") */
	public StatisticsData getStatistics() throws ResourceNotFoundException {
		return service.getIndexInformation();
	}

	@GetMapping("/statistics/{instrumentIdentifier}")
	/*
	 * @ApiOperation(value =
	 * "Returns Aggregated Statistics Data for a Particular Instument Name")
	 */
	public StatisticsData getTicks(@PathVariable String instrumentIdentifier) throws ResourceNotFoundException {
		return service.getIndexInformation(instrumentIdentifier);
	}

}
