package com.ternerwill.resourceconnector;

import com.ternerwill.resourceconnector.controller.VideoController;
import com.ternerwill.resourceconnector.service.MovieService;
import com.ternerwill.resourceconnector.service.TvPlayService;
import com.ternerwill.resourceconnector.util.Config;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.File;

import static com.ternerwill.resourceconnector.util.Config.*;

@SpringBootTest
class ResourceConnectorApplicationTests {

	@Autowired
	private TvPlayService tvPlayService;

	@Autowired
	private MovieService movieService;

	@Autowired
	private PlatformTransactionManager manager;

	@Test
	void contextLoads(@Autowired VideoController videoController,@Autowired Config config) {

		System.out.println(Config.getRequestBaseUrl());
		System.out.println(videoController.getVideos());
	}

	@Test
	void test(){
		System.out.println(tvPlayService.getTvPlayByName("老友记"));
	}

}
