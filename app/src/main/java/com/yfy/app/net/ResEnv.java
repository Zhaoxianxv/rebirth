package com.yfy.app.net;

import com.yfy.base.Base;
import com.yfy.final_tag.TagFinal;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

/**
 * 用户角色返回总信息
 * Created by SmileXie on 16/7/15.
 */
@Root(name = "Envelope")
@Namespace(reference = "http://schemas.xmlsoap.org/soap/envelope/",prefix = "s")
//@NamespaceList({
//        @Namespace(reference = "http://www.w3.org/2001/XMLSchema-instance", prefix = "xsi"),
//        @Namespace(reference = "http://www.w3.org/2001/XMLSchema", prefix = "xsd"),
////        @Namespace(reference = "http://schemas.xmlsoap.org/soap/envelope/", prefix = "s")
//})

public class ResEnv {
    @Namespace(reference = "http://schemas.xmlsoap.org/soap/envelope/")
    @Element(name = Base.BODY, required = false)
    public ResBody body;


}
