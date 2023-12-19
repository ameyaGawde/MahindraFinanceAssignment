package com.mtech.assgnproj.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mtech.assgnproj.model.Advisor;
import com.mtech.assgnproj.service.AdvisorService;

@RestController
@RequestMapping("advisor")
public class AdvisorController {

    @Autowired
    AdvisorService advisorService;
    

	@RequestMapping(value = { "/admin/advisor" }, method = { RequestMethod.POST }, produces = {"application/json;charset=utf-8" })
	@ResponseBody
	public ResponseEntity<Void> addAdvisor(@RequestBody Advisor advisor) {
        if (advisor.getName() == null || advisor.getPhotoUrl() == null) {
            return ResponseEntity.badRequest().build();
        }

        advisorService.addAdvisor(advisor);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
	
	@RequestMapping(value = {"/{userId}/advisor"}, method = { RequestMethod.GET }, produces = {"application/json;charset=utf-8" })
	@ResponseBody
	public ResponseEntity<List<Advisor>> getAdvisors(@PathVariable Long userId) {

        List<Advisor> advisors = advisorService.getAllAdvisors();

        return ResponseEntity.ok(advisors);
    }
	
	@RequestMapping(value = { "/{userId}/advisor/{advisorId}" }, method = { RequestMethod.POST }, produces = {"application/json;charset=utf-8" })
	@ResponseBody
	public ResponseEntity<Void> bookCall(@PathVariable Long userId, @PathVariable Long advisorId,
			@RequestBody String inputJsonString) {

		JSONParser jsonParser = new JSONParser();
		try {
			Random random = new Random();

			JSONObject inputJson = (JSONObject) jsonParser.parse(inputJsonString);
			LocalDateTime parsedBookingTime = LocalDateTime.parse((String) inputJson.get("bookingTime"));
			Advisor advisor = advisorService.findAdvisorById(advisorId);
			if (advisor == null) {
				System.out.println("Not found");
				return ResponseEntity.notFound().build();
			}
			advisor.setBookingTime(parsedBookingTime);
			advisor.setBookingid(random.nextLong() & Long.MAX_VALUE % 900 + 100);
		} catch (Exception e) {
		}
		return ResponseEntity.ok().build();
	}
	
	@RequestMapping(value = { "/{userId}/advisor/booking" }, method = { RequestMethod.GET }, produces = {"application/json;charset=utf-8" })
	@ResponseBody
    public ResponseEntity<List<Advisor>> getAllBookedCalls(@PathVariable Long userId) {
        List<Advisor> bookedCalls = new ArrayList<>();
        List<Advisor> advisors = advisorService.getAllAdvisors();
        
        for (Advisor advisor : advisors) {
            if (advisor.getBookingTime() != null) {
            	bookedCalls.add(advisor);
            }
        }

        return ResponseEntity.ok(bookedCalls);
    }

}
