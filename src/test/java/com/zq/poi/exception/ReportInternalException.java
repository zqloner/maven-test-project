package com.zq.poi.exception;

/**
 * @Title: ReportInternalException
 * @Description:
 * @Company: 盟固利
 * @author: 张奇
 * @date: ceate in 2020/5/22 9:25
 */
public class ReportInternalException extends Exception {
    public ReportInternalException() {
    }

    public ReportInternalException(String message) {
        super(message);
    }

    public ReportInternalException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReportInternalException(Throwable cause) {
        super(cause);
    }

    public ReportInternalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
