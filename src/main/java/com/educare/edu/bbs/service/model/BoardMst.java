package com.educare.edu.bbs.service.model;

public class BoardMst {
	private String boardType;
	private String cate;
	private String skinNm;
	private int useThumb;
	private int useFileTypeGb;
	private int permTp;
	private int edtrYn;
	private int fileYn;
	private String tmp01;
	
	public String getBoardType() {
		return boardType;
	}
	public void setBoardType(String boardType) {
		this.boardType = boardType;
	}
	public String getCate() {
		return cate;
	}
	public void setCate(String cate) {
		this.cate = cate;
	}
	
	public String[] getAddCateArr(){
		try {
			return cate.split(",");
		} catch (NullPointerException e) {
			return null;
		}
	}
	public String getSkinNm() {
		return skinNm;
	}
	public void setSkinNm(String skinNm) {
		this.skinNm = skinNm;
	}
	public int getUseThumb() {
		return useThumb;
	}
	public void setUseThumb(int useThumb) {
		this.useThumb = useThumb;
	}
	public int getUseFileTypeGb() {
		return useFileTypeGb;
	}
	public void setUseFileTypeGb(int useFileTypeGb) {
		this.useFileTypeGb = useFileTypeGb;
	}
	public int getPermTp() {
		return permTp;
	}
	public void setPermTp(int permTp) {
		this.permTp = permTp;
	}
	public int getEdtrYn() {
		return edtrYn;
	}
	public void setEdtrYn(int edtrYn) {
		this.edtrYn = edtrYn;
	}
	public int getFileYn() {
		return fileYn;
	}
	public void setFileYn(int fileYn) {
		this.fileYn = fileYn;
	}
	public String getTmp01() {
		return tmp01;
	}
	public void setTmp01(String tmp01) {
		this.tmp01 = tmp01;
	}
}
