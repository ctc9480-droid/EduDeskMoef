package com.educare.edu.bbs.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.educare.edu.bbs.BbsConstant;
import com.educare.edu.bbs.service.BoardAttachService;
import com.educare.edu.bbs.service.model.BoardAttach;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.tmpFile.service.TempFileService;
import com.educare.edu.tmpFile.service.model.TempFile;
import com.educare.util.FileUtil;
import com.educare.util.LncUtil;
import com.educare.util.MaxNumUtil;
import com.educare.util.XmlBean;

/**
 * @Class Name : BoardAttachServiceImpl.java
 * @author SI개발팀 강병주
 * @since 2020. 7. 1.
 * @version 1.0
 * @see
 * @Description 게시판 첨부파일 서비스
 * 
 *              <pre>
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 7. 1.	  	SI개발팀 강병주     		최초생성
 * </pre>
 */
@Service("BoardAttachService")
public class BoardAttachServiceImpl implements BoardAttachService {

	public static final String ATTACH_PATH = XmlBean.getServerContextRoot() + "/upload/board/attach/";

	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(BoardAttachServiceImpl.class);

	@Resource(name = "BoardAttachMapper")
	private BoardAttachMapper boardAttachMapper;

	/** 임시파일 서비스 */
	@Resource(name = "TempFileService")
	private TempFileService tempFileService;

	@Override
	public List<BoardAttach> getBoardAttachList(BoardAttach attachVo) {
		List<BoardAttach> list = boardAttachMapper.selectBoardAttachList(attachVo);

		// 파일 통계 구하기
		int sumFileSize = 0;
		int sumFileCnt = 0;
		for (BoardAttach map : list) {
			sumFileCnt++;
			sumFileSize += map.getFileSize();
		}
		attachVo.setSumFileCnt(sumFileCnt);
		attachVo.setSumFileSize(sumFileSize);

		return list;
	}

	@Override
	public BoardAttach getBoardAttachMap(Integer fileSeq) {
		BoardAttach map = boardAttachMapper.selectBoardAttachMap(fileSeq);
		return map;
	}

	@Override
	public int boardAttachWriteProc(MultipartHttpServletRequest mhsr) {
		
		
		ResultVO result = new ResultVO();
		try {
			int boardIdx = LncUtil.nvlInt(mhsr.getParameter("idx"));
			String boardType = (mhsr.getParameter("boardType"));
			String folderName = BbsConstant.PATH_ATTACH;
			
			Map <String, MultipartFile > paramMap = mhsr.getFileMap ();
			Iterator itr = paramMap.keySet().iterator();
			while (itr.hasNext()) {
				MultipartFile mf = paramMap.get((String) itr.next());
				String orgName = LncUtil.getEncode(mf.getOriginalFilename());
				
				String fileRename = FileUtil.getFileRename(orgName, boardType+"_"+boardIdx+"_");
				//tempFile에 있는 파일을 스토리지에 업로드함
				//int result_nos=NaverObjectStorage.multiPartupload(mf, folderName, orgName, fileRename);
				result = FileUtil.multiPartupload(mf, folderName, orgName, fileRename);
				if (result.getResult()==1) {
					boardAttachMapper.insertBopardAttach(
							new BoardAttach(
									MaxNumUtil.getSequence(MaxNumUtil.FUNC_FILE)
									, boardIdx
									, "01"
									, orgName
									, fileRename
									, mf.getContentType()
									, (int)mf.getSize()
							)
					);
					
				} else {
					return 4;
				}
			}
			result.setResult(1);
		} catch (NullPointerException e) {
			LOG.debug(e.getMessage());
			return 4;
		}
		return result.getResult();
	}

	@Override
	public int boardAttachDeleteProc(int fileSeq) {
		int result = 0;
		try {
			
			//파일정보가져오기
			BoardAttach baMap = boardAttachMapper.selectBoardAttachMap(fileSeq);
			String fileRename = baMap.getFileRename();
			String folderName = BbsConstant.PATH_ATTACH;
			
			//스토리지에서 삭제
			//NaverObjectStorage.delete(folderName, fileRename);
			FileUtil.delete(folderName+ fileRename);
			
			//테이블에서 삭제
			boardAttachMapper.deleteBoardAttach(fileSeq);
			result = 1;
		} catch (NullPointerException e) {
			return 5;
		}
		return result;
	}

}
