package com.abysscat.usercenter.base.utils;

import java.net.InetAddress;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;
import sun.net.util.IPAddressUtil;

@Slf4j
public class IPUtil {

    public static boolean isInternalIP(String ipStr) {
        if ("127.0.0.1".equals(ipStr) || "0:0:0:0:0:0:0:1".equals(ipStr)) {
            return true;
        }
        byte[] addr = IPAddressUtil.textToNumericFormatV4(ipStr);
        if (addr == null) {
            addr = IPAddressUtil.textToNumericFormatV6(ipStr);
        }
        return internalIp(addr);
    }

    public static boolean internalIp(byte[] addr) {
        if (addr == null) {
            return false;
        }
        final byte b0 = addr[0];
        final byte b1 = addr[1];
        // 10.x.x.x/8
        final byte section1 = 0x0A;
        // 172.16.x.x/12
        final byte section2 = (byte) 0xAC;
        final byte section3 = (byte) 0x10;
        final byte section4 = (byte) 0x1F;
        // 192.168.x.x/16
        final byte section5 = (byte) 0xC0;
        final byte section6 = (byte) 0xA8;
        switch (b0) {
            case section1:
                return true;
            case section2:
                if (b1 >= section3 && b1 <= section4) {
                    return true;
                }
                return false;
            case section5:
                if (b1 == section6) {
                    return true;
                }
                return false;
            default:
                return false;
        }
    }

    public static String getReqIP(HttpServletRequest request) {
        String reqIp = StringUtils.EMPTY;

        if (request != null) {
            reqIp = getRealIp(request.getHeader("X-Forwarded-For"));
            if (reqIp == null || reqIp.length() == 0 || "unknown".equalsIgnoreCase(reqIp)) {
                reqIp = request.getHeader("Proxy-Client-IP");
            }
            if (reqIp == null || reqIp.length() == 0 || "unknown".equalsIgnoreCase(reqIp)) {
                reqIp = request.getHeader("WL-Proxy-Client-IP");
            }
            if (reqIp == null || reqIp.length() == 0 || "unknown".equalsIgnoreCase(reqIp)) {
                reqIp = request.getHeader("HTTP_CLIENT_IP");
            }
            if (reqIp == null || reqIp.length() == 0 || "unknown".equalsIgnoreCase(reqIp)) {
                reqIp = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (reqIp == null || reqIp.length() == 0 || "unknown".equalsIgnoreCase(reqIp)) {
                reqIp = request.getRemoteAddr();
            }
        } else {
            reqIp = "UNKNOWN";
        }

        return reqIp;
    }

    /**
     * @param header
     *
     * @return
     */
    private static String getRealIp(String header) {
        if (StringUtils.isEmpty(header)) {
            return null;
        } else {
            String[] ipArr = header.split(",");
            if (ipArr != null && ipArr.length > 0 && ipArr[0] != null) {
                return ipArr[0].trim();
            } else {
                return null;
            }
        }
    }

    public static String getLocalIp() {
        try {
            InetAddress addr = InetAddress.getLocalHost();
            return addr.getHostAddress();
        }
        catch (Exception ex) {
            log.warn("getLocalIp error: {}", ex);
        }
        return null;
    }
}
