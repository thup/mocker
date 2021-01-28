package org.nico.mocker.utils;

import org.nico.mocker.dto.MockInfo;

/**
 *@Author tlibn
 *@Date 2021/1/28 19:45
 **/
public class StrUtils {

    public static void main(String[] args) {


        String uri = "/aaa/bbb/ccc";

        getMockInfo(uri);


    }

    public static MockInfo getMockInfo(String uri) {

        MockInfo mockInfo = new MockInfo();

        if(uri.startsWith("/")){
            uri = uri.substring(1, uri.length());
        }

        if(uri.contains("/")){
            int n = uri.indexOf("/");
            String sign = uri.substring(0,n);
            String realUri = uri.substring(n, uri.length());
            mockInfo.setSign(sign);
            mockInfo.setRealUri(realUri);

            /*//uri==  aaa/bbb/ccc
            System.out.println("uri==  "+uri);
            //sign==  aaa
            System.out.println("sign==  "+sign);
            //realUri==  /bbb/ccc
            System.out.println("realUri==  "+realUri);*/
        }
        return mockInfo;
    }


}
