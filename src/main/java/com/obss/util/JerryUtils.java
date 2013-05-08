package com.obss.util;

import java.io.IOException;

import jodd.io.NetUtil;
import jodd.jerry.Jerry;

public class JerryUtils {

	public static Jerry getJerry(String url) throws IOException {
		String html = NetUtil.downloadString(url);
		Jerry doc = Jerry.jerry(html);
		return doc;
	}

}
