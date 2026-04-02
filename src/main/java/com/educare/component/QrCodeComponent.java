package com.educare.component;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.stringtemplate.v4.compiler.STParser.mapExpr_return;

import ch.qos.logback.core.util.FileUtil;

import com.educare.edu.comn.service.impl.SmartCheckServiceImpl;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.util.LncUtil;
import com.educare.util.XmlBean;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

@Component("qccp")
public class QrCodeComponent {
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(QrCodeComponent.class.getName());
	//private static String CHARSET = "utf-8";
	private final static int SAVE_WIDTH=300;
	private final static int SAVE_HEIGHT=300;
	public static ResultVO createQRCode(String qrData,String path,String fileNm,int width,int height,String addText1,String addText2p,String addText3) {
        ResultVO result = new ResultVO();
		try {
			String addText2 = addText2p;//시큐어코딩 수정
			String saveFilePath = XmlBean.getServerDataRoot()+path+fileNm;
			LOG.info("saveFilePath: {}",saveFilePath);
        	FileUtils.forceMkdir(new File(XmlBean.getServerDataRoot()+path));
        	
        	int saveWidth = width > 0 ? width : SAVE_WIDTH;
        	int saveHeight = height > 0 ? height : SAVE_HEIGHT;
		
        	Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<>();
        	hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
		
        	BitMatrix matrix = new MultiFormatWriter().encode(
		         qrData,
		         BarcodeFormat.QR_CODE, saveWidth, saveHeight, hintMap);
		
        	//MatrixToImageWriter.writeToFile(matrix, saveFilePath.substring(saveFilePath.lastIndexOf('.') + 1), new File(saveFilePath));
        	BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(matrix);

            // 텍스트 오버레이 추가
            BufferedImage combined = new BufferedImage(saveWidth, saveHeight + 150, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = combined.createGraphics();

            // 하얀색 배경 그리기
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, saveWidth, saveHeight + 150);
            
            // QR 코드 이미지 그리기
            g.drawImage(qrImage, 0, 0, null);

            // 첫 번째 텍스트 그리기 (크게)
            g.setColor(Color.BLACK);
            g.setFont(new Font("Malgun Gothic", Font.BOLD, 24)); // 맑은 고딕 폰트 사용, 볼드체, 크기 24
            FontMetrics fm1 = g.getFontMetrics();
            int textWidth1 = fm1.stringWidth(addText1);
            g.drawString(addText1, (saveWidth - textWidth1) / 2, saveHeight + 30);

            // 두 번째 텍스트 그리기 (작게)
            if(addText2.length() > 20){
            	addText2 = addText2.substring(0,18)+"...";
            }
            g.setFont(new Font("Malgun Gothic", Font.PLAIN, 18)); // 맑은 고딕 폰트 사용, 일반체, 크기 16
            FontMetrics fm2 = g.getFontMetrics();
            int textWidth2 = fm2.stringWidth(addText2);
            g.drawString(addText2, (saveWidth - textWidth2) / 2, saveHeight + 70);
            
            // 세 번째 텍스트 그리기 (작게)
            g.setFont(new Font("Malgun Gothic", Font.PLAIN, 16)); // 맑은 고딕 폰트 사용, 일반체, 크기 16
            FontMetrics fm3 = g.getFontMetrics();
            int textWidth3 = fm3.stringWidth(addText3);
            g.drawString(addText3, (saveWidth - textWidth3) / 2, saveHeight + 110);
            
            
            g.dispose();

            Path filePath = new File(saveFilePath).toPath();
            ImageIO.write(combined, saveFilePath.substring(saveFilePath.lastIndexOf('.') + 1), filePath.toFile());

        	
        	Map<String, Object> data = new HashMap<>();
        	data.put("qrSrc", "/"+path+fileNm);
        	result.setData(data);
        	result.setResult(1);
        	return result;
		} catch (NullPointerException | IOException | WriterException e) {
			e.printStackTrace();
			result.setResult(0);
			return result;
		}
    }

    public static String readQRCode(String filePath)
            throws IOException, NotFoundException {
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(
                new BufferedImageLuminanceSource(
                        ImageIO.read(Files.newInputStream(Paths.get(filePath))))));
        Result qrCodeResult = new MultiFormatReader().decode(binaryBitmap);
        return qrCodeResult.getText();
    }
}
