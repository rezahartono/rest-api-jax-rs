/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package common;

/**
 *
 * @author rezah
 */
public class SettingConstant {

    public class JWTConstant {

        public static final String JWT_ALGORITHM = "KucingHitam123!";
        public static final String JWT_ISSUER = "rezah";
        public static final String JWT_TOKEN_TYPE = "Bearer";
        public static final String AUTHORIZATION = "Authorization";
    }

    public class JWTInformationData {

        public static final String USER_ID = "id";
        public static final String SESSION_ID = "session_id";
        public static final String SCHEMA_NAME = "schema_name";
    }

    public class ResponseData {

        public static final String METADATA = "metadata";
        public static final String DATA = "data";
        public static final String PAGINATION = "pagination";

    }

    public class ResponseMetaData {

        public static final String PATH = "path";
        public static final String STATE_CODE = "state_code";
        public static final String STATE = "state";
        public static final String MESSAGES = "messages";

    }
    
    public class ResponsePagination{
        public static final String TOTAL_PAGES = "total_pages";
        public static final String CURRENT_PAGE = "current_page";
        public static final String TOTAL_ITEMS = "total_items";
        public static final String CURRENT_ITEM = "current_item";
        
    }
}
