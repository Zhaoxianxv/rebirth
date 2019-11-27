package com.yfy.app.net.user;

import com.yfy.base.Base;
import com.yfy.final_tag.TagFinal;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

/**
 * Created by yfyandr on 2018/4/26.
 */
@Root(name = TagFinal.SCHOOL_NEWS_BANNER, strict = false)
@Namespace(reference = Base.NAMESPACE)
public class NewBannerReq {
    @Namespace(reference = Base.NAMESPACE)
    @Element(name = "no", required = false)
    private String no;     ///

    public void setNo(String no) {
        this.no = no;
    }
}
