package com.mtech.assgnproj.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Advisor {
	private Long id;
	private String name;
    private String photoUrl;
    private LocalDateTime bookingTime;
    private Long bookingid;
}
