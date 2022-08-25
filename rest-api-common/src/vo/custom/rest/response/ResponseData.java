/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vo.custom.rest.response;

import common.SettingConstant;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author rezah
 */
public class ResponseData {

    public Map<String, Object> MetaData(String path, int stateCode, String state, String messages) {
        Map<String, Object> metadata = new HashMap<>();

        metadata.put(SettingConstant.ResponseMetaData.PATH, path);
        metadata.put(SettingConstant.ResponseMetaData.STATE, state);
        metadata.put(SettingConstant.ResponseMetaData.STATE_CODE, stateCode);
        metadata.put(SettingConstant.ResponseMetaData.MESSAGES, messages);

        return metadata;
    }

    public Map<String, Object> Pagination(int totalPages, int currentPage, int totalItems, int currentItem) {
        Map<String, Object> pagination = new HashMap<>();

        pagination.put(SettingConstant.ResponsePagination.TOTAL_PAGES, totalPages);
        pagination.put(SettingConstant.ResponsePagination.CURRENT_PAGE, currentPage);
        pagination.put(SettingConstant.ResponsePagination.TOTAL_ITEMS, totalItems);
        pagination.put(SettingConstant.ResponsePagination.CURRENT_ITEM, currentItem);

        return pagination;
    }
}
