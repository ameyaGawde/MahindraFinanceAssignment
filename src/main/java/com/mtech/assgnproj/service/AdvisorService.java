package com.mtech.assgnproj.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.mtech.assgnproj.model.Advisor;

@Service
public class AdvisorService {

	Random random = new Random();
	private List<Advisor> advisors = new ArrayList<>();

    public void addAdvisor(Advisor advisor) {
    	advisor.setId(random.nextLong() & Long.MAX_VALUE % 900 + 100);
        advisors.add(advisor);
    }
    
    public List<Advisor> getAllAdvisors() {
        return advisors;
    }
    
    public Advisor findAdvisorById(Long advisorId) {
        return advisors.stream()
                .filter(advisor -> advisor.getId().equals(advisorId))
                .findFirst()
                .orElse(null);
    }
}
