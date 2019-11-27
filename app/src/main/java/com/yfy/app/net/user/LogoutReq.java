package com.yfy.app.net.user;

import com.yfy.base.Base;
import com.yfy.base.Variables;
import com.yfy.db.UserPreferences;
import com.yfy.final_tag.TagFinal;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

/**
 * Created by yfyandr on 2018/4/26.
 */
@Root(name = TagFinal.USER_LOGOUT, strict = false)
@Namespace(reference = Base.NAMESPACE)
public class LogoutReq {


    @Element(name = "session_key", required = false)
    private String session_key= Variables.user.getSession_key();     ///

    @Element(name = "andios", required = false)
    private String andios="and";

    @Element(name = "apikey", required = false)
    private String apikey= UserPreferences.getInstance().getJpushKey();

    @Override
    public String toString() {
        return "";

    }
}
