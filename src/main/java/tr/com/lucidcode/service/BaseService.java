package tr.com.lucidcode.service;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import tr.com.lucidcode.dao.BaseDao;

@Service("baseService")
public class BaseService<T> {
	
	protected static Logger logger = Logger.getLogger("sessionListener");
	
	BaseDao<T> baseDao;

	public BaseService() {
		baseDao = new BaseDao<T>();
	}
	
	public void insert(T obj) {

		baseDao.insert(obj);

	}

	public void delete(T obj) {

		baseDao.delete(obj);

	}

}
