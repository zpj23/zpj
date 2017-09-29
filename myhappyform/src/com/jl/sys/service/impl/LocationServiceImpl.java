package com.jl.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jl.sys.dao.LocationDao;
import com.jl.sys.pojo.LocationInfo;
import com.jl.sys.service.LocationService;
@Service("locationService")
public class LocationServiceImpl implements LocationService {
	@Autowired
	private LocationDao locationDao;
	@Override
	public void updateLocation(LocationInfo locationInfo) {
		locationDao.updateLocation(locationInfo);
	}

}
