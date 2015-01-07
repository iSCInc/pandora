package com.wikia.pandora.api;

import ch.halarious.core.HalResource;
import com.wikia.pandora.api.util.HalResponseRenderer;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;


@Provider
@Produces(MediaTypes.HAL)
public class HalMessageBodyWriter implements MessageBodyWriter<HalResource> {

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return true;
    }

    @Override
    public long getSize(HalResource data, Class<?> type, Type genericType, Annotation annotations[], MediaType mediaType) {
        return 0;
    }

    @Override
    public void writeTo(HalResource object,
                        Class<?> type,
                        Type genericType,
                        Annotation[] annotations,
                        MediaType mediaType,
                        MultivaluedMap<String, Object> headers,
                        OutputStream out)
            throws IOException {
        HalResponseRenderer renderer = new HalResponseRenderer(object);
        byte[] bytes = renderer.render().getBytes();
        out.write(bytes);
        out.flush();
    }



}
