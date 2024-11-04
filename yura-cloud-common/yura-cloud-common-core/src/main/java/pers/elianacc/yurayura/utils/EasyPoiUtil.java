package pers.elianacc.yurayura.utils;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * EasyPoi相关 util
 *
 * @author ELiaNaCc
 * @since 2023-03-23
 */
public class EasyPoiUtil {

    /**
     * 保存按模板excel导出
     *
     * @param exportExcelName      导出excel文件名（含后缀）
     * @param dataSet              导出数据（必须map类型）
     * @param saveDir              导出文件保存路径
     * @param templateExportParams 模板导出参数
     * @return void
     */
    public static void saveExportExcelByTemplate(String exportExcelName, Map<String, Object> dataSet, String saveDir
            , TemplateExportParams templateExportParams) throws IOException {
        Workbook workbook = ExcelExportUtil.exportExcel(templateExportParams, dataSet);
        File savefile = new File(saveDir);
        if (!savefile.exists()) {
            savefile.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream(saveDir + "/" + exportExcelName);
        workbook.write(fos);
        fos.close();
    }

    /**
     * 按模板excel导出
     *
     * @param exportExcelName
	 * @param dataSet
	 * @param templateExportParams
	 * @param response
     * @return void
     */
    public static void exportExcelByTemplate(String exportExcelName, Map<String, Object> dataSet
            , TemplateExportParams templateExportParams, HttpServletResponse response) throws IOException {
        Workbook workbook = ExcelExportUtil.exportExcel(templateExportParams, dataSet);

        if (workbook != null) {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition"
                    , "attachment;filename=" + URLEncoder.encode(exportExcelName, "UTF-8") + ".xlsx");
            workbook.write(response.getOutputStream());
            workbook.close();
        }

    }

}
