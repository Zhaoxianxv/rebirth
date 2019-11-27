package com.yfy.app.net.user;

import com.yfy.base.Base;
import com.yfy.final_tag.TagFinal;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

/**
 * Created by yfyandr on 2018/4/26.
 */
@Root(name = TagFinal.LOGIN, strict = false)
@Namespace(reference = Base.NAMESPACE)

public class LogupReq {


    @Element(name = "username", required = false)
    public String username;

    @Element(name = "password", required = false)
    public String password;

    @Element(name = "role_id", required = false)
    public String role_id;

    @Element(name = "appid", required = false)
    public String appid;

    @Element(name = "andios", required = false)
    private String andios="and";

    @Override
    public String toString() {
        return "LogupReq{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role_id='" + role_id + '\'' +
                ", appid='" + appid + '\'' +
                ", andios='" + andios + '\'' +
                '}';
    }
}
