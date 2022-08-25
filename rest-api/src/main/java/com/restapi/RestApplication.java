package com.restapi;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.restapi.controller.RestController;

@ApplicationPath("/api/*")
public class RestApplication extends Application {
	@Override
	public Set<Class<?>> getClasses(){
		HashSet<Class<?>> set = new HashSet<>();
		
		set.add(RestController.class);
		return set;
	}
}
