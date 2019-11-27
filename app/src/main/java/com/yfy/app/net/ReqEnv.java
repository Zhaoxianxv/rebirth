package com.yfy.app.net;

import com.yfy.base.Base;
import com.yfy.final_tag.TagFinal;
import org.simpleframework.xml.*;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.Strategy;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;


/**
 * 用户角色请求Envelope
 */

@Root(name = "soapenv:Envelope")
@NamespaceList({
//        @Namespace(reference = "http://www.w3.org/2001/XMLSchema-instance", prefix = "xsi"),
//        @Namespace(reference = "http://www.w3.org/2001/XMLSchema", prefix = "xsd"),
//        @Namespace(reference = "http://schemas.xmlsoap.org/soap/encoding/", prefix = "enc"),
//        @Namespace(reference = "http://schemas.xmlsoap.org/soap/encoding/", prefix = "encodingStyle"),
    @Namespace(reference = "http://schemas.xmlsoap.org/soap/envelope/", prefix = "soapenv"),
    @Namespace(reference = Base.NAMESPACE ,prefix = Base.TEM)
})
public class ReqEnv {
    @Namespace(reference = "http://schemas.xmlsoap.org/soap/envelope/")
    @Element(name = "Body", required = false)
    public ReqBody body;



    @Namespace(reference = "http://schemas.xmlsoap.org/soap/envelope/")
    @Element(name = "Header", required = false)
    public String aHeader="";


    private static Strategy strategy = new AnnotationStrategy();
    private static Serializer serializer = new Persister(strategy);
    @Override
    public String toString() {

        OutputStream out = new ByteArrayOutputStream();
        try {
            serializer.write(this, out);
            String result = out.toString();
            out.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "Exception";
        }

    }
}
