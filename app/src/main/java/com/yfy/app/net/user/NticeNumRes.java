package com.yfy.app.net.user;

import com.yfy.final_tag.TagFinal;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by yfyandr on 2018/4/26.
 */
@Root(name = TagFinal.GETNOTICENUM+"Response")
public class NticeNumRes {
    @Attribute(name = "xmlns", empty = "http://tempuri.org/", required = false)
    public String nameSpace;

    @Element(name = "getnoticenumResult", required = false)
    public String getnoticenumResult;
}
