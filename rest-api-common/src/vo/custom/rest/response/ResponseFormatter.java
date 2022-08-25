/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vo.custom.rest.response;

import common.SettingConstant;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.core.Response;

/**
 *
 * @author rezah
 */
public class ResponseFormatter{
    ResponseData data = new ResponseData();
    private final Map<String, Object> responseData = new HashMap<>();
    
    private static ResponseFormatter rf;

    public static ResponseFormatter getInstance() {
        if (rf == null) {
            rf = new ResponseFormatter();
        }

        return rf;
    }

    public Map<String, Object> OK(String path, Object resultData, String messages, int totalPages, int currentPage, int totalItems, int currentItem) {
        responseData.put(SettingConstant.ResponseData.METADATA, data.MetaData(path, Response.Status.OK.getStatusCode(), Response.Status.OK.toString(), messages));
        responseData.put(SettingConstant.ResponseData.PAGINATION, data.Pagination(totalPages, currentPage, totalItems, currentItem));
        responseData.put(SettingConstant.ResponseData.DATA, resultData);
        
        return responseData;
    }
}
