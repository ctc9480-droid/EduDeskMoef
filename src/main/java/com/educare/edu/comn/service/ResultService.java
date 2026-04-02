package com.educare.edu.comn.service;

import java.util.List;

import com.educare.edu.comn.model.Org;
import com.educare.edu.comn.vo.MovVO;
import com.educare.edu.comn.vo.ResultVO;

public interface ResultService {

	ResultVO getRceptInfo(int eduSeq, String userId,int rceptSeq);

	ResultVO savePayState(int rceptSeq,int eduSeq,String userId, String memoPay, int pgState);

}
