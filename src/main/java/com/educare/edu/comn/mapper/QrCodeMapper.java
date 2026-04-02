package com.educare.edu.comn.mapper;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import com.educare.edu.comn.model.QrCode;

@Mapper("QrCodeMapper")
public interface QrCodeMapper {

	QrCode selectByAttend(QrCode param);
	
	QrCode selectByFeedback(QrCode param);
	
	List<QrCode> selectByEdu(QrCode param);

	void updateByPk(QrCode param);

	void insertByPk(QrCode param);

}
