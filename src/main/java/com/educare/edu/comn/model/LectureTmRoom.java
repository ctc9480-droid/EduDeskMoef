package com.educare.edu.comn.model;

/**
 */
public class LectureTmRoom {
	
	private int tmSeq;
	private int priorSeq;
	private int roomSeq;
	
	
	
	public LectureTmRoom() {
		super();
	}
	
	public LectureTmRoom(int tmSeq, int priorSeq, int roomSeq) {
		this.tmSeq = tmSeq;
		this.priorSeq = priorSeq;
		this.roomSeq = roomSeq;
	}

	public int getTmSeq() {
		return tmSeq;
	}
	public void setTmSeq(int tmSeq) {
		this.tmSeq = tmSeq;
	}
	public int getPriorSeq() {
		return priorSeq;
	}
	public void setPriorSeq(int priorSeq) {
		this.priorSeq = priorSeq;
	}
	public int getRoomSeq() {
		return roomSeq;
	}
	public void setRoomSeq(int roomSeq) {
		this.roomSeq = roomSeq;
	}

	
}
