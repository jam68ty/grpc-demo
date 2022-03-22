package com.shoalter.grpcclient.util;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.util.JsonFormat;


public class ProtoJsonUtil {

    public static String toJson(Message sourceMessage)
            throws InvalidProtocolBufferException {
        return JsonFormat.printer().print(sourceMessage);
    }

    public static Message toProtoBean(Message.Builder targetBuilder, String json)
            throws InvalidProtocolBufferException {
        JsonFormat.parser().ignoringUnknownFields().merge(json, targetBuilder);
        return targetBuilder.build();
    }

}
