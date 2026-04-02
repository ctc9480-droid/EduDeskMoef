package com.educare.component;

import java.io.Serializable;

public class NosProgressVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1141681287503571762L;
	private long allByte;
	private long tranByte;
	private long fileSize;
	
	public void addTranByte(long tranByte){
		this.allByte+=tranByte;
	}
	
	public long getTranByte() {
		return tranByte;
	}

	public void setTranByte(long tranByte) {
		this.tranByte = tranByte;
	}

	public long getAllByte() {
		return allByte;
	}

	public void setAllByte(long allByte) {
		this.allByte = allByte;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	
}
