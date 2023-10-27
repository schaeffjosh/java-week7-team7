package com.techelevator.services;

import org.springframework.stereotype.Component;

import com.techelevator.model.CatPic;
import org.springframework.web.client.RestTemplate;

@Component
public class RestCatPicService implements CatPicService {

	private static final String API_URL = "https://cat-data.netlify.app/api/pictures/random";
	private static final RestTemplate restTemplate = new RestTemplate();

	@Override
	public CatPic getPic() {
		return restTemplate.getForObject(API_URL, CatPic.class);
	}

}	
