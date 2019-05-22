package com.javainuse.model;

import java.util.Date;
import java.util.Map;

public class GenericKubernetesParams {
	private int intValue;
	private String stringValue;
	private boolean boolValue;
	private Date dateValue;
	private float floatValue;
	
	private String cmd;
	private boolean result;
	
	private String workerName;
	Map<String, Object> internalMap;
	
	/**
	 * Set the result only if previous result was true
	 * @param newResult
	 */
	public void setConditionalResult(boolean newResult) {
		if( result )
			result = newResult;
	}
	
	/**
	 * @return the cmd
	 */
	public String getCmd() {
		return cmd;
	}
	/**
	 * @param cmd the cmd to set
	 */
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	
	
	/**
	 * @return the workerName
	 */
	public String getWorkerName() {
		return workerName;
	}
	/**
	 * @return the internalMap
	 */
	public Map<String, Object> getInternalMap() {
		return internalMap;
	}
	/**
	 * @param workerName the workerName to set
	 */
	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}
	/**
	 * @param internalMap the internalMap to set
	 */
	public void setInternalMap(Map<String, Object> internalMap) {
		this.internalMap = internalMap;
	}

	
	/**
	 * @return the result
	 */
	public boolean isResult() {
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(boolean result) {
		this.result = result;
	}
	/**
	 * @return the intValue
	 */
	public int getIntValue() {
		return intValue;
	}
	/**
	 * @return the stringValue
	 */
	public String getStringValue() {
		return stringValue;
	}
	/**
	 * @return the boolValue
	 */
	public boolean isBoolValue() {
		return boolValue;
	}
	/**
	 * @return the dateValue
	 */
	public Date getDateValue() {
		return dateValue;
	}
	/**
	 * @return the floatValue
	 */
	public float getFloatValue() {
		return floatValue;
	}
	/**
	 * @param intValue the intValue to set
	 */
	public void setIntValue(int intValue) {
		this.intValue = intValue;
	}
	/**
	 * @param stringValue the stringValue to set
	 */
	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}
	/**
	 * @param boolValue the boolValue to set
	 */
	public void setBoolValue(boolean boolValue) {
		this.boolValue = boolValue;
	}
	/**
	 * @param dateValue the dateValue to set
	 */
	public void setDateValue(Date dateValue) {
		this.dateValue = dateValue;
	}
	/**
	 * @param floatValue the floatValue to set
	 */
	public void setFloatValue(float floatValue) {
		this.floatValue = floatValue;
	}
	
	public String getValueAsString(String key) {
		if(internalMap.containsKey(key))
			return internalMap.get(key).toString();
		else
			return "";
	}
	public Integer getValueAsInteger(String key)  {
		System.out.println("Looking for value against key:" + key);
		if(internalMap.containsKey(key))
			return Integer.parseInt(internalMap.get(key).toString());
		else
			return Integer.MIN_VALUE;
	}
	
	public boolean doWorkResult() {
		//call the required plugin
		if(getWorkerName().equalsIgnoreCase("success.worker"))
		{
			//using reflection, load the class
			//class must implement the K8sPluginInterface
			//K8sPluginInterface iface = (K8sPluginInterface)Class.forName(workerName);
			//iface.callmethod();
			return true;
		}
		else
			return false;
	}
}
