package kr.mapo.eco100.repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import kr.mapo.eco100.controller.v1.faq.dto.FAQ;
import kr.mapo.eco100.controller.v1.faq.dto.FAQJeju;

@Component
public class FAQRepository {
    private List<FAQJeju> FAQlist_Jeju;
    private List<FAQ> FAQlist;

    @PostConstruct
    public void init() throws IOException {
        FAQlist_Jeju = new ArrayList<>();
        FAQlist = new ArrayList<>();
        XSSFWorkbook workbook = null;

        try {
            File file = new File("./data/Database.xlsx");

            FileInputStream fis = new FileInputStream(file);
            workbook = new XSSFWorkbook(fis);

            //FAQ 제주
            XSSFSheet FAQ_Jeju_sheet = workbook.getSheetAt(0);

            int rows = FAQ_Jeju_sheet.getPhysicalNumberOfRows();
            for(int rowindex = 1; rowindex < rows; rowindex++) {
                XSSFRow row = FAQ_Jeju_sheet.getRow(rowindex);

                Integer id = rowindex;
                String type = String.valueOf(row.getCell(1));
                String name = String.valueOf(row.getCell(2));
                String details = String.valueOf(row.getCell(3));
                String precautions = String.valueOf(row.getCell(4));
                FAQJeju faq = new FAQJeju(id,type,name,details,precautions);
                
                FAQlist_Jeju.add(faq);
            }
            
            //그냥 FAQ
            XSSFSheet FAQ_sheet = workbook.getSheetAt(1);
            rows = FAQ_sheet.getPhysicalNumberOfRows();
            for(int rowindex = 1; rowindex < rows; rowindex++) {
                XSSFRow row = FAQ_sheet.getRow(rowindex);

                Integer id = rowindex;
                String category = String.valueOf(row.getCell(1));
                String question = String.valueOf(row.getCell(2));
                String answer = String.valueOf(row.getCell(3));
                FAQ faq = new FAQ(id,category,question,answer);
                
                FAQlist.add(faq);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            workbook.close();
        }
    }

    public List<FAQJeju> getFAQlistJeju() {
        return FAQlist_Jeju;
    }

    public List<FAQ> getFAQlist() {
        return FAQlist;
    }

    public List<FAQ> searchFAQ(String word) {
        return FAQlist.stream()
        .filter(faq->
            faq.getQuestion().contains(word)
        )
        .collect(Collectors.toList());
    }

    public List<FAQJeju> searchFAQJeju(String word) {
        return FAQlist_Jeju.stream()
        .filter(faq->
            faq.getName().contains(word)
        )
        .collect(Collectors.toList());
    }
}
