package com.yfy.app.net.user;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by yfyandr on 2018/4/27.
 */
@Root(name = "readnoticeResponse")
public class ReadNoticeRes {
    @Attribute(name = "xmlns", empty = "http://tempuri.org/", required = false)
    public String nameSpace;

    @Element(name = "readnoticeResult", required = false)
    public String readnoticeResult;
}
