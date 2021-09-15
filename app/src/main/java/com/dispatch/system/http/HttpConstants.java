package com.dispatch.system.http;

/**
 * 请求数据的全局常量
 *
 * @author chenjunxu
 * @date 2017/8/14
 */
public class HttpConstants {

    /**
     * 正式环境
     *
     */
    /*public final static String ROOT_URL = "https://epssys.kuyuntech.com/";*/
    public final static String ROOT_URL_TEST = "http://test.gzfzdev.com:5035";
    public final static String ROOT_URL = "http://test.gzfzdev.com:5035";
   /* public final static String ROOT_URL_TEST = "http://nat.kuyuntech.com:19835/";*/
    /**
     * 接口请求前缀
     */
    public final static String BASE_URL = ROOT_URL_TEST;
    /**
     * 头文件中，sessionId 的 KEY
     */
    public final static String SESSION_ID_NAME = "JSESSIONID";
}
