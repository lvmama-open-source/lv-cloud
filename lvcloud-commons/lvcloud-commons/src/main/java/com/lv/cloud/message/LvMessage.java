package com.lv.cloud.message;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 通用消息体(payload)
 * @author xiaoyulin
 *
 */
public class LvMessage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1118236567096207803L;

	private Long objectId;
	private String objectType;
	private String eventType;
	private String addition;	//无特定，可以灵活跟随信息
	private String systemType;
	private String trackNumber;
	private String parentAppName;
	private String distributedContextJson;
	private Map<String, Object> attributes;//其它参数
	private Long createMilTime; // 消息创建时间（毫秒数）
	private String operatorName;
	
	public LvMessage(Long objectId, String objectType, String eventType) {
		this.objectId = objectId;
		this.objectType = objectType;
		this.eventType = eventType;
		attributes = new HashMap<String,Object>();
	}

	public LvMessage(){
		super();
		attributes = new HashMap<String,Object>();
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Long getObjectId() {
		return objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getAddition() {
		return addition;
	}

	public void setAddition(String addition) {
		this.addition = addition;
	}

	public String getSystemType() {
		return systemType;
	}

	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}

	public String getTrackNumber() {
		return trackNumber;
	}

	public void setTrackNumber(String trackNumber) {
		this.trackNumber = trackNumber;
	}

	public String getParentAppName() {
		return parentAppName;
	}

	public void setParentAppName(String parentAppName) {
		this.parentAppName = parentAppName;
	}

	public String getDistributedContextJson() {
		return distributedContextJson;
	}

	public void setDistributedContextJson(String distributedContextJson) {
		this.distributedContextJson = distributedContextJson;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	@Override
	public String toString() {
		return "Message{" +
				"objectId=" + objectId +
				", objectType='" + objectType + '\'' +
				", eventType='" + eventType + '\'' +
				", addition='" + addition + '\'' +
				", systemType='" + systemType + '\'' +
				", trackNumber='" + trackNumber + '\'' +
				", parentAppName='" + parentAppName + '\'' +
				", distributedContextJson='" + distributedContextJson + '\'' +
				'}';
	}
	public void putAttribute(String key, Object value) {
		attributes.put(key, value);
	}

	public Long getCreateMilTime() {
		return createMilTime;
	}

	public void setCreateMilTime(Long createMilTime) {
		this.createMilTime = createMilTime;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

}
