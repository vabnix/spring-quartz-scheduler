package util;

import org.slf4j.MDC;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class AppUtil {

    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    public static void putTransactionId() {
        MDC.put(AppConstants.KEY_TXNID, AppUtil.getUUID());
    }

    public static String getTransactionId() {
        if(null == MDC.get(AppConstants.KEY_TXNID)) {
            putTransactionId();
        }
        return MDC.get(AppConstants.KEY_TXNID);
    }

    public static void clearTransactionId() {
        MDC.remove(AppConstants.KEY_TXNID);
    }

    private static boolean isCollectionEmpty(Collection<?> collection) {
        if (collection == null || collection.isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean isObjectEmpty(Object object) {
        if(object == null) return true;
        else if(object instanceof String) {
            if (((String)object).trim().length() == 0) {
                return true;
            }
        } else if(object instanceof Collection) {
            return isCollectionEmpty((Collection<?>)object);
        }
        return false;
    }

    public static String getBeanToJsonString(Object beanClass) {
        return new Gson().toJson(beanClass);
    }

    public static String getBeanToJsonString(Object... beanClasses) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Object beanClass : beanClasses) {
            stringBuilder.append(getBeanToJsonString(beanClass));
            stringBuilder.append(", ");
        }
        return stringBuilder.toString();
    }

    public String concatenate(List<String> listOfItems, String separator) {
        StringBuilder sb = new StringBuilder();
        Iterator<String> stit = listOfItems.iterator();

        while (stit.hasNext()) {
            sb.append(stit.next());
            if(stit.hasNext()) {
                sb.append(separator);
            }
        }

        return sb.toString();
    }
}
