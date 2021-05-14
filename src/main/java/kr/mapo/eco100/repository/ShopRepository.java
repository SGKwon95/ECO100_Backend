package kr.mapo.eco100.repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import kr.mapo.eco100.controller.v1.shop.dto.Shop;

@Component
public class ShopRepository {
    private List<Shop> shoplist;

    @PostConstruct
    public void init() throws IOException {
        shoplist = new ArrayList<>();

        XSSFWorkbook workbook = null;

        try {
            File file = new File("./data/Database.xlsx");

            FileInputStream fis = new FileInputStream(file);
            workbook = new XSSFWorkbook(fis);

            XSSFSheet shop_sheet = workbook.getSheetAt(2);

            int rows = shop_sheet.getPhysicalNumberOfRows();
            for (int rowindex = 1; rowindex < rows; rowindex++) {
                XSSFRow row = shop_sheet.getRow(rowindex);

                Integer id = rowindex;
                String name = String.valueOf(row.getCell(0));
                String address = String.valueOf(row.getCell(1));
                String phone_num = String.valueOf(row.getCell(2));
                String running_info = String.valueOf(row.getCell(3));
                String web_url = String.valueOf(row.getCell(4));
                String location = String.valueOf(row.getCell(5));
                Float latitude = Float.parseFloat(location.split(", ")[0]);
                Float longitude = Float.parseFloat(location.split(", ")[1]);

                String base_url = "http://rpinas.iptime.org:10122/image/";

                String img_url = base_url + "shop/" + name + ".jpg";
                if (id == 15 || id == 28)
                    img_url = null;

                String logo_url = base_url + "logo/" + name + ".jpg";
                if (id == 3 || id == 20 || id == 33 || id == 36 || id == 39 || id == 41)
                    logo_url = null;

                Shop shop = new Shop(id, name, address, phone_num, running_info, web_url, latitude, longitude, img_url,
                        logo_url);
                shoplist.add(shop);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            workbook.close();
        }
    }

    public List<Shop> getShoplist() {
        return shoplist;
    }
}