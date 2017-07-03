/*
 * Copyright 2015 Alan.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.szyciov.operate.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.alibaba.fastjson.JSON;

/**
 *
 * @author Alan
 */
public class RequestTool {

	private static final Logger LOGGER = Logger.getLogger(RequestTool.class.getName());

	public static String post(String url, Object params) {
		StringBuilder bufferRes = new StringBuilder();
		try {
			URL realUrl = new URL(url);

			HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
			// 连接超时
			conn.setConnectTimeout(25000);
			// 读取超时 --服务器响应比较慢,增大时间
			conn.setReadTimeout(25000);

			HttpURLConnection.setFollowRedirects(true);

			// 请求方式
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:21.0) Gecko/20100101 Firefox/21.0");

			conn.connect();

			// 获取URLConnection对象对应的输出流
			OutputStream out = conn.getOutputStream() ;
			// 发送请求参数
			final String json = JSON.toJSONString(params);
			LOGGER.log(Level.FINE, "request json={0}", json);
			out.write(json.getBytes("utf-8"));
			out.flush();
			out.close();

			InputStream in = conn.getInputStream();
			BufferedReader read = new BufferedReader(new InputStreamReader(in, "UTF-8"));

			String valueString;
			while ((valueString = read.readLine()) != null) {
				bufferRes.append(valueString);
			}

			LOGGER.log(Level.FINE, "request json result={0}", bufferRes.toString());
			in.close();

			// 关闭连接
			conn.disconnect();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "", e);
		}

		return bufferRes.toString();
	}

	public static String get(String url, Object params) {
		StringBuilder bufferRes = new StringBuilder();
		try {
			URL realUrl = new URL(url);

			HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
			// 连接超时
			conn.setConnectTimeout(25000);
			// 读取超时 --服务器响应比较慢,增大时间
			conn.setReadTimeout(25000);

			HttpURLConnection.setFollowRedirects(true);

			// 请求方式
			conn.setRequestMethod("GET");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:21.0) Gecko/20100101 Firefox/21.0");
			conn.setRequestProperty("Referer", "https://api.weixin.qq.com/");

			conn.connect();

			// 获取URLConnection对象对应的输出流
			OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
			// 发送请求参数
			final String json = JSON.toJSONString(params);
			LOGGER.log(Level.FINE, "request json={0}", json);
			out.write(URLEncoder.encode(json, "UTF-8"));
			// out.write(json);
			out.flush();
			out.close();

			InputStream in = conn.getInputStream();
			BufferedReader read = new BufferedReader(new InputStreamReader(in, "UTF-8"));

			String valueString;
			while ((valueString = read.readLine()) != null) {
				bufferRes.append(valueString);
			}

			LOGGER.log(Level.FINE, "request json result={0}", bufferRes.toString());
			in.close();

			// 关闭连接
			conn.disconnect();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "", e);
		}

		return bufferRes.toString();
	}

	public static <T> T request(String url, Object params, Class<T> c) {
		String json = get(url, params);
		return JSON.parseObject(json, c);
	}
}
