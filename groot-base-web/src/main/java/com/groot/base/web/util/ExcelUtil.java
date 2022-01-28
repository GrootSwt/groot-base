package com.groot.base.web.util;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.groot.base.web.exception.BusinessRuntimeException;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * 简单Excel导入导出
 */
public class ExcelUtil {

    /**
     * 单sheet页获取数据
     *
     * @param file       网络文件
     * @param titleClazz 标题类
     * @param titleRows  标题行数
     * @param headRows   表头行数
     * @return 数据
     * @throws Exception 文件异常或Excel导入解析异常
     */
    public static <T> List<T> listDataFromExcel(MultipartFile file, Class<T> titleClazz, int titleRows, int headRows) throws Exception {
        if (file == null) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headRows);
        return ExcelImportUtil.importExcel(file.getInputStream(), titleClazz, params);
    }

    /**
     * 多sheet页获取指定页数据
     *
     * @param file            网络文件
     * @param titleClazz      标题类
     * @param startSheetIndex sheet索引
     * @param titleRows       标题行数
     * @param headRows        表头行数
     * @return 数据
     * @throws Exception 文件异常或Excel导入解析异常
     */
    public static <T> List<T> listDataFromExcel(MultipartFile file, Class<T> titleClazz, int startSheetIndex, int titleRows, int headRows) throws Exception {
        if (file == null) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setStartSheetIndex(startSheetIndex);
        params.setTitleRows(titleRows);
        params.setHeadRows(headRows);
        return ExcelImportUtil.importExcel(file.getInputStream(), titleClazz, params);
    }

    /**
     * 下载默认样式单sheet的Excel文件
     *
     * @param fileName   文件名
     * @param titleClazz 标题类
     * @param dataList   数据
     * @param response   相应
     */
    public static <T> void downloadExcel(String fileName, Class<T> titleClazz, List<T> dataList, HttpServletResponse response) {
        ExportParams params = new ExportParams();
        Workbook workbook = ExcelExportUtil.exportExcel(params, titleClazz, dataList);
        download(fileName, workbook, response);
    }

    /**
     * 下载自定义样式单sheet的Excel文件
     *
     * @param fileName   文件名
     * @param titleClazz 标题类
     * @param dataList   数据
     * @param response   相应
     */
    public static <T> void downloadExcel(String fileName, Class<T> titleClazz, List<T> dataList, ExportParams params, HttpServletResponse response) {
        Workbook workbook = ExcelExportUtil.exportExcel(params, titleClazz, dataList);
        download(fileName, workbook, response);
    }

    /**
     * 下载自定义样式多sheet的Excel文件
     *
     * @param fileName 文件名
     * @param list     title：ExportParams；entity：标题类；data：数据
     * @param type     Excel类型（HSSF,XSSF）
     * @param response 响应
     */
    public static <T> void downloadExcel(String fileName, List<Map<String, Object>> list, ExcelType type, HttpServletResponse response) {
        Workbook workbook = ExcelExportUtil.exportExcel(list, type);
        download(fileName, workbook, response);
    }

    /**
     * 下载
     *
     * @param fileName 文件名
     * @param workbook Excel
     * @param response 响应
     */
    private static void download(String fileName, Workbook workbook, HttpServletResponse response) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-type", "application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            workbook.write(response.getOutputStream());
        } catch (Exception e) {
            throw new BusinessRuntimeException("文件下载失败！");
        }
    }
}
