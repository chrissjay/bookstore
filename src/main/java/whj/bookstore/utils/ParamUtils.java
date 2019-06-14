package whj.bookstore.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class ParamUtils {

    public static Map<String, Object> handleServletParameter(HttpServletRequest request) {
        Map<String, String[]> parameter = request.getParameterMap();
        Map<String, Object> rParams = new HashMap<>(0);
        for (Map.Entry<String, String[]> m : parameter.entrySet()) {
            String key = m.getKey();
            String[] obj = parameter.get(key);
            rParams.put(key, (obj.length > 1) ? obj : obj[0]);
        }
        return rParams;
    }

}
