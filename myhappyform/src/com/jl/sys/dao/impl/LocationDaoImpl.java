package com.jl.sys.dao.impl;

import org.springframework.stereotype.Repository;

import com.jl.common.BaseDao;
import com.jl.sys.dao.LocationDao;
import com.jl.sys.pojo.LocationInfo;
@Repository
public class LocationDaoImpl extends BaseDao<LocationInfo> implements LocationDao {

	@Override
	public void updateLocation(LocationInfo l) {
		this.save(l);
	}

}
