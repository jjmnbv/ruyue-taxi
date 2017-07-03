package com.szyciov.op.param;

public class QueryTrackDetailParam extends BaseParam {
	/**
	 * 行程ID 必须
	 */
	private String trackId;
	/**
	 * 返回精简结果;  1_行程信息;2_行程信息+行程轨迹；)；默认行程信息
	 */
	private Integer returnResult;

	public String getTrackId() {
		return trackId;
	}

	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}

	public Integer getReturnResult() {
		return returnResult;
	}

	public void setReturnResult(Integer returnResult) {
		this.returnResult = returnResult;
	}

}
