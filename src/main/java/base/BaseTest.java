package base;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import listners.CustomListener;
import org.apache.log4j.Logger;
import org.testng.annotations.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;

@Listeners({CustomListener.class})
public class BaseTest {
    protected Logger log = Logger.getLogger(getClass());


    @BeforeClass(alwaysRun = true)
    public void baseTestBeforeClass() {
        log.info("Starting the Before class of 'Base Test'");

    }

    @AfterClass(alwaysRun = true)
    public void baseTestAfterClass() {
        log.info("Starting the After class of 'Base Test'");
    }


    @BeforeMethod(alwaysRun = true)
    public void logStartMethod(Method testMethod) {
        log.info("Starting tests method '" + testMethod.getName() + "'");
    }


    @AfterMethod(alwaysRun = true)
    public void logEndMethod(Method testMethod) {
        log.info("Ending tests method '" + testMethod.getName() + "'");
    }


    protected String[][] parseExcelDataToDataProvider(String fileName, String sheetName) throws IOException {
        this.log.info("Reading data from excel '" + fileName + "' with sheet name '" + sheetName + "'");
        String[][] arrayExcelData = (String[][]) null;

        try {

            File file = new File(fileName);

            FileInputStream fs = new FileInputStream(file);
            Workbook wb = Workbook.getWorkbook(fs);
            Sheet sh = wb.getSheet(sheetName);
            int totalNoOfCols = sh.getColumns();
            int totalNoOfRows = sh.getRows();
            arrayExcelData = new String[totalNoOfRows][totalNoOfCols];

            for (int i = 0; i < totalNoOfRows; ++i) {
                for (int j = 0; j < totalNoOfCols; ++j) {
                    arrayExcelData[i][j] = sh.getCell(j, i).getContents();
                }
            }
        } catch (IOException | BiffException var11) {
            this.log.error("Error reading the exel fileException-" + var11);
        }

        return arrayExcelData;

    }


}
