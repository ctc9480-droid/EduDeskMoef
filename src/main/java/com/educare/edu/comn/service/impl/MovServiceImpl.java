package com.educare.edu.comn.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.educare.component.VarComponent;
import com.educare.edu.comn.mapper.LectureMovMapper;
import com.educare.edu.comn.mapper.LectureTimeStdntMapper;
import com.educare.edu.comn.mapper.MovMapper;
import com.educare.edu.comn.model.LectureMov;
import com.educare.edu.comn.model.LectureTimeStdnt;
import com.educare.edu.comn.service.CheckService;
import com.educare.edu.comn.service.MovService;
import com.educare.edu.comn.service.SmartcheckService;
import com.educare.edu.comn.vo.CheckVO;
import com.educare.edu.comn.vo.MovVO;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.education.service.EduService;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.util.FileUtil;
import com.educare.util.XmlBean;

@Service("MovService")
public class MovServiceImpl implements MovService{
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(MovServiceImpl.class.getName());

	@Resource(name="LectureMovMapper")
	private LectureMovMapper lectureMovMapper;
	@Resource(name="LectureTimeStdntMapper")
	private LectureTimeStdntMapper lectureTimeStdntMapper;
	@Resource(name="MovMapper")
	private MovMapper movMapper;
	@Resource(name="SmartcheckService")
	private SmartcheckService smartcheckService;
	@Resource(name="EduService")
	private EduService eduService;
	@Resource(name="CheckService")
	private CheckService checkService;

	@Override
	public int deleteMov(int idx) {
		try {
			LectureMov param = new LectureMov();
			param.setIdx(idx);
			LectureMov info = lectureMovMapper.selectByPk(param);
			if(info==null){
				return 0;
			}
			if(!SessionUserInfoHelper.isSuperAdmin()){
				if(!info.getRegId().equals(SessionUserInfoHelper.getUserId())){
					return 0;//본인이 올린것이 아니다.
				}
			}
			
			String orgCd = info.getOrgCd();
			String fileOrg = info.getFileOrg();
			String fileRename = info.getFileRename();
			String folderName = "mov/"+orgCd+"/";
			//int result = NaverObjectStorage.delete(folderName, fileRename);
			FileUtil.delete(XmlBean.getServerDataRoot()+folderName+ fileRename);
			//if(result==0){
			//	return 0;
			//}
			lectureMovMapper.deleteByPk(param);
			return 1;
		} catch (NullPointerException e) {
			return 0;
		}
	}

	@Override
	public int setHistoryTime(int eduSeq, int timeSeq, String movTime, String movAllTime, String userId) {
		try {
			//오류찾기위해 추가함,230202
			LOG.info("[오류찾기]eduSeq:"+eduSeq+", timeSeq:"+timeSeq+", userId:"+userId+", movTime:"+movTime+", movAllTime:"+movAllTime);
			
			Lecture lctre = eduService.getLctreDetail(eduSeq);
			CheckVO check = checkService.checkEduClose(lctre);
			if(check.getResult()==1){
				return 1;//진도율만 저장하지 않고 정상처리
			}
			
			LectureTimeStdnt param = new LectureTimeStdnt(eduSeq, timeSeq, userId, movTime,movAllTime,-1);
			LectureTimeStdnt info = lectureTimeStdntMapper.selectByPk(param);
			
			if(ObjectUtils.isEmpty(info)){
				double movTime2 = Double.parseDouble(movTime);
				double movAllTime2 = Double.parseDouble(movAllTime);
				param.setMovTime("0");//첫 등록은 무조건 0으로 세팅함 ,이유는 모르겠지만 진도율 100상태로 등록되는 경우가 있는것 같음
				if(movAllTime2!=0 && movTime2<movAllTime2){
					param.setMovTime(movTime);
					param.setMovAllTime(movAllTime);
				}
				lectureTimeStdntMapper.insertByPk(param);
			}else{
				if(info.getMovRe()==1){
					return 2;
				}
				double movTime1 = Double.parseDouble(info.getMovTime());
				double movTime2 = Double.parseDouble(movTime);
				double movAllTime1 = ObjectUtils.isEmpty(info.getMovAllTime())?0:Double.parseDouble(info.getMovAllTime());
				double movAllTime2 = Double.parseDouble(movAllTime);
				//if( movTime1<movTime2 || movAllTime1!=movAllTime2){//동영상 총길이가 상황에 따라 변하는거같다. 진도율에 문제가 일어나는거 같다. 정확한 원인은 파악하지 못함.,220513 다시원복
				if( movTime1<movTime2){//동영상 총길이가 다를때 조건은 왜 넣은건지 파악 안됨. 어차피 동영상 교체되면 진도율 초기화 되니 무조건 최고진도율은 수정안되게 조건 수정함 
					LOG.info("[진도갱신]eduSeq:"+eduSeq+", timeSeq:"+timeSeq+", userId:"+userId+", movTime:"+movTime+", movAllTime:"+movAllTime);
					lectureTimeStdntMapper.updateByPk(param);
				}
				
				//5초남았거나, 초과시 수료 처리
				if(movAllTime1>0){
					if( ( movAllTime1-movTime2)<5 || movAllTime1<movTime2 ){
						param.setMovTime(movAllTime);
						LOG.info("[수료처리]eduSeq:"+eduSeq+", timeSeq:"+timeSeq+", userId:"+userId+", movTime:"+movTime+", movAllTime:"+movAllTime);
						lectureTimeStdntMapper.updateByPk(param);
						ResultVO result = smartcheckService.setAttendByStudent(eduSeq,timeSeq,userId,VarComponent.ATT_ATTEND_CD,"로그내용");
						
						//수료체크
						int result2 = eduService.setCheckPassByMov(eduSeq,userId);
					}
				}
			}
			return 1;
		} catch (NullPointerException e) {
			return 0;
		}
	}
	
	@Override
	public List<MovVO> getStdntProgressList(int eduSeq, int timeSeq) {
		try {
			MovVO param = new MovVO();
			param.setEduSeq(eduSeq);
			param.setTimeSeq(timeSeq);
			List<MovVO> list = movMapper.getStdntProgressList(param);
			return list;
		} catch (NullPointerException e) {
			return null;
		}
	}
	
}
