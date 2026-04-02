package com.educare.edu.mobileid.mapper;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.educare.edu.mobileid.model.QrTable;


@Repository("qrTableMapper")
public class QrTableMapper {
	
	private static final Logger logger = LoggerFactory.getLogger(QrTableMapper.class);
	
	@Resource(name="sqlSessionTemplate")
	private SqlSessionTemplate session;
	
	public QrTable selectQrTableByNonce(QrTable paramQrTable) {
		return this.selectQrTableByNonce(paramQrTable.getNonce());
	}
	
	public QrTable selectQrTableByNonce(String nonce) {
		QrTable qrTable = session.selectOne("qrTableMapper.selectQrTableByNonce", nonce);
		return qrTable;
	}
	
	public List<QrTable> selectQrTableAllOrderByQrKeyDesc() {
		List<QrTable> qrTables = session.selectList("qrTableMapper.selectQrTableAllOrderByQrKeyDesc");
		return qrTables;
	}
	
	public void insertQrTable(QrTable paramQrTable) {
		session.insert("qrTableMapper.insertQrTable", paramQrTable);
	}
	
	public void updateQrTable(QrTable paramQrTable) {
		session.insert("qrTableMapper.updateQrTable", paramQrTable);
	}
	
}
