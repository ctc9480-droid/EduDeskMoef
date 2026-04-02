import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class XlsToEdu {
	static String folderPath = "C:/doc/crepas/edu/KTL_한국산업기술연구원/문서/마이그레이션/LMS_아토소프트/TMP";
	static String jdbcURL = "jdbc:mariadb://172.234.90.216:3307/edudesk_ktl"; // MySQL 주소
	static String dbUser = "edudesk_ktl"; // MySQL 사용자명
	static String dbPassword = "edudesk_ktl_1@3$"; // MySQL 비밀번호
	
	public static void main(String[] args) {
		
        // 폴더 객체 생성
        File folder = new File(folderPath);

        // 폴더가 존재하는지 확인
        if (folder.exists() && folder.isDirectory()) {
            // 폴더 내 모든 파일 및 디렉토리 가져오기
            File[] files = folder.listFiles();

            if (files != null) {
                System.out.println("폴더 내 파일 목록:");
                for (File file : files) {
                	if (file.isFile()) {
                        System.out.println("[파일] " + file.getName());
                        htmlToEdu(folderPath + "/" + file.getName());
                    }
                }
            } else {
                System.out.println("폴더 내 파일이 없습니다.");
            }
        } else {
            System.out.println("폴더가 존재하지 않거나 잘못된 경로입니다.");
        }
        
		//htmlToEdu(htmlFilePath);
		//xlsToEdu(excelFilePath);
	}
	
	public static void htmlToEdu(String htmlFilePath) {
		
        try (Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword)) {
            File input = new File(htmlFilePath);
            Document doc = Jsoup.parse(input, "EUC-KR"); // HTML 파일을 파싱
            
            Elements rows = doc.select("table:nth-of-type(2) tbody tr"); // 두 번째 테이블의 tbody 안의 tr 가져오기
            String insertSQL = "INSERT INTO ktl_edu_result (user_nm, rgst_num, mobile, edu_nm, edu_st_dt, hrd_reg_dt, status, card_tp, edu_tp) " +
                               "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);

            for (Element row : rows) {
                Elements cols = row.select("td");
                if (cols.size() < 10) continue; // 데이터가 부족한 행은 스킵 

                String number = cols.get(0).text();
                if ("번호".equals(number)) continue; // 타이틀이 있는 행은 스킵
                
                String user_nm = cols.get(1).text();
                String rgst_num = cols.get(2).text();
                String mobile = cols.get(3).text();
                mobile = mobile.length() >= 4 ? mobile.substring(mobile.length() - 4) : mobile;
                String edu_nm = cols.get(4).text();
                String edu_st_dt = cols.get(5).text();
                String hrd_reg_dt = cols.get(6).text().trim().isEmpty() ? null : cols.get(6).text();
                String status = cols.get(7).text();
                String card_tp = cols.get(8).text().trim().isEmpty() ? null : cols.get(8).text();
                String edu_tp = cols.get(9).text();
                
                System.out.println("user_nm:" + user_nm);

                preparedStatement.setString(1, user_nm);
                preparedStatement.setString(2, rgst_num);
                preparedStatement.setString(3, mobile);
                preparedStatement.setString(4, edu_nm);
                preparedStatement.setString(5, edu_st_dt);
                preparedStatement.setString(6, hrd_reg_dt);
                preparedStatement.setString(7, status);
                preparedStatement.setString(8, card_tp);
                preparedStatement.setString(9, edu_tp);

                preparedStatement.executeUpdate();
            }

            System.out.println("HTML 데이터를 MySQL에 성공적으로 삽입했습니다.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public static void xlsToEdu(String excelFilePath) {
		
        try {
        	Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
            FileInputStream file = new FileInputStream(new File(excelFilePath));
            //Workbook workbook = new XSSFWorkbook(file);
            Workbook workbook = WorkbookFactory.create(file);

            Sheet sheet = workbook.getSheetAt(0); // 첫 번째 시트 읽기
            String insertSQL = "INSERT INTO ktl_edu_result (user_nm, rgst_num, mobile, edu_nm, edu_st_dt, hrd_reg_dt, status, card_tp, edu_tp) " +
                               "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // 첫 번째 행(헤더) 건너뜀

                String user_nm = row.getCell(0).getStringCellValue();
                String rgst_num = row.getCell(1).getStringCellValue();
                String mobile = row.getCell(2).getStringCellValue();
                String edu_nm = row.getCell(3).getStringCellValue();
                String edu_st_dt = row.getCell(4).getStringCellValue();
                String hrd_reg_dt = row.getCell(5).getStringCellValue();
                String status = row.getCell(6).getStringCellValue();
                String card_tp = row.getCell(7).getStringCellValue();
                String edu_tp = row.getCell(8).getStringCellValue();
                
                System.out.println("user_nm:" + user_nm);

                preparedStatement.setString(1, user_nm);
                preparedStatement.setString(2, rgst_num);
                preparedStatement.setString(3, mobile);
                preparedStatement.setString(4, edu_nm);
                preparedStatement.setString(5, edu_st_dt);
                preparedStatement.setString(6, hrd_reg_dt);
                preparedStatement.setString(7, status);
                preparedStatement.setString(8, card_tp);
                preparedStatement.setString(9, edu_tp);

                preparedStatement.executeUpdate();
            }

            System.out.println("엑셀 데이터를 MySQL에 성공적으로 삽입했습니다.");
        } catch (Exception e) {
            e.printStackTrace();
        } 
	}

}
