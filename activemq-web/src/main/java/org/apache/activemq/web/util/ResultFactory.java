package org.apache.activemq.web.util;
import java.util.List;

public class ResultFactory {


    public static <T> Result obtainResultByList(List<T> lists) {
        return obtainResult(ResultCode.SUCCESSFUL, "成功", lists.size(), lists);
    }

    public static <T> Result obtainResultBySuccessful(int count, List<T> t) {
        return obtainResult(ResultCode.SUCCESSFUL, "成功", count, t);
    }

    public static <T> Result obtainResultBySuccessful(int count, T t) {
        return obtainResult(ResultCode.SUCCESSFUL, "成功", count, t);
    }


    public static <T> Result obtainResultByFailure(int count, T t) {
        return obtainResult(ResultCode.FAILURE, "失败", count, t);
    }

    public static <T> Result obtainResultByFailure(int count, List<T> t) {
        return obtainResult(ResultCode.FAILURE, "失败", count, t);
    }

    public static <T> Result obtainResult(int code, String msg, int count, Object response) {
        return new Result(code, count, msg, response);
    }


}
