package com.educare.util;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.tmpFile.service.TempFileService;

/**
 * @Class Name : FileUtil.java
 * @author SI개발팀 박용주
 * @since 2019. 11. 26.
 * @version 1.0
 * @see java.nio.channels.FileChannel
 * @see java.io.BufferedWriter
 * @see java.io.BufferedReader
 * @see java.io.FilenameFilter
 * @see java.net.URLEncoder
 * @Description 파일 관련 Utility Class
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2019. 11. 26.	SI개발팀 박용주     		최초생성 
 * </pre>
 */

public class FileUtil {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(FileUtil.class.getName());
	
	/**
	 * 입력되는 경로는 반드시 소스 root 경로를 포함해야함.
	 * @param pathVar 파일의 경로 스트링
	 * @return  pathVar가 CONTEXT_ROOT이면 true, 아니면 false
	 */
	private static boolean rootDirCheck( String pathVar ){
		String path = pathVar;
		if( path != null && 
				!"".equals(path) ){
			String rootPath = XmlBean.getServerContextRoot();
			if(rootPath != null){
				rootPath = rootPath.replace("/", "\\");
				path = path.replace("/", "\\");
				
				if(path.indexOf( rootPath ) != -1 ){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 파일을 복사한다.
	 * @param orgPath 복사할 원본 전체경로 + 파일명
	 * @param copyPath 복사될 전체경로 + 파일명
	 * @return 복사 성공 : true, 실패 : false
	 */
	public static boolean copy(String orgPath, String copyPath){
		return copy(orgPath, copyPath, false);
	}
	
	/**
	 * 파일을 복사한다.
	 * @param orgPath 원본 전체경로 + 파일명
	 * @param copyPath 복사 전체경로 + 파일명
	 * @param isOverwrite 덮어쓸지 여부(false : 새파일명으로 복사)
	 * @return 복사 성공 : true, 실패 : false
	 */
	public static boolean copy(String orgPath, String copyPath, boolean isOverwrite){
		
		//if( !rootDirCheck( orgPath ) || !rootDirCheck( copyPath ) ){
		//	return false;
		//}
		
		boolean isCheck = false;
		FileInputStream fis = null;
		FileOutputStream fos = null;
		FileChannel input = null;
		FileChannel output = null;
		
		String srcFile= LncUtil.filePathReplaceAll(orgPath);
		String destFile= LncUtil.filePathReplaceAll(copyPath);
		
		String dir = destFile.substring( 0, destFile.lastIndexOf( File.separator )+1 );
		if( !exists( dir ) ){
			makeDirectorys( dir );
		}
		
		try {
			//원본 검사
			File org = new File(srcFile);
			if(!org.exists()){
				return false;
			}
			
			File ff = new File(destFile);
			if(ff.exists()){
				if(!isOverwrite){
					//이름 변경
					destFile = fileNameCheck(destFile);
				}else{
					if(ff.exists()){
						//폴더 삭제
						//ff.delete();
						fileDelete(ff);
					}
				}
			}
			
			fis = new FileInputStream(srcFile);
			fos = new FileOutputStream(destFile);
			
			input = fis.getChannel();
			output = fos.getChannel();
			output.transferFrom(input, 0, input.size());
			isCheck = true;
		} catch (IOException e) {
			isCheck = false;
		} finally {
			if(output != null){try{output.close();}catch(IOException e){LOG.error("ERROR:",e);}}
			if(input != null){try{input.close();}catch(IOException e){LOG.error("ERROR:",e);}}
			if(fos != null){try{fos.close();}catch(IOException e){LOG.error("ERROR:",e);}}
			if(fis != null){try{fis.close();}catch(IOException e){LOG.error("ERROR:",e);}}
		}
		
		return isCheck;
	}
	
	/**
	 * 파일 존재여부를 확인한다.
	 * @param filePath 파일 전체경로
	 * @return 존재 : true, 미존재 : false
	 */
	public static boolean exists(String filePathVar){
		String filePath = filePathVar;
		if( !rootDirCheck( filePath ) ){
    		return false;
    	}
		
		filePath = LncUtil.filePathReplaceAll(filePath);
		File ff = new File(filePath);
		return ff.exists();
	}
	
	/**
	 * 디렉토리들을 생성한다.
	 * @param pathStr 생성할 디렉토리 경로
	 * @return 생성 성공 : true, 실패 : false
	 */
	public static boolean makeDirectorys(String pathStr){
		
		if (pathStr == null || "".equals(pathStr)) {
			return false;
		}
		
		//ap루트경로체크 할필요 없음,211028
		//if( !rootDirCheck( pathStr ) ){
    	//	return false;
    	//}
		
		String path= LncUtil.filePathReplaceAll(pathStr);
		
		File dFile = new File(path);
		
		if (dFile.exists()) {
			if (dFile.isDirectory()) {
				return true;
			} else {
				return false;
			}
		} else {
			dFile.mkdirs();
		}
		return true;
	}
	
	/**
	 * 동일한 파일명 존재여부를 확인한다.
	 * @param filePath 파일 전체경로 + 디렉토리명
	 * @return 동일 파일명 존재 : rename, 미존재 : dir
	 */
	public static String fileNameCheck( String filePath ){
		return fileNameDupRename( filePath, 0 );
	}
	
	/**
	 * 동일한 파일명 존재여부를 확인한다.
	 * @param filePathVar 파일 전체경로 + 디렉토리명
	 * @param numVar 동일 파일명 갯수
	 * @return 동일 파일명 존재 : rename, 미존재 : dir
	 */
	private static String fileNameDupRename( String filePathVar , int numVar ){
		String filePath = filePathVar;
		int num = numVar;
		//if( !rootDirCheck( filePath ) ){
    	//	return null;
    	//}
		
		filePath = LncUtil.filePathReplaceAll(filePath);
		
		String newFileName = "";
		File ff = new File(filePath);
		if( ff.exists() && ff.isFile() ){
			filePath = filePath.replaceAll("\\(+[0-9]+\\)", "");
			num++;
			/*
			String fileType = "";
			if( filePath.lastIndexOf(".") !=-1 ){
				fileType = filePath.substring( filePath.lastIndexOf(".") );
				filePath = filePath.substring(0, filePath.lastIndexOf("."));
			}
			filePath = filePath+"("+i+")" + fileType;
			*/
			
			String path = filePath.substring( 0, filePath.lastIndexOf(File.separator)+1);
			String fileName = filePath.substring( filePath.lastIndexOf(File.separator)+1);
			
			String fileType = "";
			if(fileName.lastIndexOf(".") != -1){
				fileType = fileName.substring(fileName.lastIndexOf("."));
				fileName = fileName.substring(0, fileName.lastIndexOf("."));
			}
			
			filePath = path + fileName+"("+num+")" + fileType;
			//System.out.println("filePath : " + filePath);
			newFileName = fileNameDupRename(filePath, num);
		}else{
			newFileName = filePath;
		}
		return newFileName;
	}
	
	/**
	 * 파일 다운로드 : 파일만 다운로드 가능함.
	 * @param filePathVar 다운로드할 파일 전체경로 + 파일명
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 */
	public static void download(String filePathVar, String fileName, HttpServletRequest request, HttpServletResponse response){
		String dataPath = XmlBean.getServerDataRoot();
		String filePath = dataPath+filePathVar;
		
		//ap루트경로 체크를 할필요가 없음,211028
		//if( !rootDirCheck( filePath ) ){
    	//	return;
    	//}
		
		filePath = LncUtil.filePathReplaceAll(filePath);
		LOG.debug(filePath);
		File file = new File(filePath);
		FileInputStream fin = null;
		BufferedInputStream bfin = null;
		OutputStream os = null;
		if (file.exists() && file.isFile()) {
			String fileNameLocal = file.getName();
			if(fileName != null && !"".equals(fileName)){
				fileNameLocal = fileName;
			}
			try {
				fin = new FileInputStream(file);
				bfin = new BufferedInputStream(fin);
				
				String agentType = request.getHeader("User-Agent");
				if (agentType != null && agentType.indexOf("MSIE 5.5") != -1) {
					response.setContentType("application/octet-stream");
					response.setHeader("Content-Disposition",
							"attachment; filename=" + fileNameLocal + ";");
				} else {
					response.setContentType("application/smnet");
					response.setHeader("Content-Type",
							"application/x-msdownload; charset=UTF-8;");
					response.setHeader("Content-Disposition",
							"attachment; filename="
									+ URLEncoder.encode(fileNameLocal, "UTF-8").replace("+", "%20")
									+ ";");
				}
				response.setHeader("Content-Transfer-Encoding", "binary;");
				response.setHeader("Content-Length", "" + file.length());
				response.setHeader("Pragma", "no-cache;");
				response.setHeader("Expires", "-1;");

				os = response.getOutputStream();
				byte[] abStream = new byte[1024];
				int leng = 0;
				while ((leng = bfin.read(abStream))  !=-1) {
					os.write(abStream, 0, leng);
				}
			} catch (IOException e) {
				LOG.error("ERROR:",e);
			} finally {
				if (fin != null){try{fin.close();}catch(IOException e){LOG.error("ERROR:",e);}}
				if (bfin != null){try{bfin.close();}catch(IOException e){LOG.error("ERROR:",e);}}
				if (os != null){try{os.flush();os.close();}catch(IOException e){LOG.error("ERROR:",e);}}
			}
		} else {
			try {
				response.setContentType( "text/html; charset=UTF-8" ); //리눅스 환경에서 스크립트가 동작 하지 않아 추가함
				response.getWriter().println( "<html  lang='ko'>" );
				response.getWriter().println( "<script  type='text/javascript'>" );
				response.getWriter().println( "alert('File not found!');" );
				response.getWriter().println( "history.back(-1);" );
				response.getWriter().println( "</script><noscript></noscript>" );
				response.getWriter().println( "</html>" );
			} catch (IOException e) {
				LOG.error("ERROR:",e);
			}
		}
	}
	
	/**
	 * 파일을 삭제한다.
	 * @param fullPathVar 삭제할 파일 전체경로 + 파일명
	 * @return 삭제 성공 : true, 실패 : false
	 */
	public static boolean delete(String fullPathVar){
		String dataPath = XmlBean.getServerDataRoot();
		String fullPath = dataPath+fullPathVar;
		
		//루트검사필요 없음,211028
		//if( !rootDirCheck( fullPath ) ){
		//	return false;
		//}
		
		boolean isCheck = false;
		if(fullPath != null && !"".equals(fullPath)){
			fullPath = LncUtil.filePathReplaceAll( fullPath );
			File ff = new File(fullPath);
			if(ff.exists()){
				if(ff.isDirectory()){
					directoryDelete( ff );
				}else{
					if(ff.exists()){
						//ff.delete();
						fileDelete(ff);
					}
					isCheck = true;
				}
			}
		}
		return isCheck;
	}
	
	/**
	 * 디렉토리를 삭제한다.
	 * @param dir 삭제할 디렉토리 파일객체
	 * @return 삭제 성공 : true, 실패 : false
	 */
    public static boolean directoryDelete(File dir) {
    	
    	if(dir == null){
    		return false;
    	}
    	
    	//if( !rootDirCheck( dir.getAbsolutePath() ) ){
    		//return false;
    	//}
    	
        if (dir.isDirectory()) {
            String[] children = dir.list();
            if(children != null){
            	for (int i=0; i<children.length; i++) {
            		children[i]= LncUtil.filePathReplaceAll(children[i]);
            		boolean success = directoryDelete(new File(dir, children[i]));
            		if (!success) {
            			return false;
            		}
            	}
            }
        }

        return dir.delete();
    }
	
    static public void deleteOldDirectory(File path) {
		Long today = new Date().getTime();
		if (path.exists() && path.isDirectory()) {
			File[] files = path.listFiles();
			if(files != null){
				for (int i = 0; i < files.length; i++) {
					if (today - files[i].lastModified() > 24 * 3600 * 1000) {
						directoryDelete(files[i]);
					}
				}
			}
		}
	}
    
    /**
     * <pre>
     * 리스트에 담겨있는 파일정보로 압축함
     * </pre>
     * @param zipFileList : 압축대상파일 리스트
     * @param zipPath : 압축파일 저장 경로
     * @param zipNm : 압축파일 저장 파일명
     * @return
     */
    public static int createZip(List<Map<String, String>> zipFileList,String zipPath,String zipNm) {
		int result = 0;
		String path = zipPath;
		File file = new File(path);
		
		//디렉토리 생성
		//if (!file.exists()) {
		//	file.mkdirs();
		//}
		
		String files[] = null;

		//파일이 디렉토리 일경우 리스트를 읽어오고
		//파일이 디렉토리가 아니면 첫번째 배열에 파일이름을 넣는다.
		if( file.isDirectory() ){
			files = file.list();
		}else{
			files = new String[1];
			files[0] = file.getName();
		}
		
		
		//buffer size
		int size = 1024;
		byte[] buf = new byte[size];
		String outZipNm = path+zipNm;
		
		FileInputStream fis = null;
		ZipArchiveOutputStream zos = null;
		BufferedInputStream bis = null;
		
		try {
			// Zip 파일생성
			zos = new ZipArchiveOutputStream(new BufferedOutputStream(new FileOutputStream(outZipNm)));
			
			//for( int i=0; i < files.length; i++ ){
			for(Map<String, String> zipMap:zipFileList ){
				//해당 폴더안에 다른 폴더가 있다면 지나간다.
				if( new File(path+zipMap.get("saveName")).isDirectory() ){
					continue;
				}
				
				//encoding 설정
				zos.setEncoding("UTF-8");
				
				//buffer에 해당파일의 stream을 입력한다.
				fis = new FileInputStream(path + zipMap.get("saveName"));
				bis = new BufferedInputStream(fis,size);
				
				//zip에 넣을 다음 entry 를 가져온다.
				zos.putArchiveEntry(new ZipArchiveEntry(zipMap.get("orgName")));
				
				//준비된 버퍼에서 집출력스트림으로 write 한다.
				int len;
				while((len = bis.read(buf,0,size)) != -1){
					zos.write(buf,0,len);
				}
				
				bis.close();
				fis.close();
				zos.closeArchiveEntry();
 
			}
			zos.close();
			result=1;
			return result;
		} catch (NullPointerException | IOException e) {
			return result;
		}finally{
			try {
				if( zos != null ){
						zos.close();
				}
				if( fis != null ){
					fis.close();
				}
				if( bis != null ){
					bis.close();
				}
			} catch (IOException e) {
				LOG.error("압축진행 중 에러");
			}
		}
    }
    
    /**
     * file을 multipartfile로 변환
     * @param orgPath
     * @return
     */
    public static MultipartFile getFile2Mf(String orgPath){
    	if( !rootDirCheck( orgPath )){
			return null;
		}
	
		InputStream input2 = null;
		OutputStream os = null;
		String srcFile= LncUtil.filePathReplaceAll(orgPath);
		
		try {
			//원본 검사
			File org = new File(srcFile);
			if(!org.exists()){
				return null;
			}
			
			FileItem fileItem = new DiskFileItem("mainFile", Files.probeContentType(org.toPath()), false, org.getName(), (int) org.length(), org.getParentFile());
			    input2 = new FileInputStream(org);
			    os = fileItem.getOutputStream();
			    IOUtils.copy(input2, os);
			    // Or faster..
			    // IOUtils.copy(new FileInputStream(file), fileItem.getOutputStream());
			MultipartFile mf = new CommonsMultipartFile(fileItem);
			return mf;
		} catch (IOException e) {
			return null;
		} finally {
			if(os != null){try{os.close();}catch(IOException e){LOG.error("ERROR:",e);}}
			if(input2 != null){try{input2.close();}catch(IOException e){LOG.error("ERROR:",e);}}
		}
		
    }
    
    public static String getFileRename(String orgName,String prefixStr){
    	if(orgName != null && prefixStr != null){
    		String fileType = orgName.substring(orgName.lastIndexOf(".") + 1, orgName.length());
    		String fileRename = prefixStr+System.currentTimeMillis() + "." + fileType;
    		return fileRename;
    	}else{
    		return null;
    	}
    }
    public static String getFileExt(String orgName){
    	if(orgName == null){
    		return null;
    	}
    	String fileType = orgName.substring(orgName.lastIndexOf(".") + 1, orgName.length());
		return fileType;
    }
    
    public static String resizeImageFile(MultipartFile mf,int newWidth,int newHeight) {
    	try {
			
    		ResultVO result2 = checkUpload(mf);
    		if(result2.getResult() != 1){
    			return null;
    		}
    		
    		//Image originalImage = ImageIO.read(new File(path, savedName));
    		Image originalImage = ImageIO.read(mf.getInputStream());
    		
    		//int originWidth = originalImage.getWidth(null);
    		//int originHeight = originalImage.getHeight(null);
    		
			//int newHeight = (originHeight * newWidth) / originWidth;
			
			Image resizeImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
			
			BufferedImage newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
			Graphics graphics = newImage.getGraphics();
			graphics.drawImage(resizeImage, 0, 0, null);
			graphics.dispose();
			
			String orgName = mf.getOriginalFilename();
			String resizeImgName = FileUtil.getFileRename(orgName, "resize_");
			String path = TempFileService.TEMP_PATH+"resize/";
			FileUtil.makeDirectorys( path );
			File newFile = new File(path  + resizeImgName);
			String formatName = orgName.substring(orgName.lastIndexOf(".") + 1);
			ImageIO.write(newImage, formatName.toLowerCase(), newFile);
			
			return path+resizeImgName;    
		} catch (NullPointerException | IOException e) {
			return null;
		}
    }
    
    public static ResultVO checkUpload(MultipartFile file){
		ResultVO result = new ResultVO();

		long maxFileSize = (1024 * 1024 * 300);
		// 체크
		long size = file.getSize();
		if (size > maxFileSize) {
			result.setMsg("용량초과");
			result.setResult(0);
			return result;
		}
		// Receive file from MultipartFile
		String fileName = file.getOriginalFilename().toLowerCase();
		// Check file extension from whitelist
		if (fileName != null) {
			if (!fileName.endsWith(".doc") && !fileName.endsWith(".hwp") && !fileName.endsWith(".pdf") && !fileName.endsWith(".xls")
					&& !fileName.endsWith(".txt") && !fileName.endsWith(".xlsx") && !fileName.endsWith(".jpg") && !fileName.endsWith(".png") && !fileName.endsWith(".zip")
					&& !fileName.endsWith(".mp4") && !fileName.endsWith(".webp")
					) {
				result.setMsg("확장자 위반");
				result.setResult(0);
				return result;
			} 
		}

    	
    	result.setResult(1);
    	return result;
    }
    
	public static ResultVO multiPartupload( MultipartFile mf, String subPath, String fileOrg, String fileRename) {
		ResultVO result = new ResultVO();
		if(mf == null || subPath == null || fileOrg == null || fileRename == null){
			result.setResult(0);
			return result;
		}
		
		ResultVO result2 = checkUpload(mf);
		if(result2.getResult() != 1){
			result.setMsg(result2.getMsg());
			result.setResult(0);
			return result;
		}
		
		try {
			
			fileRename = LncUtil.replaceXSS(fileRename);
			String dataPath = XmlBean.getServerDataRoot();
			File file = null;
			String orgNm = "";
			
			File dir = new File(dataPath+subPath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
		
			String fileType = fileOrg.substring(fileOrg.lastIndexOf(".") + 1, fileOrg.length());
			
			file = new File( dataPath+subPath  + fileRename );
			mf.transferTo(file);
			
			//권한 변경
			file.setReadable(true, false);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException | IllegalStateException | IOException e) {
			e.printStackTrace();
			result.setResult(0);
			return result;
		}
	}
	public static ResultVO move2Path(String sourcePath, String targetPath,String saveFileNm) {
		String dataPath = XmlBean.getServerDataRoot();
		ResultVO result = new ResultVO();
		try {
			String orgNm = "";
			
			File dir = new File(dataPath+targetPath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
		
			//String fileType = saveFileNm.substring(saveFileNm.lastIndexOf(".") + 1, saveFileNm.length());
			
			File file = new File( dataPath+sourcePath  + saveFileNm );
			File newFile = new File( dataPath+targetPath  + saveFileNm );

			Files.copy(file.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
	        
			if(file.exists()){
				//file.delete();
				fileDelete(file);
			}
			
			result.setResult(1);
			return result;
		} catch (NullPointerException | IOException e) {
			result.setResult(0);
			return result;
		} 
	}
    
	private static synchronized void  fileDelete(File file){
		file.delete();
	}
}
